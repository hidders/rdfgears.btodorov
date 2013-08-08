package nl.tudelft.wis.datamanagement.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import nl.tudelft.wis.datamanagement.backend.DataTypesFacade;
import nl.tudelft.wis.datamanagement.backend.persistance.EntityPersister;

import org.junit.Test;

public class TestInsertChildren {

    
    @Test
    public void test() throws Exception {
	DataTypesFacade facade = new DataTypesFacade();
	facade.storeDataType(TestUtils.getDescriptor());

	EntityPersister ep = new EntityPersister();
	ep.store("Employee", TestUtils.getObject());

	String query = "from Employee";

	Map<String, Object> setFields = new HashMap<String, Object>();
	setFields.put("departmentName", "newDep");
	
	//
	//
	ep.insertChild(query, null, "departments", setFields);

	
	List<Map> executeQuery = ep.executeQuery("from Employee", null);
	Map emp = executeQuery.get(0);

	List<Map<String, Object>> departments = (List) emp.get("departments");
	Assert.assertEquals(departments.size(), 3);
    }
}
