package nl.tudelft.rdfgears.plugin;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class WorkflowTemplate {

public String workflowTemplate;
	
	public WorkflowTemplate(InputStream workflowTemplate) {
		this.workflowTemplate = FunctionDescriptor.convertStreamToString(workflowTemplate);
	}
	
	public WorkflowTemplate(String workflowTemplate) {
		this.workflowTemplate = workflowTemplate;
	}

	public InputStream asInputStream() {
		try {
			return new ByteArrayInputStream(workflowTemplate.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String asString() {
		return workflowTemplate;
	}
}
