package nl.tudelft.wis.datamanagement.api.entitydef.types;

import javax.xml.bind.annotation.XmlTransient;

import nl.tudelft.wis.datamanagement.api.entitydef.BasicType;

public class BooleanType implements BasicType {

    public static final String TYPE = "boolean";

    @XmlTransient
    public String getJavaType() {
	return TYPE;
    }

    @XmlTransient
    public String getDBType() {
	return "bool";
    }

    @XmlTransient
    public String getRGLType() {
	return TYPE;
    }
}
