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

package starters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import exceptions.NoAlgorithmsSetException;
import inputHandling.DataGenAntiCorr;
import inputHandling.DataGenAntiCorrCommons;
import inputHandling.DataGenAntiCorrGauss;
import inputHandling.DataGenAntiCorrGaussCommons;
import inputHandling.DataGenCorrCommons;
import inputHandling.DataGenCorrGaussCommons;
import inputHandling.DataGenRandom;
import inputHandling.FileImporterFloat;
import inputHandling.InputHandler;
import tests.DifferentDatasizeBenchmark;
import tests.DifferentDimensionsBenchmark;
import tests.ThreadBenchmark;

/**
 * Starter Class. Which other Tests should be run? Comment / Uncomment or add
 * new Cases
 * 
 * @author Sven Wiemann
 *
 */
public class StartExamples {
	public static void main(String[] args) throws SecurityException, IOException, NoAlgorithmsSetException {

		/**
		 * Inputhandling
		 */
		// 2 included Files
		File file = new File("src/main/resources/data2.txt");

		// The different Inputhandlers currently included
		InputHandler inputAnti = new DataGenAntiCorr(1);
		InputHandler inputAntiGauss = new DataGenAntiCorrGauss(1);
		InputHandler inputRandom = new DataGenRandom(1);
		InputHandler inputFile = new FileImporterFloat(file);
		InputHandler inputCorrCommons = new DataGenCorrCommons(1);
		InputHandler inputCorrCommonsGauss = new DataGenCorrGaussCommons(1);
		InputHandler inputCommons = new DataGenAntiCorrCommons(1);
		InputHandler inputCommonsGauss = new DataGenAntiCorrGaussCommons(1);

		/**
		 * Benchmark Test - Start
		 */
		// How many Repetitions for each Test?
		int repeatCount = 15;

		// Benchmark Tests: The InputHandlers used for this Test are added here
		List<InputHandler> handlerList = new ArrayList<InputHandler>();
		handlerList.add(inputAnti);
		handlerList.add(inputAntiGauss);
		handlerList.add(inputRandom);
		handlerList.add(inputCommons);
		handlerList.add(inputCommonsGauss);
		handlerList.add(inputCorrCommons);
		handlerList.add(inputCorrCommonsGauss);

		// Loop through all Inputhandlers
		for (int i = 0; i < handlerList.size(); i++) {
			InputHandler input2 = handlerList.get(i);

			// Override some variables, run the test, and generate the output
			DifferentDatasizeBenchmark test2 = new DifferentDatasizeBenchmark();
			test2.dimensions = 5;
			test2.run(input2, repeatCount);
			test2.genOutput();
			test2 = null;

			DifferentDatasizeBenchmark test3 = new DifferentDatasizeBenchmark();
			test3.dimensions = 8;
			test3.run(input2, repeatCount);
			test3.genOutput();
			test3 = null;

			DifferentDimensionsBenchmark test4 = new DifferentDimensionsBenchmark();
			test4.tupleCount = 100000;
			test4.run(input2, repeatCount);
			test4.genOutput();
			test4 = null;

			DifferentDimensionsBenchmark test5 = new DifferentDimensionsBenchmark();
			test5.tupleCount = 1000000;
			test5.dimensions = 4;
			test5.run(input2, repeatCount);
			test5.genOutput();
			test5 = null;

			ThreadBenchmark test6 = new ThreadBenchmark();
			test6.dimensions = 5;
			test6.tupleCount = 100000;
			test6.run(input2, repeatCount);
			test6.genOutput();
			test6 = null;

			ThreadBenchmark test7 = new ThreadBenchmark();
			test7.dimensions = 8;
			test7.tupleCount = 100000;
			test7.run(input2, repeatCount);
			test7.genOutput();
			test7 = null;

			ThreadBenchmark test8 = new ThreadBenchmark();
			test8.dimensions = 5;
			test8.tupleCount = 1000000;
			test8.run(input2, repeatCount);
			test8.genOutput();
			test8 = null;
		}
		/**
		 * Benchmark Test - End
		 */


	}
}
