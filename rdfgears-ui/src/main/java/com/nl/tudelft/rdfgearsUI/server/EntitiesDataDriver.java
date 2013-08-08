package com.nl.tudelft.rdfgearsUI.server;

import java.util.List;

import nl.tudelft.wis.datamanagement.api.entitydef.EntityDescriptor;
import nl.tudelft.wis.datamanagement.api.entitydef.Field;
import nl.tudelft.wis.datamanagement.backend.datatypes.DataTypesPersister;

public class EntitiesDataDriver {
	private ConfigurationDataDriver configurationDataDriver;

	public EntitiesDataDriver(ConfigurationDataDriver configurationDataDriver) {
		this.configurationDataDriver = configurationDataDriver;
	}

	public String getListOfEntities() {
		
		List<EntityDescriptor> all;
		try {
			all = new DataTypesPersister().getAll();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<source>");
		
		for(EntityDescriptor ed : all){
			sb.append("<option value=\"" + ed.getName() + "\" label=\"" + ed.getName() + "\">");
				for(Field p : ed.getEntity().getProperties()){
					sb.append("<data name=\"" + p.getName() +"\" label=\"" + p.getName() +"\" iterate=\"false\"/>");
				}
			sb.append("</option>");
			
		}
		
		sb.append("</source>");
//					"<type>" +
//						"<var name=\"test\"/> " +
//					"</type>" +
		return sb.toString();
	}
}
