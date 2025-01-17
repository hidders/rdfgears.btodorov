package nl.tudelft.rdfgears.rgl.datamodel.type;

/**
 * TODO: It is probably nicer to *NOT* check the types by doing 'someType instanceof BagType' 
 * but rather by using some functions .isBagType(), .isRecordType(), etc.
 * 
 * @author Eric Feliksik
 *
 */
public abstract class RGLType {
	public abstract boolean isType(RGLType type);
//	public abstract boolean isSupertypeOf(RGLType otherType);
//	
//	public boolean isSubtypeOf(RGLType otherType) {
//		if (otherType==null)
//			throw new RuntimeException("Cannot compare to type to null value");
//		
//		return otherType.isSupertypeOf(this);
//	}
	
	
	/**
	 * A dispatcher method that is similar to the visitor pattern. 
	 * It is implemented by all types, to allow the otherType to 
	 * do the dispatching to the right Type class. 
	 * @param otherType
	 * @return
	 */
	public abstract boolean acceptsAsSubtype(RGLType otherType); 
	
//	public boolean isStrictSupertypeOf(RGLType otherType) {
//		return !otherType.isSubtypeOf(this);
//	} 
	
	
	/* ***************************
	 * following functions should be overridden by subclasses 
	 */

	public boolean isSubtypeOf(BagType type){
		return false;
	}
	
	public boolean isSubtypeOf(RecordType type){
		return false;
	}
	
	public boolean isSubtypeOf(GraphType type){
		return false;
	}
	
	public boolean isSubtypeOf(RDFType type){
		return false;
	}
	
	public boolean isSubtypeOf(BooleanType type){
		return false;
	}

	public boolean isSubtypeOf(SubType type){
		return false;
	}
	
	public boolean isSubtypeOf(RGLType type){
		/* we don't know how to handle this, let it be dispatched */
		return type.acceptsAsSubtype(this);
	}
	
	
	
	/* ****************************************
	 * Functions to check whether we can cast with the asXXX() functions. 
	 */

	public boolean isBagType(){
		return false;
	}
	public boolean isRecordType(){
		return false;
	}

	public boolean isGraphType(){
		return false;
	}

	public boolean isRDFValueType(){
		return false;
	}

	public boolean isBooleanType(){
		return false;
	}
	
	public BagType asBagType(){
		throw new RuntimeException("I am not a Bag: "+toString());
	}
	public RecordType asRecordType(){
		throw new RuntimeException("I am not a Record: "+toString());
	}

	public GraphType asGraphType(){
		throw new RuntimeException("I am not a Graph: "+toString());
	}

	public RDFType asRDFType(){
		throw new RuntimeException("I am not an RDFValue: "+toString());
	}

	public BooleanType asBooleanType(){
		throw new RuntimeException("I am not a Boolean: "+toString());
	}

	
}
