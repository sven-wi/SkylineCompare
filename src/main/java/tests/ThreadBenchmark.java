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
 * Test-Case: Scaling of the different algorithms, against different Thread counts
 * @author Sven Wiemann
 *
 */
public class ThreadBenchmark extends Benchmark{
	public ThreadBenchmark() {
		super();
		printEveryValue = false;
		printMedian = false;
	}
	
	public List<Result> run(InputHandler handler, int repetitions) throws SecurityException, IOException, NoAlgorithmsSetException {
		List<Result> results = new ArrayList<Result>();
		for (int i = 1; i <= 4; i++) {
			threadCount = i;
			// delegate everything to the superclass
			results.addAll(super.run(handler, repetitions));
		}
		return results;
	}

	public List<Result> run(TupleList tupleList, int repetitions) throws SecurityException, IOException, NoAlgorithmsSetException {
		List<Result> results = new ArrayList<Result>();
		for (int i = 1; i <= 4; i++) {
			threadCount = i;
			// delegate everything to the superclass
			results.addAll(super.run(tupleList, repetitions));
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
			// Thread count vs. Runtime
			file = pgf.insertAlgorithmResults(new File("src/main/resources/texTemplates/template.tex"), outputFile, 
					"Thr", "Laufzeitanalyse: Anzahl der Threads", "Anzahl der Threads");
			pgf.genPdf(file);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
