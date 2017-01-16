/*
 * Copyright 2016 Sven Wiemann
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use 
 * this file except in compliance with the License. You may obtain a copy of the 
 * License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed 
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES 
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for 
 * the specific language governing permissions and limitations under the License.
 */
package framework;

/**
 * Objects that measures the runtime of one Calculation.
 * If an algorithm needs more different Calculation Intervals create an algorithm specific Extension.
 * StartInput => FinishInput => StartCalc => FinishCalc
 * @author Sven Wiemann
 *
 */
public class TimeObject {
	protected long startInput, finishInput, startCalc, finishCalc;
	
	public String print() {
		return "" + startInput + ", " + finishInput + ", " + startCalc + ", " + finishCalc;
	}
	
	/**
	 * The Total Time used for the Calculation, includes the building time of necessary Data Structures
	 * @return
	 */
	public long getTotalTime() {
		return finishCalc - startInput;
	}
	
	/**
	 * The Time used for the raw Calculation of one Iteration
	 * @return
	 */
	public long getCalcTime() {
		return finishCalc - startCalc;
	}
	
	/**
	 * The Building Time of necessary Data Structures 
	 * @return
	 */
	public long getInputTime() {
		return finishInput - startInput;
	}

	/**
	 * Time Index, when did we start the Input processing
	 * @return
	 */
	public long getStartInput() {
		return startInput;
	}

	/**
	 * Time Index, when did we finish the Input processing
	 * @return
	 */
	public long getFinishInput() {
		return finishInput;
	}

	/**
	 * Time Index, when did we start with the Calculation
	 * @return
	 */
	public long getStartCalc() {
		return startCalc;
	}

	/**
	 * Time Index, when did we finish with the Calculation
	 * @return
	 */
	public long getFinishCalc() {
		return finishCalc;
	}

	/**
	 * The Input processing starts now
	 */
	public void setStartInput() {
		this.startInput = System.nanoTime();
	}

	/**
	 * The Input processing ends now
	 */
	public void setFinishInput() {
		this.finishInput = System.nanoTime();
	}

	/**
	 * The Calculation starts now
	 */
	public void setStartCalc() {
		this.startCalc = System.nanoTime();
	}
	
	/**
	 * The Calculation ends now
	 */
	public void setFinishCalc() {
		this.finishCalc = System.nanoTime();
	}

}
