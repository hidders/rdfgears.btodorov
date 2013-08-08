<%@page import="nl.tudelft.wis.usem.plugin.repository.localrepository.LocalPluginRepository"%>
<%@page
	import="nl.tudelft.wis.usem.plugin.repository.PluginRepositoryFactory"%>
<%@page
	import="org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory"%>
<%@page
	import="org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.tomcat.util.http.fileupload.FileItem"%>
<%@page import="java.io.InputStream"%>
<%@ page import="java.util.*"%>
<%
	try {
		String inputFolder = null;

		InputStream plugin = null;
		String pluginName = null;

		List<FileItem> items = new ServletFileUpload(
				new DiskFileItemFactory()).parseRequest(request);
		for (FileItem item : items) {
			if (item.isFormField()) {
				String fieldname = item.getFieldName();

				if (fieldname.equals("folder")) {
					inputFolder = item.getString();
				}
 
			} else {
				if (item.getFieldName().equals("pluginfile")) {
					pluginName = item.getName();
					plugin = item.getInputStream();
				}
			}
		}

		if (new LocalPluginRepository().publishlPlugin(
				inputFolder, pluginName, plugin))
			out.write("<div class=\"success\">Plug-in successfully published!</div>");
		else
			out.write("<div class=\"error\">Error publishing plug-in!</div>");
	} catch (Throwable t) {
		t.printStackTrace();
		out.write("<div class=\"error\">Error publishing plug-in!</div>");
	}
%>
