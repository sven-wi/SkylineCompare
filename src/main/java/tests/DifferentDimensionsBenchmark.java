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
 * Calls the Benchmark-Testcase with different dimension counts.
 * @author Sven Wiemann
 *
 */
public class DifferentDimensionsBenchmark extends Benchmark{
	// The increase of the dimension count, and the maximal Dimension size
	public int offset = 1, maxDimensions = 10;
	
	/**
	 * Generates the test case with custom settings. 
	 */
	public DifferentDimensionsBenchmark() {
		super();
		tupleCount = 100000;
		dimensions = 4;
		// We are only interested in the average runtimes.
		printEveryValue = false;
		printMedian = false;
	}
	
	/**
	 * Generates the test case with the specified settings. 
	 * @param startDimensions Amount of dimensions in the first Test
	 * @param offset The increase added to the dimension count
	 * @param maxDimensions
	 */
	public DifferentDimensionsBenchmark(int startDimensions, int offset, int maxDimensions) {
		super();
		dimensions = startDimensions;
		this.offset = offset;
		this.maxDimensions = maxDimensions;
		tupleCount = 100000;
		// We are only interested in the average runtimes.
		printEveryValue = false;
		printMedian = false;
	}
	
	
	public List<Result> run(InputHandler handler, int repetitions) throws SecurityException, IOException, NoAlgorithmsSetException {
		List<Result> results = new ArrayList<Result>();
		while (dimensions <= maxDimensions) {
			// Superclass starts the Test
			results.addAll(super.run(handler, repetitions));
			dimensions = dimensions + offset;
		}
		return results;
	}

	public List<Result> run(TupleList tupleList, int repetitions) throws SecurityException, IOException, NoAlgorithmsSetException {
		List<Result> results = new ArrayList<Result>();
		while (dimensions <= maxDimensions) {
			// Superclass starts the Test
			results.addAll(super.run(tupleList, repetitions));
			dimensions = dimensions + offset;
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
			// Dimensions vs Runtime
			file = pgf.insertAlgorithmResults(new File("src/main/resources/texTemplates/template.tex"), outputFile, 
					"Dim", "Laufzeitvergleich: Verschiedene Dimensionen", "Anzahl der Dimensionen");
			pgf.genPdf(file);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

}
