package nl.tudelft.wis.datamanagement.api.entitydef;

public interface BasicType extends FieldType{
	public String getJavaType();
	public String getDBType();
	public String getRGLType();
}
