package nl.tudelft.rdfgears.rgl.function.entitystore;

import java.util.HashMap;
import java.util.Map;

import nl.tudelft.rdfgears.engine.ValueFactory;
import nl.tudelft.rdfgears.rgl.datamodel.type.RDFType;
import nl.tudelft.rdfgears.rgl.datamodel.type.RGLType;
import nl.tudelft.rdfgears.rgl.datamodel.value.RGLValue;
import nl.tudelft.rdfgears.rgl.function.SimplyTypedRGLFunction;
import nl.tudelft.rdfgears.util.row.ValueRow;
import nl.tudelft.wis.datamanagement.backend.persistance.EntityPersister;

/**
 * A function that can execute SPARQL SELECT/CONSTRUCT queries.
 * 
 * The query is configured with initialize()
 * 
 * 
 * @author Eric Feliksik
 * 
 */
public class EntityUpdateFunction extends SimplyTypedRGLFunction {

    private String query;
    private Map<String, String> setStatements;

    public void initialize(Map<String, String> config) {
	query = config.get("query");
	parseSetStatemetns(config);

	/*
	 * configure the required inputs based on the ';' separated list of
	 * prebound variable names
	 */
	String bindVarStr = config.get("bindVariables");
	if (bindVarStr != null) {
	    String[] split = bindVarStr.split(";");
	    for (int i = 0; i < split.length; i++) {
		if (split[i].length() > 0)
		    requireInputType(split[i], RDFType.getInstance());
	    }
	}
    }

    private void parseSetStatemetns(Map<String, String> config) {
	setStatements= new HashMap<String, String>();
	for(String str : config.get("setStatement").split(",")){
	    if(str.contains("=")){
		String[] item = str.split("=");
		setStatements.put(item[0].trim(), item[1].trim());
	    }
	}
    }

    @Override
    public RGLType getOutputType() {
	return RDFType.getInstance();
    }

    /**
     * The values in the inputRow will be bound to the variables in the query.
     * 
     * This is also the way to pass models; you can use a section GRAPH $mygraph
     * { ... } .
     * 
     * Then make sure the inputRow contains a field 'mygraph' with a RGL Graph
     * value.
     * 
     */
    @Override
    public RGLValue simpleExecute(ValueRow inputRow) {
	try {
	    Map<String, String> params = new HashMap<String, String>();
	    for (String key : inputRow.getRange()) {
		params.put(key, inputRow.get(key).asLiteral().getValueString());
	    }

	    new EntityPersister().update(query, params, parseInput(inputRow));

	    return ValueFactory.createTrue();

	} catch (Throwable t) {
	    t.printStackTrace();
	    return ValueFactory.createFalse();
	}
    }

    private Map<String, Object> parseInput(ValueRow inputRow) {
	Map<String, Object> result = new HashMap<String, Object>();
	RGL2EntityConverter converter = new RGL2EntityConverter();
	
	for(String field : setStatements.keySet()){
	    String value = setStatements.get(field);
	    if(value.startsWith("\"") && value.endsWith("\"")){
		result.put(field, value.subSequence(1, value.length() - 1));
	    }else{
		result.put(field, converter.convertRGLValue(inputRow.get(value)));
	    }
	}
	
	return result;
    }

}
