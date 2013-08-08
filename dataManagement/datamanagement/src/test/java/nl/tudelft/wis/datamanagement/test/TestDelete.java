package nl.tudelft.wis.datamanagement.test;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import nl.tudelft.wis.datamanagement.backend.DataTypesFacade;
import nl.tudelft.wis.datamanagement.backend.persistance.EntityPersister;

import org.junit.Test;

public class TestDelete {

    @Test
    public void testDeleteRoot() throws Exception {
	DataTypesFacade facade = new DataTypesFacade();
	facade.storeDataType(TestUtils.getDescriptor());

	EntityPersister ep = new EntityPersister();
	ep.store("Employee", TestUtils.getObject());

	ep.delete("from Employee", null);
	//
	List<Map> executeQuery = ep.executeQuery("from Employee", null);
	Assert.assertEquals(executeQuery.size(), 0);

    }

    @Test
    public void testDeleteOneToOne() throws Exception {
	DataTypesFacade facade = new DataTypesFacade();
	facade.storeDataType(TestUtils.getDescriptor());

	EntityPersister ep = new EntityPersister();
	ep.store("Employee", TestUtils.getObject());

	ep.delete(
		"select e.desk from Employee e",
		null);
	//
	List<Map> executeQuery = ep.executeQuery("from Employee", null);
	Map emp = executeQuery.get(0);

	Assert.assertNull(emp.get("desk"));
    }

    @Test
    public void testDeleteOneToMany() throws Exception {
	DataTypesFacade facade = new DataTypesFacade();
	facade.storeDataType(TestUtils.getDescriptor());

	EntityPersister ep = new EntityPersister();
	ep.store("Employee", TestUtils.getObject());

	ep.delete(
		"select dep from Employee e join e.departments dep where dep.departmentName like 'dep2'",
		null);
	//
	List<Map> executeQuery = ep.executeQuery("from Employee", null);
	Map emp = executeQuery.get(0);

	Assert.assertEquals(((List) emp.get("departments")).size(), 1);
	Assert.assertEquals(((Map) ((List) emp.get("departments")).get(0))
		.get("departmentName"), "dep1");

    }

}
