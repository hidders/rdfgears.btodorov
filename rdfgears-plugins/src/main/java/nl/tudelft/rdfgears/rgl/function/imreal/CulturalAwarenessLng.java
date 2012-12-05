package nl.tudelft.rdfgears.rgl.function.imreal;

import java.io.BufferedReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import nl.tudelft.rdfgears.engine.Config;
import nl.tudelft.rdfgears.engine.Engine;
import nl.tudelft.rdfgears.engine.ValueFactory;
import nl.tudelft.rdfgears.rgl.datamodel.type.BagType;
import nl.tudelft.rdfgears.rgl.datamodel.type.RDFType;
import nl.tudelft.rdfgears.rgl.datamodel.type.RGLType;
import nl.tudelft.rdfgears.rgl.datamodel.value.RGLValue;
import nl.tudelft.rdfgears.rgl.function.SimplyTypedRGLFunction;
import nl.tudelft.rdfgears.rgl.function.imreal.vocabulary.USEM;
import nl.tudelft.rdfgears.rgl.function.imreal.vocabulary.WI;
import nl.tudelft.rdfgears.rgl.function.imreal.vocabulary.WO;
import nl.tudelft.rdfgears.util.row.ValueRow;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * Based on the number of languages detected in tweets, a cultural awareness value is returned
 * 
 * 0 if only 1 language is detected
 * 1 if 2 languages are detected
 * 2 if 3+ languages are detected
 * 
 * @author Claudia
 */
public class CulturalAwarenessLng extends TwitterLanguageDetector
{
	private static int MIN_NUM_TWEETS_TO_COUNT = 5; /* only languages with at least these many tweets are counted */
	
	@Override
	public RGLValue simpleExecute(ValueRow inputRow) 
	{
		RGLValue rdfValue = inputRow.get(INPUT_USERNAME);
		if (!rdfValue.isLiteral())
			return ValueFactory.createNull("Cannot handle URI input in "
					+ getFullName());

		// we are happy, value can be safely cast with .asLiteral().
		String username = rdfValue.asLiteral().getValueString();
		
		String uuid = "";
		RGLValue rdfValue2 = inputRow.get(INPUT_UUID);
		if(rdfValue2!=null)
			uuid = rdfValue2.asLiteral().getValueString();

		HashMap<String, Double> languageMap;
		try 
		{
			languageMap = detectLanguage(username);
		} catch (Exception e) 
		{
			return ValueFactory.createNull("Error in "
					+ this.getClass().getCanonicalName() + ": "
					+ e.getMessage());
		}

		int numLanguages = 0;
		for(String language : languageMap.keySet())
		{
			if( languageMap.get(language) >= MIN_NUM_TWEETS_TO_COUNT)
				numLanguages++;
		}
		
		HashMap<String, Double> map = new HashMap<String, Double>();
		map.put("",(double)numLanguages);
 
		RGLValue userProfile = null;
		try 
		{
			userProfile = UserProfileGenerator.generateProfile(this, (uuid.equals("")==true) ? username : uuid, map);
		} 
		catch (Exception e) 
		{
			return ValueFactory.createNull("Error in "
					+ this.getClass().getCanonicalName() + ": "
					+ e.getMessage());
		}
		return userProfile;
	}
}
