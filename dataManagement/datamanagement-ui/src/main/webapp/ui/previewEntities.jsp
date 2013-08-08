<%@page import="nl.tudelft.wis.datamanagement.backend.EnitiesUIHandler"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page
	import="nl.tudelft.wis.datamanagement.backend.EntityDefinitionEndpoint"%>
<%@page import="net.sf.json.JSONSerializer"%>
<%@page import="net.sf.json.JSONArray"%>
<%@ page import="java.util.*"%>
<%
    JSONArray jsonObj = new EnitiesUIHandler().getAllEntities();
		
	out.write(jsonObj.toString());
%>
