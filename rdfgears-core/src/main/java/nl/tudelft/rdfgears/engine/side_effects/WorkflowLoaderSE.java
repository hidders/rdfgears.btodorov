package nl.tudelft.rdfgears.engine.side_effects;

import java.util.HashSet;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import nl.tudelft.rdfgears.engine.Config;
import nl.tudelft.rdfgears.engine.Engine;
import nl.tudelft.rdfgears.engine.WorkflowLoader;
import nl.tudelft.rdfgears.rgl.exception.CircularWorkflowException;
import nl.tudelft.rdfgears.rgl.exception.WorkflowLoadingException;
import nl.tudelft.rdfgears.rgl.workflow.Workflow;
import nl.tudelft.rdfgears.rgl.workflow.WorkflowNode;
import nl.tudelft.wis.usem.plugin.access_management.PluginAccessManagerFactory;

import org.w3c.dom.NodeList;

public class WorkflowLoaderSE extends WorkflowLoader {

    public static Workflow loadWorkflow(String workflowId)
	    throws WorkflowLoadingException {
	Engine.init((Config) null);
	if (workflowId == null || workflowId.equals("")) {
	    throw new WorkflowLoadingException(
		    "No workflow was specified to load.");
	}

	// Make sure any plugin changes are applied
	PluginAccessManagerFactory.getPluginManager().refresh();

	WorkflowLoaderSE wLoader = new WorkflowLoaderSE(workflowId);
	return wLoader.getWorkflow();
    }

    public WorkflowLoaderSE(String workflowId) throws WorkflowLoadingException {
	super(workflowId);
    }

    protected WorkflowNode load() throws CircularWorkflowException,
	    WorkflowLoadingException {
	WorkflowNode output = super.load();

	try {
	    Set<WorkflowNode> nodesWithSideEffects = getSideEffectProcessors();

	    return new WorkflowNodeSE(output, nodesWithSideEffects);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new WorkflowLoadingException(e.getMessage());
	}
    }

    private Set<WorkflowNode> getSideEffectProcessors() throws Exception {
	XPathFactory xPathfactory = XPathFactory.newInstance();
	XPath xpath = xPathfactory.newXPath();
	XPathExpression expr = xpath
		.compile("//processor[function/config[@param = 'sideEffects'] = 'true']/@id");

	NodeList nl = (NodeList) expr.evaluate(getXmlDoc(), XPathConstants.NODESET);

	Set<WorkflowNode> nodesWithSideEffects = new HashSet<WorkflowNode>();

	for (int i = 0; i < nl.getLength(); i++) {
	    String nodeId = nl.item(i).getNodeValue();
	    recursivelyLinkInputs(nodeId);
	    nodesWithSideEffects.add(getNode(nodeId));
	}

	return nodesWithSideEffects;
    }

}
