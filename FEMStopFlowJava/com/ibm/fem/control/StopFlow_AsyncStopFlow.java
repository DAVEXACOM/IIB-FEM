package com.ibm.fem.control;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;
import com.ibm.broker.config.proxy.*;

public class StopFlow_AsyncStopFlow extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly assembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");

		try {
			MbMessage message = assembly.getMessage();
			// ----------------------------------------------------------
			// Add user code below
			
// Use local properties available through CMP API for broker and execution group	
			     		     
			     BrokerProxy bLocal = BrokerProxy.getLocalInstance();
			     ExecutionGroupProxy eLocal = ExecutionGroupProxy.getLocalInstance();

// retrieve the application and flow names from the Environment where they were place via ESQL code in the preceding Compute node			     
			     String APPLNAME = assembly.getGlobalEnvironment().getRootElement().getFirstElementByPath("APPLNAME").getValueAsString();
			     String FLOWNAME = assembly.getGlobalEnvironment().getRootElement().getFirstElementByPath("FLOWNAME").getValueAsString();
// attempt to get a handle to the application			     
			     ApplicationProxy aLocal = eLocal.getApplicationByName(APPLNAME);
			     if (aLocal != null) {
			    	 // if we found an application container get the handle to the message flow from it
				     MessageFlowProxy mLocal = aLocal.getMessageFlowByName(FLOWNAME);
				     // request that the broker stop the flow.
				     mLocal.stop();
				} else {
					// if we did not find an application container get the handle to the message flow from the execution group 
				     MessageFlowProxy mLocal = eLocal.getMessageFlowByName(FLOWNAME);
				     // request that the broker stop the flow.
				     mLocal.stop();
				}
			// End of user code			     
			// ----------------------------------------------------------
		} catch (MbException e) {
			// Re-throw to allow Broker handling of MbException
			throw e;
		} catch (RuntimeException e) {
			// Re-throw to allow Broker handling of RuntimeException
			throw e;
		} catch (Exception e) {
			// Consider replacing Exception with type(s) thrown by user code
			// Example handling ensures all exceptions are re-thrown to be handled in the flow
			throw new MbUserException(this, "evaluate()", "", "", e.toString(),
					null);
		}
		// Change following to propagate the message to the 'out' or 'alt' terminal
		out.propagate(assembly);
	}
}
//bLocal.setSynchronous(10000); //??? CAN WE USE THIS INSTEAD OF A Java SLEEP in SleepWhileAsync JavaCompute?????
//public void setSynchronous(int timeToWaitMs)Configures whether the IBM Integration API (CMP) should wait for broker configuration changes
//to be fully completed before returning to the caller. 
//For all new IBM Integration API (CMP) applications*, the default behavior is for all property change methods to return 
//immediately after the change request has been queued for processing at the broker. This means that if a method to return a 
//property's value is called immediately after setting it, the old value is typically returned as the request is unlikely yet
//to have been processed and a response returned. 
//A positive timeToWaitMs parameter to this method causes configuration change methods to pause until a positive or negative
//response is received by the broker, which means that a successful return from the property change method implies
//that the broker successfully processed the request. In this case, if the configuration change request is rejected by the broker,
//a ConfigManagerProxyRequestFailedException is thrown. If the broker does not respond in the configured time period, 
//a ConfigManagerProxyRequestTimeoutException is thrown (which does not mean that the request to set the property failed
