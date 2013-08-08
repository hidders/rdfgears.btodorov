<%@page import="net.sf.json.JSONSerializer"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="nl.tudelft.wis.usem.plugin.admin.PluginAdminUIHandler"%>
<%@ page import="java.util.*"%>
<%
	String data = request.getParameter("data");

	JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(data);

	for (Object obj : jsonArray) {
		String plugin = (String) obj;
		if (plugin.endsWith(".jar")) {
	boolean flag = new PluginAdminUIHandler().installFromRepository(request, plugin);
	if (flag) {
		out.write("Plug-in: " + plugin
		+ " was succesfully installed. <br>");
	} else {
		out.write("Plug-in: " + plugin
		+ " failed to install. <br>");
	}
		}
	}
%>
