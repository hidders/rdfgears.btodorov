<%@page import="nl.tudelft.wis.usem.plugin.admin.PluginAdminUIHandler"%>
<%@ page import="java.util.*"%>
<%
	if (new PluginAdminUIHandler().installPlugin(request))
		out.write("<div class=\"success\">Plug-in successfully installed!</div>");
	else
		out.write("<div class=\"error\">Error installing plug-in!</div>");
%>
