package nl.tudelft.wis.datamanagement.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.tudelft.wis.datamanagement.api.entitydef.EntityDescriptor;
import nl.tudelft.wis.datamanagement.api.entitydef.Field;
import nl.tudelft.wis.datamanagement.api.entitydef.types.BagType;
import nl.tudelft.wis.datamanagement.api.entitydef.types.LiteralType;
import nl.tudelft.wis.datamanagement.api.entitydef.types.TupleType;

public class TestUtils {
    public static EntityDescriptor getDescriptor() {
	TupleType desk = new TupleType();
	List<Field> properties = new ArrayList<Field>();
	properties.add(new Field("deskName", new LiteralType()));
	properties.add(new Field("number", new LiteralType()));
	desk.setProperties(properties);

	TupleType department = new TupleType();
	properties = new ArrayList<Field>();
	properties.add(new Field("departmentName", new LiteralType()));
	department.setProperties(properties);

	TupleType employee = new TupleType();
	properties = new ArrayList<Field>();
	properties.add(new Field("name", new LiteralType()));
	properties.add(new Field("departments", new BagType(department)));
	properties.add(new Field("desk", desk));
	employee.setProperties(properties);

	EntityDescriptor desc = new EntityDescriptor();

	desc.setName("Employee");
	desc.setEntity(employee);

	return desc;
    }

    public static Map getObject() {
	Map desk = new HashMap();
	desk.put("deskName", "desk1");
	desk.put("number", "5");

	Map department1 = new HashMap();
	department1.put("departmentName", "dep1");

	Map department2 = new HashMap();
	department2.put("departmentName", "dep2");

	List departments = new ArrayList();
	departments.add(department1);
	departments.add(department2);

	Map employee = new HashMap();
	employee.put("name", "firstEmployee");
	employee.put("desk", desk);
	employee.put("departments", departments);

	return employee;
    }
}
