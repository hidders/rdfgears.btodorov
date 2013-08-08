<%@page import="net.sf.json.JSONObject"%>
<%@page
	import="nl.tudelft.wis.datamanagement.backend.EntityDefinitionEndpoint"%>
<%@page import="net.sf.json.JSONSerializer"%>
<%@page import="net.sf.json.JSONArray"%>
<%@ page import="java.util.*"%>
<%
	String data = request.getParameter("data");

	JSONObject jsonObj = new EntityDefinitionEndpoint().read(data);
		
	out.write(jsonObj.toString());
%>
