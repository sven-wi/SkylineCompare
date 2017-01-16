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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import exceptions.NoAlgorithmsSetException;
import inputHandling.DataGenAntiCorr;
import inputHandling.DataGenAntiCorrGauss;
import inputHandling.InputHandler;
import pSkyline.PSkyline2;
import tests.DifferentDatasizeBenchmark;
import tests.DifferentDimensionsBenchmark;

/**
 * Minimal Example: Test some Algorithms. Included
 * Benchmarks: Influence of different Tuple-Quantities and different
 * Dimension-Sizes on the Runtime. Included Data Generators:
 * light-Anti-Correlated, light-Anti-Correlated-Gaussian Results are saved into
 * the /resources/output Folder. Additional Examples are included in the
 * StartExamples Class.
 * 
 * This class is used by the 'gradle run' command line command.
 * 
 * @author Sven Wiemann
 *
 */
public class Start {
	public static void main(String[] args) throws SecurityException, IOException, NoAlgorithmsSetException {
		// How many Repetitions for each Test?
		int repeatCount = 15;

		// Add the Data Generators to the List.
		InputHandler inputAnti = new DataGenAntiCorr(1); // light-Anti-Correlated
		InputHandler inputAntiGauss = new DataGenAntiCorrGauss(1); // light-Anti-Correlated-Gaussian
		List<InputHandler> handlerList = new ArrayList<InputHandler>();
		handlerList.add(inputAnti);
		handlerList.add(inputAntiGauss);
		// Add additional Data Generators here

		// Loop through all InputHandlers
		for (int i = 0; i < handlerList.size(); i++) {
			InputHandler input2 = handlerList.get(i);

			DifferentDatasizeBenchmark test2 = new DifferentDatasizeBenchmark();
			test2.dimensions = 5;	// Override some Variables: We want to test
									// 5-dimensional Data
			// Add additional overrides here

			test2.algorithms.clear(); // Customize the Algorithms used in this Test
			// Add new Algorithms here
			// Example: test2.algorithms.add(new SkylineAlgorithm());
			test2.algorithms.add(new PSkyline2());
			if(test2.algorithms.size() == 0)
				throw new NoAlgorithmsSetException();

			// Run the Test, generate the output
			test2.run(input2, repeatCount);
			test2.genOutput();
			test2 = null;

			DifferentDimensionsBenchmark test5 = new DifferentDimensionsBenchmark();
			test5.tupleCount = 1000000; // Override some Variables: We want to
										// start with 4-dimensional Data
			test5.dimensions = 4;		// and 1000000 Tuples.

			test5.algorithms.clear(); // Customize the Algorithms used in this Test
			// Add new Algorithms here
			// Example: test5.algorithms.add(new SkylineAlgorithm());
			
			if(test5.algorithms.size() == 0)
				throw new NoAlgorithmsSetException();

			// Run the Test, generate the output
			test5.run(input2, repeatCount);
			test5.genOutput();
			test5 = null;
		}
	}

}
