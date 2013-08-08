package nl.tudelft.rdfgears.rgl.function.entitystore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.tudelft.rdfgears.rgl.datamodel.value.BagValue;
import nl.tudelft.rdfgears.rgl.datamodel.value.BooleanValue;
import nl.tudelft.rdfgears.rgl.datamodel.value.GraphValue;
import nl.tudelft.rdfgears.rgl.datamodel.value.LiteralValue;
import nl.tudelft.rdfgears.rgl.datamodel.value.RGLValue;
import nl.tudelft.rdfgears.util.row.ValueRow;

public class RGL2EntityConverter {
    public Map<String, Object> convert(ValueRow inputRow) {
	Map<String, Object> object = new HashMap<String, Object>();

	for (String p : inputRow.getRange()) {
	    RGLValue rglValue = inputRow.get(p);
	    if (rglValue.isBag()) {
		object.put(p, parse(rglValue.asBag()));
	    } else if (rglValue.isRecord()) {
		object.put(p, convert(rglValue.asRecord()));
	    } else if (rglValue.isBoolean()) {
		object.put(p, parse(rglValue.asBoolean()));
	    } else if (rglValue.isGraph()) {
		object.put(p, parse(rglValue.asGraph()));
	    } else if (rglValue.isLiteral()) {
		object.put(p, parse(rglValue.asLiteral()));
	    }
	}

	return object;
    }

    private Object parse(LiteralValue asLiteral) {
	return asLiteral.getValueString();
    }

    private Object parse(GraphValue asGraph) {
	return asGraph.getURI().toString();
    }

    private Object parse(BooleanValue asBoolean) {
	return asBoolean.isTrue();
    }

    private List parse(BagValue asBag) {
	List<Object> result = new ArrayList<Object>();

	for (RGLValue object : asBag) {
	    if (object.isRecord()) {
		result.add(convert(object.asRecord()));
	    } else {
		throw new IllegalArgumentException();
	    }
	}
	return result;
    }

    public Object convertRGLValue(RGLValue rglValue) {
	if (rglValue.isBag()) {
	    return parse(rglValue.asBag());
	} else if (rglValue.isRecord()) {
	    return convert(rglValue.asRecord());
	} else if (rglValue.isBoolean()) {
	    return parse(rglValue.asBoolean());
	} else if (rglValue.isGraph()) {
	    return parse(rglValue.asGraph());
	} else if (rglValue.isLiteral()) {
	    return parse(rglValue.asLiteral());
	}
	
	return null;
    }
}
