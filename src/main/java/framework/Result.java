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
package framework;

import java.util.logging.Logger;

import framework.TimeObject;

/**
 * Result Object, containing runtimes, Skyline-Sizes and input data of one Skyline-Computation
 * @author Sven Wiemann
 *
 */
public class Result implements Comparable<Result> {
	private TimeObject timeobject;
	private TupleList skylineList;
	private int skylineSize, inputSize, dimensions, threads;
	private SkylineAlgorithm algorithm;
	protected static final Logger logger = Logger.getLogger(Result.class.getName());

	public Result (TimeObject timeObject, TupleList skylineList, int skylineSize, int inputSize, int dimensions, int threads, SkylineAlgorithm algo){
		this.timeobject = timeObject;
		this.skylineList = skylineList;
		this.skylineSize = skylineSize;
		this.inputSize = inputSize;
		this.dimensions = dimensions;
		this.threads = threads;
		this.algorithm = algo;
	}
	
	public Result (TimeObject timeObject, int skylineSize, int inputSize, int dimensions, int threads, SkylineAlgorithm algo){
		this.timeobject = timeObject;
		this.skylineSize = skylineSize;
		this.inputSize = inputSize;
		this.dimensions = dimensions;
		this.threads = threads;
		this.algorithm = algo;
	}
	
	public Result (int inputSize, int dimensions, int threads, SkylineAlgorithm algo){
		this.inputSize = inputSize;
		this.dimensions = dimensions;
		this.threads = threads;
		this.algorithm = algo;
	}
	
	public void printResult() {
		logger.info("Skyline-Computation with " + algorithm.getClass() + ". Input-Size: " + inputSize + 
				", Dimensions: " + dimensions + ", Threads: " + threads + ", Skyline-Size: " + skylineSize + 
				"\tCalcTime: " + timeobject.getCalcTime() + " ns\tInputTime: " + timeobject.getInputTime() + " ns"); 
	}
	

	@Override
	public int compareTo(Result o) {
		if(timeobject.getCalcTime() > o.getTimeobject().getCalcTime())
			return 1;
		if(timeobject.getCalcTime() == o.getTimeobject().getCalcTime())
			return 0;
		return -1;
	}


	public TimeObject getTimeobject() {
		return timeobject;
	}

	public void setTimeobject(TimeObject timeobject) {
		this.timeobject = timeobject;
	}

	public TupleList getSkylineList() {
		if(skylineList == null){
			logger.severe("The Skyline List has not been returned. Modify the 'returnCompleteSkyline' of the Algorithm.");
		}
			
		return skylineList;
	}

	public void setSkylineList(TupleList skylineList) {
		this.skylineList = skylineList;
	}

	public int getSkylineSize() {
		return skylineSize;
	}

	public void setSkylineSize(int skylineSize) {
		this.skylineSize = skylineSize;
	}

	public int getInputSize() {
		return inputSize;
	}

	public void setInputSize(int inputSize) {
		this.inputSize = inputSize;
	}

	public int getDimensions() {
		return dimensions;
	}

	public void setDimensions(int dimensions) {
		this.dimensions = dimensions;
	}
	
	public int getThreads() {
		return threads;
	}

	public void setThreads(int threads) {
		this.threads = threads;
	}

	public SkylineAlgorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(SkylineAlgorithm algorithm) {
		this.algorithm = algorithm;
	}
}
