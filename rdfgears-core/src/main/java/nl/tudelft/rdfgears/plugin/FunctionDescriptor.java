package nl.tudelft.rdfgears.plugin;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import nl.tudelft.rdfgears.rgl.function.RGLFunction;

public class FunctionDescriptor {
	
	public String functionDescriptor;
	private Class<? extends RGLFunction> functionClass;
	
	public FunctionDescriptor(InputStream functionDescriptor, Class<? extends RGLFunction> functClass) {
		this.functionClass = functClass;
		this.functionDescriptor = convertStreamToString(functionDescriptor);
	}
	
	public FunctionDescriptor(String functionDescriptor, Class<? extends RGLFunction> functClass) {
		this.functionDescriptor = functionDescriptor;
		this.functionClass = functClass;
	}

	public InputStream asInputStream() {
		try {
			return new ByteArrayInputStream(functionDescriptor.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String asString() {
		return functionDescriptor;
	}
	
	public Class<? extends RGLFunction> getFunctionClass() {
		return functionClass;
	}
	
	public static String convertStreamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}

}
