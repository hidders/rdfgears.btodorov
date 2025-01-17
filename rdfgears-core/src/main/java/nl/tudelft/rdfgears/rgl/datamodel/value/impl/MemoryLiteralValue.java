package nl.tudelft.rdfgears.rgl.datamodel.value.impl;

import nl.tudelft.rdfgears.engine.Engine;
import nl.tudelft.rdfgears.engine.ValueFactory;
import nl.tudelft.rdfgears.engine.bindings.MemoryLiteralBinding;
import nl.tudelft.rdfgears.rgl.datamodel.value.LiteralValue;
import nl.tudelft.rdfgears.rgl.datamodel.value.RDFValue;
import nl.tudelft.rdfgears.rgl.datamodel.value.RGLValue;
import nl.tudelft.rdfgears.rgl.exception.ComparisonNotDefinedException;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.impl.LiteralImpl;
import com.sleepycat.bind.tuple.TupleBinding;


/**
 * Implementation is based on Jena LiteralImpl. This is a relatively complex structure various wrapped literal
 * objects. This is necessary because it must flexibly deal with plain literals (with or without language tag) and 
 * typed literals, and the string part of a typed literal must be parsed/unparsed according to its type.  
 * 
 * It may be better for perfomance to only use the strictly necessary Jena parts, but it must be investigated which 
 * those are. First we have to figure out a good interface for the literal types (possibly inspired by Jena) that 
 * provides a good balance between simplicity and flexibility.  
 * 
 * I think it would be good to split this class in a TypedLiteralValue and a PlainLiteralValue, plain having an optional 
 * language tag. They all implement the same interface, but need not have all possible data fields, thus also making
 * method implementation easier (less conditions). Then the LiteralValue interface provides all methods like getLanguage(), 
 * getLiteralType(), getValueDouble(), getValueDate(), getValueString(), etc. but a plain literal returns null on getLiteralType()
 * and a typed literal returns null on getLanguage().
 * 
 * @author Eric Feliksik
 *
 */
public class MemoryLiteralValue extends LiteralValue {
	private Literal node;
	
	/* values to calculate the RDFNode from */
	private RDFDatatype dtype;
	private Object value;

	private String lang = null;
	
	/**
	 * Make RGL Literal based on a Jena Literal
	 * @param literalNode
	 */
	private MemoryLiteralValue(Literal literalNode) {
		if (! literalNode.isLiteral()){
			throw new RuntimeException("Need a Node_Literal; cannot instantiate LiteralValue with Node of type "+literalNode.getClass());
		}
		
		this.node = literalNode;
		this.dtype = (RDFDatatype) literalNode.getDatatype();
		this.value = literalNode.getString();
		
		String lang = literalNode.getLanguage();
		if (! lang.equals(""))
			this.lang = lang;
	}

//	/** 
//	 * Handy if you want to make an integer object: you don't have to build a string, that is done later. 
//	 */
//	private MemoryLiteralValue(Object stringable, XSDDatatype dtype) {
//		this.stringable = stringable;
//		this.dtype = dtype;
//	}

	/** 
	 * Handy if you want to make an integer object: you don't have to build a string, that is done later. 
	 */
	private MemoryLiteralValue(Object value, RDFDatatype dtype) {
		this.value = value;
		this.dtype = dtype;
	}

	/** 
	 * Create plain literal with (optional) language tag  
	 */
	private MemoryLiteralValue(String value, String lang) {
		this.value = value;
		this.lang = lang;
	}

	/**
	 * Create a typed literal
	 * @param stringable
	 * @param dtype
	 * @return
	 */
	public static MemoryLiteralValue createLiteralTyped(Object stringable, RDFDatatype dtype){
		return new MemoryLiteralValue(stringable, dtype);
	}
	

	public static LiteralValue createPlainLiteral(String str, String language) {
		return new MemoryLiteralValue(str, language);
	}

	public static LiteralValue createLiteral(Literal literal) {
		return new MemoryLiteralValue(literal);
	}


    public Literal createTypedLiteral( String string , RDFDatatype dType)
    {
        return new LiteralImpl(Node.createLiteral(string, "", dType), null) ;
    }
    

	@Override
	public RDFDatatype getLiteralType() {
		return dtype;
	}
	
	@Override
	public String getLanguageTag() {
		return lang;
	}

	/**
	 * Override getRDFNode. Do not create some Jena-resource with our value-id, but instead just return our Jena 
	 * equivalent Literal Node. 
	 * 
	 *  Get an RDFNode representation of this value. 
	 * Creating it is postponed until this function is called, because an RDFNode object is 
	 * a few times heavier than our RDFValue.
	 *  
	 */
	@Override
	public Literal getRDFNode() {
		if (node==null){
			assert(value!=null);
			if (dtype!=null){ // typed literal
				node = Engine.getDefaultModel().createTypedLiteral(value, dtype);
			}
			else { // plain literal, possibly with language tag 
				node = Engine.getDefaultModel().createLiteral(value.toString(), lang);
				assert(lang==null || node.getLanguage().equals(lang)); // if lang==null, getLanguage() returns "" (empty string)
			}
			assert(node!=null);
		}
		return node;
	}

	
	private String formatString(String lexicalForm, String languageTag, String type){
		StringBuilder builder = new StringBuilder();
		assert(languageTag==null || type==null) : "In RDF you cannot have both a language tag and a type. "+languageTag+", datatype="+type;
		
		builder.append("\"");
		builder.append(lexicalForm); 
		builder.append("\"");
		
		if (languageTag!=null){
			builder.append("@");
			builder.append(languageTag);
		} else if (type!=null){
			builder.append("^^<");
			builder.append(type);
			builder.append(">");
		}
		return builder.toString();
	}

	
	public String toString(){
		if (node!=null){
			String lang = node.getLanguage();
			if (lang.equals(""))
				lang = null;
			
			return formatString(node.getLexicalForm(), lang, node.getDatatypeURI());
			//return "\""+ getRDFNode().toString()+"\"";
		}
		else {
			assert(value!=null) : "We have neither a node nor a value, so then this Literal is undefined ";
			String dtypeStr = dtype!=null ? dtype.getURI() : null; 
			return formatString( value.toString(), null, dtypeStr);
		}
			
	}
	
	/**
	 * Hmmm, this class can currently only compare doubles!!!
	 * 
	 * That's not so elegant. 
	 */
	@Override
	public int compareTo(RGLValue v2){
		if (v2.isNull())
			return 1; // we are larger than NULL 
		
		LiteralValue v2Lit = v2.asLiteral();
		LiteralValue thisLit = this.asLiteral();
		
		/**
		 * 
		 * CLEAN THIS UP PLEASE 
		 * 
		 * We compare with trial and error and fall back to comparing string representation.  
		 * 
		 * It would be better to check the literal type and then pick the comparator accordingly.  
		 *  
		 */
		try {
			//System.out.println("Node is: "+ ((RDFValue) that).getRDFNode());
			double thatDouble = v2Lit.getValueDouble();
			double thisDouble = thisLit.getValueDouble();
			
			if (thisDouble==thatDouble) 
				return 0;
			else if (thisDouble<thatDouble) 
				return -1;
			else // (thisDouble>thatDouble)
				return 1;
		} catch (NumberFormatException e){
			/* compare them as strings */ 
			String thisString = thisLit.getValueString();
			String v2String = v2Lit.getValueString();
			
			if (thisLit.getLiteralType()!=v2Lit.getLiteralType()){
				/* cannot compare different types, but just give back anything */
				Engine.getLogger().debug("WARNING: Returning hacked comparison of values with types "+thisLit.getLiteralType()+" and "+v2Lit.getLiteralType());
				
				if (thisLit.getLiteralType()==null){
					return -1; 
				} else if (v2Lit.getLiteralType()==null){
					return 1; 
				} else {
					return thisLit.getLiteralType().getURI().compareTo(v2Lit.getLiteralType().getURI());	
				}
			}
			if ( thisLit.getLanguageTag()!=v2Lit.getLanguageTag()){
				/* cannot compare different types, but just give back anything */
				Engine.getLogger().debug("WARNING: Returning hack for comparison of values with languages "+thisLit.getLanguageTag()+" and "+v2Lit.getLanguageTag());
				
				if (thisLit.getLanguageTag()==null){
					return -1; 
				} else if (v2Lit.getLanguageTag()==null){
					return 1; 
				} else {
					return thisLit.getLanguageTag().compareTo(v2Lit.getLanguageTag());	
				}
			}
			
			return thisString.compareTo(v2String);
			
		} catch (RuntimeException e){
			Engine.getLogger().error("Cannot compare values "+this+" and "+v2+": "+e.getMessage());
			e.printStackTrace();
			throw new ComparisonNotDefinedException(this, v2);
		}
	}
	
	

	/** 
	 * Try to parse the literal as a double, using the Jena mechanism (a bit slower).
	 * 
	 * May therefore throw a RuntimeException (e.g. NumberFormatException, check Jena source).
	 * 
	 * It is cleaner to never do getRDFNode(), but we then have to see for ourselves whether we have 
	 * to create a String representation before parsing it, or whether we can use the 'value' object
	 * can be converted to a double directory (e.g. from Double or Integer)
	 */
	public double getValueDouble(){
		return getRDFNode().getDouble(); 
	}	

	
	/** 
	 * return the Lexical Form of this literal. That is, the string-representation of the value, 
	 * without language or datatype.  
	 * 
	 */
	@Override
	public String getValueString() {
		return getRDFNode().getLexicalForm();
	}

	@Override
	public TupleBinding<RGLValue> getBinding() {
		return new MemoryLiteralBinding();
	}
}
