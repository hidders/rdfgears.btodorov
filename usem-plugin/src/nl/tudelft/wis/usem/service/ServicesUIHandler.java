package nl.tudelft.wis.usem.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ServicesUIHandler {

	private static final String REST_INPUT_URL = "../../rdfgears-rest/user/input/";

	public String getServicesHTML() {
		StringBuilder sb = new StringBuilder();

		Map<String, List<WorkflowDesc>> workflowDirContent = getWorkflowDirContent();

		for (String cat : workflowDirContent.keySet()) {

			sb.append("<li class=\"nav-header\">");
			sb.append(cat);
			sb.append("</li>");

			for (WorkflowDesc w : workflowDirContent.get(cat)) {
				sb.append("<li>");
				sb.append("<a onclick=\"navigate('" + REST_INPUT_URL
						+ w.getId() + "')\">");
				sb.append(w.getName());
				sb.append("</a>");
				sb.append("</li>");
			}

		}

		return sb.toString();
	}

	private Map<String, List<WorkflowDesc>> getWorkflowDirContent() {
		File operatorDir = new File(getWritableDir());
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Map<String, List<WorkflowDesc>> categories = new HashMap<String, List<WorkflowDesc>>();

		try {
			if (operatorDir.isDirectory()) {
				File operators[] = operatorDir.listFiles();

				dBuilder = dbFactory.newDocumentBuilder();
				for (File op : operators) {
					if (op.isFile()) {
						Document d = dBuilder.parse(op);
						d.getDocumentElement().normalize();
						Element wf = (Element) d.getElementsByTagName(
								"rdfgears").item(0);

						Element meta = (Element) wf.getElementsByTagName(
								"metadata").item(0);
						String id = meta.getElementsByTagName("id").item(0)
								.getTextContent();
						String name = meta.getElementsByTagName("name").item(0)
								.getTextContent();

						if (meta.getElementsByTagName("category").getLength() > 0) {
							String cat = meta.getElementsByTagName("category")
									.item(0).getTextContent();
							if (cat.trim().length() > 0) {
								if (!categories.containsKey(cat)) {
									categories
											.put(cat,
													new ArrayList<ServicesUIHandler.WorkflowDesc>());
								}
								categories.get(cat).add(
										new WorkflowDesc(id, name));
							}
						}
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return categories;
	}

	private static String getWritableDir() {
		String path = System.getProperty("java.io.tmpdir")
				+ "/rdfgears/data/workflows/";
		File dir = new File(path);
		dir.mkdirs();
		return path;
	}

	private class WorkflowDesc {
		private String id;
		private String name;

		public WorkflowDesc(String id, String name) {
			super();
			this.id = id;
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public String getId() {
			return id;
		}
	}
}
