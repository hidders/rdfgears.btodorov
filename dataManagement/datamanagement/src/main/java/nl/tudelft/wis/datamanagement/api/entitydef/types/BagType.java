package nl.tudelft.wis.datamanagement.api.entitydef.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class BagType implements CompositeType {

	@XmlElement(name = "tuple")
	private TupleType tuple;

	public BagType(TupleType tuple) {
		this.tuple = tuple;
	}

	public BagType() {
	}

	public void setTuple(TupleType tuple) {
		this.tuple = tuple;
	}

	public TupleType getTuple() {
		return tuple;
	}
}
