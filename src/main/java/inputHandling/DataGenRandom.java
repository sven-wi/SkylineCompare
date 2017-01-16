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

import framework.Tuple;
import framework.TupleList;
import inputHandling.InputHandler.DataGenInterface;

/**
 * Generate uniform distributed, fully random Data
 * @author Sven Wiemann
 *
 */
public class DataGenRandom implements DataGenInterface{
	private static final Logger logger = Logger.getLogger(DataGenRandom.class.getName());
	private long seed;
	// Standard deviation and center of the Data Generators
	private float lowerBounds = 0f, higherBounds = 1f;
	
	/**
	 * Generate Data with a custom Seed
	 * @param seed
	 */
	public DataGenRandom(long seed) {
		this.seed = seed;
	}
	
	/**
	 * Generate Data with the current Time as Seed.
	 */
	public DataGenRandom() {
		this(System.currentTimeMillis());
	}
	
	/**
	 * Generate custom seeded Data in an Intervall.
	 * @param seed
	 * @param lowerBounds
	 * @param higherBounds
	 */
	public DataGenRandom(long seed, float lowerBounds, float higherBounds){
		this(seed);
		this.lowerBounds = lowerBounds;
		this.higherBounds = higherBounds;		
	}
	
	/**
	 * Generate Data in an Intervall
	 * @param lowerBounds
	 * @param higherBounds
	 */
	public DataGenRandom(float lowerBounds, float higherBounds){
		this(System.currentTimeMillis());
		this.lowerBounds = lowerBounds;
		this.higherBounds = higherBounds;		
	}

	@Override
	public TupleList genData(int dimensions, int tupleCount) {
		logger.info("Generating uniform random Data with " + tupleCount + " Tuples in " + dimensions + " dimensions");
		Random gen = new Random(seed);
		TupleList tupleList = new TupleList(dimensions);
		
		for (int i = 0; i <= tupleCount; i++){
			Tuple tuple = new Tuple(dimensions);
			for(int j = 0; j < dimensions; j++){
				float value = lowerBounds + gen.nextFloat() * ((higherBounds - lowerBounds) + 1);
				tuple.setValue(j, value);
			}
			tupleList.add(tuple);
		}
		logger.info(tupleCount + " entries generated");
		return tupleList;
	}

	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}

	public float getLowerBounds() {
		return lowerBounds;
	}

	public float getHigherBounds() {
		return higherBounds;
	}

	public void setLowerBounds(float lowerBounds) {
		this.lowerBounds = lowerBounds;
	}

	public void setHigherBounds(float higherBounds) {
		this.higherBounds = higherBounds;
	}
}
