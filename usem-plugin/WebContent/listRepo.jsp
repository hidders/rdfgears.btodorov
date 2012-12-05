<%@page import="nl.tudelft.wis.usem.plugin.repository.PluginRepositoryFactory"%>
<%@page import="nl.tudelft.wis.usem.plugin.repository.localrepository.LocalPluginRepository"%>
<%
	out.write(PluginRepositoryFactory.getRepository().listRepositoryContents(null));
%>