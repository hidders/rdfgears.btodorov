package nl.tudelft.rdfgears.rgl.datamodel.value.impl;

import java.util.Collection;
import java.util.Iterator;

import nl.tudelft.rdfgears.rgl.datamodel.value.BagValue;
import nl.tudelft.rdfgears.rgl.datamodel.value.RGLValue;
import nl.tudelft.rdfgears.rgl.datamodel.value.StreamingBagValue;
import nl.tudelft.rdfgears.rgl.function.RGLFunction;
import nl.tudelft.rdfgears.rgl.workflow.FunctionProcessor;
import nl.tudelft.rdfgears.rgl.workflow.InputPort;
import nl.tudelft.rdfgears.rgl.workflow.LazyRGLValue;
import nl.tudelft.rdfgears.rgl.workflow.ValueRowIterator;
import nl.tudelft.rdfgears.util.row.ValueRow;


/**
 * 
 * A MappingBagValue is a bag that takes an InputRow and a processor. 
 * The processors has 0 or more iterating ports. For every iterating port 'p', the InputRow's element
 * 'p' is assumed to contain a Bag; For the other elements, assume they are conceptually put in a singleton bag.
 * 
 *  Now take the Cartesian bag product 'C' of all these bags. This is a Bag of mappings (inputName=>element). 
 *  
 * This bags contents are defined as the Mapping of C with the processor's function. It is thus used to implement 
 * the iteration mechanism or RDF gears. So if story is unclear, read the formal RGL definition.   
 *  
 * @author Eric Feliksik
 *
 */
public class MappingBagValue extends StreamingBagValue {
	private static int INPUTS_OK;
	private static int INPUTS_NULL;
	private static int INPUTS_UNKNOWN;
	private int inputsPrecondition = INPUTS_UNKNOWN;
	
	
	class MappingIterator implements Iterator<RGLValue>{
		Iterator<ValueRow> inputRowIter;
		public MappingIterator(){
			inputRowIter = new ValueRowIterator(inputRow, processor, false);
		}
		
		@Override
		public synchronized RGLValue next() {
			assert( hasNext());
			return transformValueRow(inputRowIter.next()) ;
		}
		
		@Override
		public boolean hasNext(){
			return inputRowIter.hasNext();
		}

		@Override
		public void remove() {
			assert(false): "not implemented";
		}
		

		private RGLValue transformValueRow(ValueRow input){
			if (transformationFunction.isLazy()){
				return new LazyRGLValue(transformationFunction, input);
			} else {
				return transformationFunction.execute(input);
			}
		}
	}
	
	private RGLFunction transformationFunction;
	private ValueRow inputRow; 
	private FunctionProcessor processor; 
	/**
	 * Instantiate a BagValue that ... 
	 */
	public MappingBagValue(FunctionProcessor processor, ValueRow inputRow){
		this.inputRow = inputRow;
		this.processor = processor;
		this.transformationFunction = processor.getFunction();
	}
	
	@Override
	public Iterator<RGLValue> getStreamingBagIterator() {
		return new MappingIterator();
	}
//	
//	/**
//	 * If any of the marked input elements is NULL, this class can not iterate over that input bag. 
//	 * Thus the result must be null. 
//	 * If an iterator is instantiated, errors will occur. 
//	 */
//	public boolean isNull(){ 
//		 if (inputsPrecondition == INPUTS_UNKNOWN){ /* initialize */
//			 inputsPrecondition = INPUTS_OK;
//			 
//			 for (InputPort port : processor.getPortSet()){
//				 if (port.iterates()){
//					 String inputName = port.getName();
//					 
//					 if (inputRow.get(inputName).isNull()){ /* do NOT get it from processor, it may be reset */
//						 /* we cannot iterate over this port, as it returns null */
//						 inputsPrecondition = INPUTS_NULL;
//					 }
//				 }
//			 }
//		 }
//		 return inputsPrecondition == INPUTS_NULL; 
//	}

	@Override
	public int size() {
		return BagValue.getNaiveSize(this);
	}
}


