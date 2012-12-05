package nl.tudelft.wis.usem.service;

import java.io.File;

public class ServicesUIHandler {
	
	private static final String REST_INPUT_URL = "../../rdfgears-rest/user/input/"; 
	
	public static String getServicesHTML(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("<li class=\"nav-header\">");
		sb.append("Group 1");
		sb.append("</li>");
		
		File workflowsDir = new File(getWritableDir());
		
		if(workflowsDir.isDirectory()){
			for(File w : workflowsDir.listFiles()){
				if(w.isFile()){
					sb.append("<li>");
					sb.append("<a onclick=\"navigate('" + REST_INPUT_URL + w.getName().replace(".xml", "")  + "')\">");
					sb.append(w.getName().replace(".xml", ""));
					sb.append("</a>");
					sb.append("</li>");
				}
			}
		}
		
		return sb.toString();
	}
	
	private static String getWritableDir() {
		String path = System.getProperty("java.io.tmpdir")+"/rdfgears/data/workflows/";
		File dir = new File(path);
		dir.mkdirs();
		return path;
	}
}
