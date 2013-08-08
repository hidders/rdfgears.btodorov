package nl.tudelft.wis.datamanagement.backend;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import nl.tudelft.wis.datamanagement.api.entitydef.EntityDescriptor;
import nl.tudelft.wis.datamanagement.api.entitydef.Field;
import nl.tudelft.wis.datamanagement.api.entitydef.types.BagType;
import nl.tudelft.wis.datamanagement.api.entitydef.types.BasicTypeFacory;
import nl.tudelft.wis.datamanagement.api.entitydef.types.TupleType;

public class EntityDescriptorJSONParser {

    public EntityDescriptor parse(JSONObject obj) {
	EntityDescriptor entityDescriptor = new EntityDescriptor();

	entityDescriptor.setName(obj.getString("name"));
	entityDescriptor.setReadAccess("true".equals(obj
		.getString("readAccess")));
	entityDescriptor.setWriteAccess("true".equals(obj
		.getString("writeAccess")));

	entityDescriptor.setDescription(obj.getString("description"));

	entityDescriptor.setEntity(parseEntity(obj.getJSONArray("children")));

	return entityDescriptor;
    }

    private TupleType parseEntity(JSONArray jsonArray) {
	TupleType entity = new TupleType();
	List<Field> props = new ArrayList<Field>();
	entity.setProperties(props);

	if (jsonArray == null)
	    return entity;

	for (Object obj : jsonArray) {
	    JSONObject prop = (JSONObject) obj;

	    if (prop.containsKey("isFolder") && prop.getBoolean("isFolder")) {
		Field property = new Field();
		props.add(property);

		property.setName(prop.getString("name"));

		JSONArray children = null;
		if (prop.containsKey("children")) {
		    children = prop.getJSONArray("children");
		}

		if ("true".equals(prop.getString("isMultiple")))
		    property.setValue(new BagType(parseEntity(children)));
		else {
		    property.setValue(parseEntity(children));
		}

	    } else {
		Field property = new Field();
		props.add(property);

		property.setName(prop.getString("name"));
		property.setValue(BasicTypeFacory.typeForName(prop
			.getString("rglType")));
	    }

	}
	return entity;
    }

}
