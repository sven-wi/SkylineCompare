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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import exceptions.NoAlgorithmsSetException;
import framework.Result;
import framework.TupleList;
import inputHandling.InputHandler;
import output.TikzTex;

/**
 * Calls the Benchmark-Testcase with different TupleCounts.
 * @author Sven Wiemann
 *
 */
public class DifferentDatasizeBenchmark extends Benchmark{
	// The increase of the Tuple size, and the maximal Tuple size
	public int offset = 100000, maxSize = 1000000;
	
	public DifferentDatasizeBenchmark() {
		super();
		dimensions = 5;
		tupleCount = 100000;
		// We are only interested in the average runtimes.
		printEveryValue = false;
		printMedian = false;
	}
	
	/**
	 * Generates the test case with custom settings. 
	 * @param startSize
	 * @param offset The increase added to the tuple size after each iteration
	 * @param maxSize
	 */
	public DifferentDatasizeBenchmark(int startSize, int offset, int maxSize) {
		super();
		dimensions = 5;
		tupleCount = startSize;
		this.offset = offset;
		this.maxSize = maxSize;
		tupleCount = startSize;
		// We are only interested in the average runtimes.
		printEveryValue = false;
		printMedian = false;
	}
	
	public List<Result> run(InputHandler handler, int repetitions) throws SecurityException, IOException, NoAlgorithmsSetException {
		List<Result> results = new ArrayList<Result>();
		while(tupleCount <= maxSize){
			// Delegate everything to the Superclass
			results.addAll(super.run(handler, repetitions));
			tupleCount = tupleCount + offset;
		}
		return results;
	}

	public List<Result> run(TupleList tupleList, int repetitions) throws SecurityException, IOException, NoAlgorithmsSetException {
		List<Result> results = new ArrayList<Result>();
		while(tupleCount <= maxSize){
			// Delegate everything to the Superclass
			results.addAll(super.run(tupleList, repetitions));
			tupleCount = tupleCount + offset;
		}
		return results;
	}
	
	/**
	 * Generate the Output Graphics, based on the output-result-log.
	 */
	@Override
	public void genOutput() {
		TikzTex pgf = new TikzTex(outputDir);
		try {
			File file;
			// TupleListsize vs Runtime Test
			file = pgf.insertAlgorithmResults(new File("src/main/resources/texTemplates/template.tex"), outputFile, 
					"Tuples", "Laufzeitvergleich: Verschiedene Tupelanzahlen", "Anzahl der Tupel");
			pgf.genPdf(file);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
