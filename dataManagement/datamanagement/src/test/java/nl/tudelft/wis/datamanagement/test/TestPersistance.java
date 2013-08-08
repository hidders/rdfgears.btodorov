package nl.tudelft.wis.datamanagement.test;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import nl.tudelft.wis.datamanagement.backend.DataTypesFacade;
import nl.tudelft.wis.datamanagement.backend.persistance.EntityPersister;

import org.junit.Test;

public class TestPersistance {

    @Test
    public void test() throws Exception {
	DataTypesFacade facade = new DataTypesFacade();
	facade.storeDataType(TestUtils.getDescriptor());

	EntityPersister ep = new EntityPersister();
	ep.store("Employee", TestUtils.getObject());
	
//	
//
	List<Map> executeQuery = ep.executeQuery("from Employee", null);
	
	Assert.assertEquals(executeQuery.size(), 1);
	
	Map emp = executeQuery.get(0);
	
	Assert.assertEquals(emp.get("name"), "firstEmployee");
	Assert.assertEquals(((Map)emp.get("desk")).get("deskName"), "desk1");
	Assert.assertEquals(((List)emp.get("departments")).size(), 2);
	
    }

}
