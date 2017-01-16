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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.rank.Median;

import exceptions.SkylineSizeException;
import framework.Result;
import framework.SkylineAlgorithm;
import framework.TupleList;
import inputHandling.InputHandler;

/**
 * TestCase: Calls SingleTest for one Algorithm several times.
 * @author Sven Wiemann
 *
 */
public abstract class RepeatTest extends SingleTest{
	// Should this Test calculate the Averages or Median Runtimes?
	public boolean calcAverages = true;
	public boolean printAverage = true;
	public boolean printMedian = true;
	
	public List<Result> run(InputHandler handler, SkylineAlgorithm algorithm, int repetitions) throws SecurityException, IOException {
		List<Result> results = new ArrayList<Result>();
		// Delegate everything to the Superclass
		results.add(super.run(handler, algorithm, true));
		for (int i = 1; i < repetitions; i++)			
			results.add(super.run(handler, algorithm, false));
		if (calcAverages)
			printResults(results, algorithm);
		try {
			// check the results: Skyline valid?
			checkResults(results);
		} catch (SkylineSizeException e) {
			logger.severe("Calculation with wrong Skyline Size received!");
			e.printStackTrace();
		}
		return results;
	}

	public List<Result> run(TupleList tupleList, SkylineAlgorithm algorithm, int repetitions) throws SecurityException, IOException {
		List<Result> results = new ArrayList<Result>();
		// Delegate everything to the Superclass
		results.add(super.run(tupleList, algorithm, true));
		for (int i = 1; i < repetitions; i++)			
			results.add(super.run(tupleList, algorithm, false));
		if (calcAverages)
			printResults(results, algorithm);
		try {
			// check the results: Skyline valid?
			checkResults(results);
		} catch (SkylineSizeException e) {
			logger.severe("Calculation with wrong Skyline Size received!");
			e.printStackTrace();
		}
		return results;
	}
	
	/**
	 * Are the Skyline sizes of the given Results equal?
	 * @param results
	 * @throws SkylineSizeException
	 */
	public void checkResults(List<Result> results) throws SkylineSizeException {
		int skylineSize = results.get(0).getSkylineSize();
		for (int i = 1; i < results.size(); i++) {
			if(skylineSize != results.get(i).getSkylineSize()){
				throw new SkylineSizeException();
			}
				
		}
	}
	
	/**
	 * Prints the Average/Median Runtimes of this Testcase into the Result-Output-Logger.
	 * @param results
	 * @param algorithm
	 * @throws SecurityException
	 * @throws IOException
	 */
	public void printResults(List<Result> results, SkylineAlgorithm algorithm) throws SecurityException, IOException {
		// Add the Result Output-Logger
		logger.getParent().addHandler(fileHandler);
		
		// Calculation
		int repeatCount = results.size();
		long timeComp = 0, timeInput = 0;
		double[] compTimes = new double[repeatCount], inputTimes = new double[repeatCount];
		
		for (int i = 0; i < repeatCount; i++){
			// Big RepeatCounts? Remove the first Calculations, to reduce the impact of the non-JIT-optimized Calculations
			if(i > 2 || repeatCount <= 3) { 
				timeComp = timeComp + results.get(i).getTimeobject().getCalcTime();
				timeInput = timeInput + results.get(i).getTimeobject().getInputTime();				
			}
			compTimes[i] = results.get(i).getTimeobject().getCalcTime();
			inputTimes[i] = results.get(i).getTimeobject().getInputTime();
		}
		if (repeatCount > 3) {
			repeatCount = repeatCount - 3;
		}
		long avgComp = timeComp/(repeatCount);
		long avgInpt = timeInput/(repeatCount);
		if (!algorithm.buildDataStructureEveryIteration)
			avgInpt = (long) inputTimes[0];

		
		// Average
		String str = "";
		if(printAverage){
			str = String.format("%3d\t%5d\t%8d\t%16d\t%16d\t%5s\t%s",  
					threadCount, dimensions, tupleCount, avgComp, avgInpt, "avg", algorithm.getClass().getSimpleName());
			logger.info(str);
		}
		
		// Median
		if(printMedian){
			Median med = new Median();
			Double medCalc;
			Double medInpt;
			if(repeatCount > 3) {
				medCalc = med.evaluate(compTimes, 3, repeatCount);
				medInpt = med.evaluate(inputTimes, 3, repeatCount);
			}
			else {
				medCalc = med.evaluate(compTimes, 0, compTimes.length);
				medInpt = med.evaluate(inputTimes, 0, inputTimes.length);
			}
				
			if (!algorithm.buildDataStructureEveryIteration)
				medInpt = inputTimes[0];
			str = String.format("%3d\t%5d\t%8d\t%16.0f\t%16.0f\t%5s\t%s",  
					threadCount, dimensions, tupleCount, medCalc, medInpt, "med", algorithm.getClass().getSimpleName());
			logger.info(str);
		}
		
		// Remove the Handler, the Result File should contain only Results
		logger.getParent().removeHandler(fileHandler);
	}
}
