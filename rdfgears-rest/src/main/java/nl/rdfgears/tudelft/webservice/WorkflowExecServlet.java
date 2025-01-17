package nl.rdfgears.tudelft.webservice;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import nl.rdfgears.tudelft.webservice.requests.WorkflowExecutionRequest;
import nl.rdfgears.tudelft.webservice.requests.WorkflowExecutionRequestGET;
import nl.rdfgears.tudelft.webservice.requests.WorkflowRequest;
import nl.tudelft.rdfgears.engine.Engine;
import nl.tudelft.rdfgears.engine.Optimizer;
import nl.tudelft.rdfgears.engine.WorkflowLoader;
import nl.tudelft.rdfgears.rgl.datamodel.type.RDFType;
import nl.tudelft.rdfgears.rgl.datamodel.type.RGLType;
import nl.tudelft.rdfgears.rgl.datamodel.value.RGLValue;
import nl.tudelft.rdfgears.rgl.datamodel.value.visitors.ImRealXMLSerializer;
import nl.tudelft.rdfgears.rgl.datamodel.value.visitors.ValueSerializer;
import nl.tudelft.rdfgears.rgl.exception.FunctionTypingException;
import nl.tudelft.rdfgears.rgl.exception.WorkflowCheckingException;
import nl.tudelft.rdfgears.rgl.exception.WorkflowLoadingException;
import nl.tudelft.rdfgears.rgl.workflow.Workflow;
import nl.tudelft.rdfgears.util.ValueParser;
import nl.tudelft.rdfgears.util.row.HashValueRow;
import nl.tudelft.rdfgears.util.row.TypeRow;

/**
 * Servlet implementation class WorkflowExecServlet
 */
public class WorkflowExecServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletOutputStream output;
     
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WorkflowExecServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getAttribute("javax.servlet.forward.servlet_path")==null){
			throw new RuntimeException("Don't call this path directly; call the dispatcher. ");
		}

		output = response.getOutputStream();
		
//		String workflowId = WorkflowTools.getWorkflowId(request);
		
		WorkflowExecutionRequest execRequest ;
		try {
			execRequest = new WorkflowExecutionRequestGET(request, response);
			execRequest.configureWorkflowInputs();
			execRequest.typeCheck();
			execRequest.execute();
							
		} catch (WorkflowLoadingException e) {
			output.print("Error: ");
			output.print(e.getMessage());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			output.print("Error: ");
			output.print(e.getMessage());
			e.printStackTrace();
		} catch (WorkflowCheckingException e) {
			output.print("Error: ");
			
			/** Code duplication from RDFGears.java command line tool */ 
			
			output.println("The workflow is not executable, as it did not pass the typechecking test: ");
		
			WorkflowCheckingException rootCause = e.getRootCause();
			if (rootCause instanceof FunctionTypingException) {
				FunctionTypingException fEx = (FunctionTypingException) rootCause;
				if (fEx.isIterationProblem())
					output.println("I think you forgot an iteration marker somewhere! ");
			}
			output.println(e.getProblemDescription());
			
		}
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	

}
