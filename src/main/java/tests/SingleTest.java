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
package tests;

import framework.Result;
import framework.SkylineAlgorithm;
import framework.Tuple;
import framework.TupleList;
import inputHandling.InputHandler;
import inputHandling.InputHandler.DataGenInterface;
import inputHandling.InputHandler.FileInputInterface;
import preferences.Preference;

/**
 * TestCase: Runs one Testiteration of one Algorithm, and prints the Result if needed.
 * @author Sven Wiemann
 *
 */
public abstract class SingleTest extends TestCase {
	// Parameters. These could be overridden in child-Tests.
	public int threadCount = 4;
	// Should we print the Result of every Test into the output File?
	public boolean printEveryValue = true;
	public int tupleCount = 10000;
	public int dimensions = 6;
	public Preference[] preferences = null;

	@Override
	public Result run(InputHandler handler, SkylineAlgorithm algorithm, boolean firstIteration){
		TupleList tupleList = null;
		// Input
		if(handler instanceof FileInputInterface){
			tupleList = ((FileInputInterface) handler).readFile();
		}
		else if(handler instanceof DataGenInterface){
			tupleList = ((DataGenInterface) handler).genData(dimensions, tupleCount);
		}
		// Add new Handlerconfigs here
		
		// Preference Handling
		if(preferences != null) {
			for(Tuple tuple : tupleList) {
				for (int i = 0; i < dimensions; i++) {
					if(preferences[i] != null){
						tuple.setValue(i, preferences[i].calc(tuple.getValue(i)));
					}
				}
			}
		}
		Result result;
		// Use the Algorithm
		if(firstIteration)
			result = algorithm.calc(tupleList, threadCount);
		else
			result = algorithm.repeatCalc(tupleList, threadCount);
		
		if (printEveryValue)
			printResults(result, algorithm);
		return result;
	}

	@Override
	public Result run(TupleList tupleList, SkylineAlgorithm algorithm, boolean firstIteration){
		// Preference Handling
		if(preferences != null) {
			for(Tuple tuple : tupleList) {
				for (int i = 0; i < dimensions; i++) {
					if(preferences[i] != null){
						tuple.setValue(i, preferences[i].calc(tuple.getValue(i)));
					}
				}
			}
		}
		
		Result result;
		// Use the Algorithm
		if(firstIteration)
			result = algorithm.calc(tupleList, threadCount);
		else
			result = algorithm.repeatCalc(tupleList, threadCount);
		
		if (printEveryValue)
			printResults(result, algorithm);
		return result;
	}
	
	/**
	 * Print the Result of this Calculation into the Output-Log
	 * @param result
	 * @param algorithm
	 */
	public void printResults(Result result, SkylineAlgorithm algorithm){
		// Add the Result Output-Logger
		logger.getParent().addHandler(fileHandler);
			
		String str = String.format("%3d\t%5d\t%8d\t%16d\t%16d\t%5s\t%s", 
				threadCount, dimensions, tupleCount, result.getTimeobject().getCalcTime(), result.getTimeobject().getInputTime(), "val", algorithm.getClass().getSimpleName());
		logger.info(str);
		// Remove the Handler, the Result File should contain only Results
		logger.getParent().removeHandler(fileHandler);
	}
}
