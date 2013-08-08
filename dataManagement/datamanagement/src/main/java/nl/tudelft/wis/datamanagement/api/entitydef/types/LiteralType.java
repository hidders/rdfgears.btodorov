package nl.tudelft.wis.datamanagement.api.entitydef.types;

import javax.xml.bind.annotation.XmlTransient;

import nl.tudelft.wis.datamanagement.api.entitydef.BasicType;

public class LiteralType implements BasicType {

    public static final String TYPE = "literal";

    @XmlTransient
    public String getJavaType() {
	return "string";
    }

    @XmlTransient
    public String getDBType() {
	return "VARCHAR(255)";
    }

    @XmlTransient
    public String getRGLType() {
	return TYPE;
    }

}
