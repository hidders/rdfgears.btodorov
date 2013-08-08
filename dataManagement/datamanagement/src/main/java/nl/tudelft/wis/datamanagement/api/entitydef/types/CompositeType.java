package nl.tudelft.wis.datamanagement.api.entitydef.types;

import nl.tudelft.wis.datamanagement.api.entitydef.FieldType;

public interface CompositeType extends FieldType{
	public TupleType getTuple(); 
}
