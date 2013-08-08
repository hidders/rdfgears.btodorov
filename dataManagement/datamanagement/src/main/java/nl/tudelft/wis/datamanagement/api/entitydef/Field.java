package nl.tudelft.wis.datamanagement.api.entitydef;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import nl.tudelft.wis.datamanagement.api.entitydef.types.BagType;
import nl.tudelft.wis.datamanagement.api.entitydef.types.BooleanType;
import nl.tudelft.wis.datamanagement.api.entitydef.types.LiteralType;
import nl.tudelft.wis.datamanagement.api.entitydef.types.TupleType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Field {

    public Field() {
    }

    public Field(String name, FieldType value) {
	super();
	this.name = name;
	this.value = value;
    }

    @XmlAttribute
    private String name;

    @XmlElements({ @XmlElement(name = "bool", type = BooleanType.class),
	    @XmlElement(name = "literal", type = LiteralType.class),
	    @XmlElement(name = "bag", type = BagType.class),
	    @XmlElement(name = "tuple", type = TupleType.class) })
    private FieldType value;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public FieldType getValue() {
	return value;
    }

    public void setValue(FieldType value) {
	this.value = value;
    }

}
