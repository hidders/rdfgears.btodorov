package nl.tudelft.wis.datamanagement.backend;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import nl.tudelft.wis.datamanagement.api.entitydef.EntityDescriptor;
import nl.tudelft.wis.datamanagement.backend.datatypes.DataTypesPersister;

public class EnitiesUIHandler {

	public JSONArray getAllEntityNames(){
		try {
			List<EntityDescriptor> descriptors = new DataTypesPersister()
					.getAll();
			
			JSONArray entities = new JSONArray();
			
			for(EntityDescriptor ed : descriptors){
				JSONObject entity = new JSONObject();
				
				entity.accumulate("title", ed.getName());
				
				entities.add(entity);
			}

			return entities;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public JSONArray getAllEntities(){
		try {
			List<EntityDescriptor> descriptors = new DataTypesPersister()
					.getAll();
			
			JSONArray entities = new JSONArray();
			
			for(EntityDescriptor ed : descriptors){
				JSONObject jsonEntity = new EntityDescriptorJSONBuilder().parse(ed);
				
				entities.add(jsonEntity);
			}

			return entities;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(new EnitiesUIHandler().getAllEntityNames());
	}

}
