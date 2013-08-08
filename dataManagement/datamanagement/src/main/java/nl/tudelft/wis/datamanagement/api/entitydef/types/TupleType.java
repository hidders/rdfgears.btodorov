package nl.tudelft.wis.datamanagement.api.entitydef.types;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import nl.tudelft.wis.datamanagement.api.entitydef.Field;

@XmlAccessorType(XmlAccessType.FIELD)
public class TupleType implements CompositeType {

	@XmlElement(name = "field")
	private List<Field> properties;


	public List<Field> getProperties() {
		return properties;
	}

	public void setProperties(List<Field> properties) {
		this.properties = properties;
	}

	public TupleType getTuple() {
		return this;
	}
}
