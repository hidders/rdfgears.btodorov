package nl.tudelft.wis.datamanagement.api.entitydef.types;

import nl.tudelft.wis.datamanagement.api.entitydef.BasicType;

public class BasicTypeFacory {
	public static BasicType typeForName(String type) {
		if (type.equals(BooleanType.TYPE)) {
			return new BooleanType();
		} else if (type.equals(LiteralType.TYPE)) {
			return new LiteralType();
		} else {
			System.out.println("Type: " + type + " is not supported.");
			return null;
		}
	}
}
