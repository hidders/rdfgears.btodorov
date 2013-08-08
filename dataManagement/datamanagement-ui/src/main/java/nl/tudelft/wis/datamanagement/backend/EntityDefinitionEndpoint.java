package nl.tudelft.wis.datamanagement.backend;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import nl.tudelft.wis.datamanagement.api.entitydef.EntityDescriptor;
import nl.tudelft.wis.datamanagement.backend.datatypes.DataTypesPersister;
import nl.tudelft.wis.datamanagement.backend.db.DatabaseManager;
import nl.tudelft.wis.datamanagement.backend.hbm.HBMPersister;

public class EntityDefinitionEndpoint {

    public boolean save(JSONObject input) {
	try {
	    EntityDescriptor entityDescriptor = new EntityDescriptorJSONParser()
		    .parse(input);

	    new DataTypesFacade().storeDataType(entityDescriptor);

	} catch (Exception e) {
	    e.printStackTrace();
	    return false;
	}

	return true;
    }

    public JSONObject read(String name) {
	try {
	    EntityDescriptor descriptor = new DataTypesPersister()
		    .getByName(name);

	    return new EntityDescriptorJSONBuilder().parse(descriptor);

	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public boolean delete(String name) {
	try {
	    new DataTypesFacade().removeDataType(name);
	} catch (Exception e) {
	    e.printStackTrace();
	    return false;
	}

	return true;
    }

    public static void main(String[] args) {
	String data = "{\"name\":\"test\",\"description\":\"test\",\"readAccess\":false,\"writeAccess\":true,\"entities\":[{\"title\":\"Propety\",\"key\":\"_2\",\"isFolder\":false,\"isLazy\":false,\"tooltip\":null,\"href\":null,\"icon\":null,\"addClass\":null,\"noLink\":false,\"activate\":false,\"focus\":false,\"expand\":false,\"select\":false,\"hideCheckbox\":false,\"unselectable\":false,\"name\":\"Property\",\"rglType\":\"double\"},{\"title\":\"PropetyMult\",\"key\":\"_3\",\"isFolder\":true,\"isLazy\":false,\"tooltip\":null,\"href\":null,\"icon\":null,\"addClass\":null,\"noLink\":false,\"activate\":false,\"focus\":false,\"expand\":false,\"select\":false,\"hideCheckbox\":false,\"unselectable\":false,\"name\":\"PropetyMult\",\"rglType\":\"double\",\"isMultiple\":true}]}";
	JSONObject jsonObj = (JSONObject) JSONSerializer.toJSON(data);

	new EntityDefinitionEndpoint().save(jsonObj);
    }
}
