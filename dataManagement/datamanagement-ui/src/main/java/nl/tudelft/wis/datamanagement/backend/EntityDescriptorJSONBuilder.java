package nl.tudelft.wis.datamanagement.backend;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import nl.tudelft.wis.datamanagement.api.entitydef.BasicType;
import nl.tudelft.wis.datamanagement.api.entitydef.EntityDescriptor;
import nl.tudelft.wis.datamanagement.api.entitydef.Field;
import nl.tudelft.wis.datamanagement.api.entitydef.types.BagType;
import nl.tudelft.wis.datamanagement.api.entitydef.types.TupleType;

public class EntityDescriptorJSONBuilder {

    public JSONObject parse(EntityDescriptor obj) {
	JSONObject result = new JSONObject();

	result.accumulate("name", obj.getName());
	result.accumulate("title", obj.getName());
	result.accumulate("readAccess", obj.isReadAccess());
	result.accumulate("writeAccess", obj.isWriteAccess());
	result.accumulate("description", obj.getDescription());

	result.accumulate("children", getChildren(obj.getEntity()));

	return result;
    }

    private JSONArray getChildren(TupleType entity) {
	JSONArray children = new JSONArray();

	if (entity.getProperties() == null)
	    return children;

	for (Field obj : entity.getProperties()) {
	    JSONObject prop = new JSONObject();

	    prop.accumulate("name", obj.getName());
	    prop.accumulate("title", obj.getName());

	    if (obj.getValue() instanceof BasicType) {
		prop.accumulate("rglType",
			((BasicType) obj.getValue()).getRGLType());
	    } else if (obj.getValue() instanceof BagType) {
		BagType entprop = (BagType) obj.getValue();
		prop.accumulate("isFolder", true);
		prop.accumulate("isMultiple", true);
		prop.accumulate("children", getChildren(entprop.getTuple()));
	    } else if (obj.getValue() instanceof TupleType) {
		TupleType entprop = (TupleType) obj.getValue();
		prop.accumulate("isFolder", true);
		prop.accumulate("isMultiple", false);
		prop.accumulate("children", getChildren(entprop.getTuple()));
	    }

	    children.add(prop);
	}

	return children;
    }

}
