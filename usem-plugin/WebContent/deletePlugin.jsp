<%@page import="nl.tudelft.wis.usem.plugin.admin.SessionUtils"%>
<%@page import="nl.tudelft.wis.usem.plugin.admin.PluginAdmin"%>
<%@page import="nl.tudelft.wis.usem.plugin.admin.PluginAdminFactory"%>
<%@page import="nl.tudelft.wis.usem.plugin.admin.PluginAdminUIHandler"%>
<%@ page import="java.util.*"%>
<%
	PluginAdmin pluginAdmin = SessionUtils.getPluginAdminFromSession(request.getSession());
	
	if (pluginAdmin.deletePlugin(
			request.getParameter("id")))
		out.write("<div class=\"success\">Plug-in successfully deleted!</div>");
	else
		out.write("<div class=\"error\">Error deleting plug-in!</div>");
%>
