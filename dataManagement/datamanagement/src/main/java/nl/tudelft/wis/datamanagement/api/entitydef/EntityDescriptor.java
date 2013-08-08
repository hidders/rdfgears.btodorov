package nl.tudelft.wis.datamanagement.api.entitydef;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import nl.tudelft.wis.datamanagement.api.entitydef.types.TupleType;

@XmlRootElement(name = "entity")
@XmlType(propOrder = { "name", "description", "readAccess", "writeAccess",
		"entity" })
@XmlAccessorType(XmlAccessType.FIELD) 
public class EntityDescriptor {

	@XmlElement
	private String name;
	
	@XmlTransient
	private String originalName;

	@XmlElement
	private String description;

	@XmlElement
	private boolean readAccess;

	@XmlElement
	private boolean writeAccess;

	@XmlElement(name = "type")
	private TupleType entity;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	
	public String getOriginalName() {
		return originalName;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isReadAccess() {
		return readAccess;
	}

	public void setReadAccess(boolean readAccess) {
		this.readAccess = readAccess;
	}

	public boolean isWriteAccess() {
		return writeAccess;
	}

	public void setWriteAccess(boolean writeAccess) {
		this.writeAccess = writeAccess;
	}

	public TupleType getEntity() {
		return entity;
	}

	public void setEntity(TupleType entity) {
		this.entity = entity;
	}

}
