package nl.tudelft.rdfgears.engine.side_effects;

import java.util.Set;

import nl.tudelft.rdfgears.rgl.datamodel.type.RGLType;
import nl.tudelft.rdfgears.rgl.datamodel.value.RGLValue;
import nl.tudelft.rdfgears.rgl.exception.WorkflowCheckingException;
import nl.tudelft.rdfgears.rgl.workflow.WorkflowNode;

/**
 * Wrapper
 *
 */
public class WorkflowNodeSE extends WorkflowNode {
    
    private WorkflowNode outputNode;
    private Set<WorkflowNode> nodesWithSideEffects;
    
    public WorkflowNodeSE(WorkflowNode outputNode, Set<WorkflowNode> nodesWithSideEffects) {
	super(outputNode.getId());
	this.outputNode = outputNode;
	this.nodesWithSideEffects = nodesWithSideEffects;
    }

    @Override
    public RGLValue getResultValue() {
	for(WorkflowNode node : nodesWithSideEffects){
	    //call toString() to load lazy values
	    node.getResultValue().toString();
	}
	
	return outputNode.getResultValue();
    }

    @Override
    public RGLType getOutputType() throws WorkflowCheckingException {
	return outputNode.getOutputType();
    }

    @Override
    public void resetProcessorCache() {
	for(WorkflowNode node : nodesWithSideEffects){
	    node.resetProcessorCache();
	}
	outputNode.resetProcessorCache();
    }

}
