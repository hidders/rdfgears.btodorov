package nl.tudelft.rdfgears.rgl.function.entitystore;

import java.util.HashMap;
import java.util.Map;

import nl.tudelft.rdfgears.engine.ValueFactory;
import nl.tudelft.rdfgears.rgl.datamodel.type.BooleanType;
import nl.tudelft.rdfgears.rgl.datamodel.type.RGLType;
import nl.tudelft.rdfgears.rgl.datamodel.value.RGLValue;
import nl.tudelft.rdfgears.rgl.function.SimplyTypedRGLFunction;
import nl.tudelft.rdfgears.util.row.ValueRow;
import nl.tudelft.wis.datamanagement.api.entitydef.EntityDescriptor;
import nl.tudelft.wis.datamanagement.api.entitydef.Field;
import nl.tudelft.wis.datamanagement.backend.datatypes.DataTypesPersister;
import nl.tudelft.wis.datamanagement.backend.persistance.EntityPersister;

public class EntityStoreFunction extends SimplyTypedRGLFunction {

    private String entity;

    @Override
    public void initialize(Map<String, String> config) {
	super.initialize(config);

	this.entity = config.get("entity");

	DataTypesPersister entityDescriptiorManager = new DataTypesPersister();
	EntityDescriptor byName;
	try {
	    byName = entityDescriptiorManager.getByName(entity);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return;
	}

	for (Field p : byName.getEntity().getProperties()) {
	    requireInput(p.getName());
	}
    }

    @Override
    public RGLType getOutputType() {
	return BooleanType.getInstance();
    }

    @Override
    public RGLValue simpleExecute(ValueRow inputRow) {
	EntityPersister entityDescriptiorManager = new EntityPersister();

	Map<String, Object> object = new RGL2EntityConverter().convert(inputRow);
	try {
	    entityDescriptiorManager.store(entity, object);
	} catch (Exception e) {
	    e.printStackTrace();
	    return ValueFactory.createFalse();
	}

	return ValueFactory.createTrue();
    }

}
