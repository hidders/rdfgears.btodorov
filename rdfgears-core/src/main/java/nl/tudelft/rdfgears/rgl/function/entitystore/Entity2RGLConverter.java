package nl.tudelft.rdfgears.rgl.function.entitystore;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import nl.tudelft.rdfgears.engine.ValueFactory;
import nl.tudelft.rdfgears.rgl.datamodel.value.RGLValue;
import nl.tudelft.rdfgears.rgl.datamodel.value.impl.ModifiableRecord;
import nl.tudelft.rdfgears.rgl.datamodel.value.impl.bags.ListBackedBagValue;
import nl.tudelft.rdfgears.util.row.FieldIndexArrayMap;

public class Entity2RGLConverter {

    public RGLValue convert(Map<String, Object> m) {
	removeMetadata(m);
	FieldIndexArrayMap fiMap = new FieldIndexArrayMap(m.keySet());
	ModifiableRecord rec = new ModifiableRecord(fiMap);

	for (String key : m.keySet()) {
	    Object obj = m.get(key);

	    if (obj instanceof List<?>) {
		List<Object> bag = (List<Object>) obj;
		rec.put(key, convert(bag));

	    } else if (obj instanceof Map<?, ?>) {
		Map<String, Object> record = (Map<String, Object>) obj;
		rec.put(key, convert(record));
	    } else {
		rec.put(key, ValueFactory.createLiteralPlain(m.get(key)
			.toString(), null));
	    }

	}

	return rec;
    }

    private void removeMetadata(Map<String, Object> m) {
	for (String field : new HashSet<String>(m.keySet())) {
	    if (field.startsWith("$") && field.endsWith("$")) {
		m.remove(field);
	    }
	}

    }

    private RGLValue convert(List<Object> bag) {
	List<RGLValue> backingList = ValueFactory.createBagBackingList();

	for (Object o : bag) {
	    if (o instanceof Map<?, ?>) {
		Map<String, Object> record = (Map<String, Object>) o;
		backingList.add(convert(record));
	    }
	}

	return new ListBackedBagValue(backingList);
    }
}
