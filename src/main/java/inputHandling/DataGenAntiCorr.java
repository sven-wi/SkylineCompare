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
package inputHandling;

import java.util.Random;
import java.util.logging.Logger;

import dataGen.CustomRealMatrix;
import framework.TupleList;
import inputHandling.InputHandler.DataGenInterface;

/**
 * Data Generator: Light Anti correlated Data.
 * One Component of every Tuple must be smaller than the center of the Distribution
 * @author Sven Wiemann
 *
 */
public class DataGenAntiCorr implements DataGenInterface{
	private static final Logger logger = Logger.getLogger(DataGenAntiCorr.class.getName());
	private long seed;
	// Standard deviation and center of the Data Generators
	private float std = 0.6f, center = 2f;
	
	/**
	 * Use the given Seed.
	 * @param seed
	 */
	public DataGenAntiCorr(long seed) {
		this.seed = seed;
	}
	
	/**
	 * Use the current Time as the Seed.
	 */
	public DataGenAntiCorr() {
		this(System.currentTimeMillis());
	}
	
	/**
	 * Use a custom Seed
	 * @param seed
	 * @param std standard deviation
	 * @param center center of the distribution
	 */
	public DataGenAntiCorr(long seed, float std, float center){
		this(seed);
		this.std = std;
		this.center = center;		
	}
	
	/**
	 * Use the current time as the Seed
	 * @param std standard deviation
	 * @param center center of the distribution
	 */
	public DataGenAntiCorr(float std, float center){
		this(System.currentTimeMillis());
		this.std = std;
		this.center = center;		
	}

	/**
	 * Generate the Data
	 */
	@Override
	public TupleList genData(int dimensions, int tupleCount) {
		logger.info("Generating uniform anti-correlated Data with " + tupleCount + " Tuples in " + dimensions + " dimensions");
		Random gen = new Random(seed);
				
		CustomRealMatrix values = new CustomRealMatrix(tupleCount, dimensions);
		int validTupleCount = 0;
		
		// Create the Tuples
		while (validTupleCount < tupleCount){
			boolean minimalEntryFound = false;
			double entry = 0d;
			for(int j = 0; j < dimensions; j++){
				entry = std*gen.nextDouble()+center;
				
				// At least one Tuple-Component should be lower than the center of the distribution => light Anti-Correlation
				if (!minimalEntryFound && entry < center)
					minimalEntryFound = true;
				else if (minimalEntryFound){
					while (entry < center){
						entry = std*gen.nextDouble()+center;
					}
				}
				values.setEntry(validTupleCount, j, entry);
			}
			validTupleCount = validTupleCount + 1;
		}
		logger.info(values.getRowDimension() + " entries generated");
		return values.transformToList();
	}

	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}

	public float getStd() {
		return std;
	}

	public float getCenter() {
		return center;
	}

	public void setStd(float std) {
		this.std = std;
	}

	public void setCenter(float center) {
		this.center = center;
	}

	
}
