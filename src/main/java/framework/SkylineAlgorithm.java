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
 * Superclass of every Skyline-Algorithm.
 * New algorithms need to extend this class to work with the Framework.
 * @author Sven Wiemann
 *
 */
public abstract class SkylineAlgorithm {
	protected TimeObject time;
	
	// These Variables could be overwritten in inheriting classes
	// true if the InputFile must be read in every Iteration, i.e. the InputFile is overwritten or
	// removed in an Iteration of the Algorithm
	public boolean readFileEveryIteration = false;
	// True if the Data Structure (Tree) is destroyed in one Iteration of the Skyline-Algorithm 
	public boolean buildDataStructureEveryIteration = true;
	// Should we return the complete Skyline-List or only the Size?
	public boolean returnCompleteSkyline = true;
	public boolean deepAnalysis = true;
	
	public SkylineAlgorithm(TimeObject time) {
		this.time = time;
	}
	
	/**
	 * Start one complete Calculation.
	 * @param list
	 * @param threadCount
	 * @return
	 */
	public abstract Result calc(TupleList list, int threadCount);
	
	/**
	 * Some Algorithms don't need to build up their Data-structure every iteration. This method is called
	 * for later (not the first) iterations in repeated test-cases.  
	 * @param list
	 * @param threadCount
	 * @return
	 */
	public abstract Result repeatCalc(TupleList list, int threadCount);

	// Getter and Setter
	public TimeObject getTime() {
		return time;
	}

	public boolean isReadFileEveryIteration() {
		return readFileEveryIteration;
	}

	public void setReadFileEveryIteration(boolean readFileEveryIteration) {
		this.readFileEveryIteration = readFileEveryIteration;
	}

	public boolean isBuildDataStructureEveryIteration() {
		return buildDataStructureEveryIteration;
	}

	public void setBuildDataStructureEveryIteration(boolean buildDataStructureEveryIteration) {
		this.buildDataStructureEveryIteration = buildDataStructureEveryIteration;
	}

	public boolean isReturnCompleteSkyline() {
		return returnCompleteSkyline;
	}

	public void setReturnCompleteSkyline(boolean returnCompleteSkyline) {
		this.returnCompleteSkyline = returnCompleteSkyline;
	}

	public boolean isDeepAnalysis() {
		return deepAnalysis;
	}

	public void setDeepAnalysis(boolean deepAnalysis) {
		this.deepAnalysis = deepAnalysis;
	}

}
