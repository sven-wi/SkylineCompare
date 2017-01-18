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

import exceptions.NoAlgorithmsSetException;
import exceptions.SkylineSizeException;
import framework.Result;
import framework.SkylineAlgorithm;
import framework.TupleList;
import inputHandling.InputHandler;

/**
 * Calls RepeatTest on several Algorithms
 * @author Sven Wiemann
 *
 */
public abstract class Benchmark extends RepeatTest{
	// The List of Algorithms which should be used in this Test
	public List<SkylineAlgorithm> algorithms;

	/**
	 * Creates this Test Case, normal values
	 * @param algorithms the List of Algorithms which should be used in this Test
	 */
	public Benchmark(List<SkylineAlgorithm> algorithms) {		
		this.algorithms = algorithms;		
	}
	
	/**
	 * Creates this Test Case, normal values
	 */
	public Benchmark() {
		algorithms = new ArrayList<SkylineAlgorithm>();
		// Add your Algorithms here 
		// Example: algorithms.add(new SkylineAlgorithm());
	}

	/**
	 * Runs the whole Benchmark with the given Inputhandler
	 * @param handler the type of input which should be used in this Benchmark
	 * @param repetitions
	 * @return a Result object for each Iteration
	 * @throws SecurityException
	 * @throws IOException
	 * @throws NoAlgorithmsSetException 
	 */
	public List<Result> run(InputHandler handler, int repetitions) throws SecurityException, IOException, NoAlgorithmsSetException {
		if(algorithms.size() == 0)
			throw new NoAlgorithmsSetException();
		List<Result> results = new ArrayList<Result>();
		for (SkylineAlgorithm algorithm : algorithms) {
			// delegate everything to the superclasses
			results.addAll(super.run(handler, algorithm, repetitions));
			algorithm = null;
		}
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
	 * Runs the whole Benchmark with the given TupleList
	 * @param tupleList
	 * @param repetitions
	 * @return
	 * @throws SecurityException
	 * @throws IOException
	 * @throws NoAlgorithmsSetException 
	 */
	public List<Result> run(TupleList tupleList, int repetitions) throws SecurityException, IOException, NoAlgorithmsSetException {
		if(algorithms.size() == 0)
			throw new NoAlgorithmsSetException();
		List<Result> results = new ArrayList<Result>();
		for (SkylineAlgorithm algorithm : algorithms) {
			// delegate everything to the superclasses
			results.addAll(super.run(tupleList, algorithm, repetitions));
			algorithm = null;
		}
		return results;
	}
	
	// Output is handled by the super classes and inherited classes
}
