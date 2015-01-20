/* Copyright (c) 2013 IBM Corporation and other Contributors

 All rights reserved. This program and the accompanying materials
 are made available under the terms of the Eclipse Public License v1.0
 which accompanies this distribution, and is available at
 http://www.eclipse.org/legal/epl-v10.html
 
 Contributors:
     IBM - initial API and implementation */
package com.ibm.fem.control;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;

public class StopFlow_SleepWhileAsyncStop extends MbJavaComputeNode {
//	int delay;
//	public int getDelay() {
//		return delay;
//	}
//	public void setDelay(int delay) {
//		this.delay = delay;
//	}
//	public void onInitialize() throws MbException
//	{
	   // access the user-defined properties here
//		int delayOnStop = (int) getUserDefinedAttribute("delayOnStop");
//		setDelay(delayOnStop);
//	}
	public void evaluate(MbMessageAssembly assembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");

		try {
			MbMessage message = assembly.getMessage();
			// ----------------------------------------------------------
			// Add user code below
			try {
//			    Thread.sleep(getDelay());                 //1000 milliseconds is one second.
			    Thread.sleep(3000);                 //not sure we even need this node at all.
			    
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
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
