package nl.tudelft.rdfgears.rgl.datamodel.value.tests;

import nl.tudelft.rdfgears.engine.ValueFactory;
import nl.tudelft.rdfgears.rgl.datamodel.value.LiteralValue;
import nl.tudelft.rdfgears.rgl.datamodel.value.impl.ModifiableRecord;
import nl.tudelft.rdfgears.util.row.FieldIndexMap;
import nl.tudelft.rdfgears.util.row.FieldIndexMapFactory;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestValues {
	private final static String field1 = "a_field1"; /* non-alphabetically sorted list */
	private final static String field2 = "d_field2";
	private final static String field3 = "c_field3";
	private final static String field4 = "b_field4";

	@Before 
	public void initialize() {
		/* init */
	}
	
	
	@Test  
	public void testNothing(){
		assertTrue(true);
	}
	
	/**
	 * test for bug http://code.google.com/p/rdfgears/issues/detail?id=9
	 * 
	 */
    @Ignore("issue 9 not fixed yet. ")
	@Test  
	public void testRecordOrder(){
		FieldIndexMap fiMap = FieldIndexMapFactory.create(field1, field2, field3, field4);
		ModifiableRecord rec = ValueFactory.createModifiableRecordValue(fiMap);
		
		LiteralValue aLiteral = ValueFactory.createLiteralPlain("lit", null);
		
		rec.put(field1, aLiteral);
		rec.put(field2, aLiteral);
		rec.put(field3, aLiteral);
		rec.put(field4, aLiteral);
		
		int i=0;
		for (String s : rec.getRange()){
			switch(i){
			case(0):
				assertTrue("should be "+field1+" but is "+s, s.equals(field1));
				break;
			case(1):
				assertTrue("should be "+field2+" but is "+s, s.equals(field2));
				break;
			case(2):
				assertTrue("should be "+field3+" but is "+s, s.equals(field3));
				break;
			case(3):
				assertTrue("should be "+field4+" but is "+s, s.equals(field4));
				break;
			}
			i++;
		}
	}
	
	
	
}
