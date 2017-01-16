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


/**
 * One Data Tuple
 * @author Sven Wiemann
 *
 */
public class Tuple {
	// The coordinates of the Tuple
	private float[] values;
	// How many dimensions?
	private int dimensions;
	
	public Tuple(float[] values){
		this.values = values;
		this.dimensions = values.length;
	}
	
	public Tuple(double[] values){
		this.values = new float[values.length];
		this.dimensions = values.length;
		for(int i = 0; i < values.length; i++){
			this.values[i] = (float) values[i];
		}
	}
	
	public Tuple(int dimensionCount){
		this.values = new float[dimensionCount];
		this.dimensions = dimensionCount;
	}
	
	public boolean equals(Tuple tuple2) {
		if(dimensions != tuple2.dimensions)
			return false;
		for(int i = 0; i < dimensions; i++) {
			if(values[i] != tuple2.values[i])
				return false;
		}
		return true;		
	}

	/**
	 * Compare this tuple to tuple2
	 * @param tuple2
	 * @return
	 */
	public int compare(Tuple tuple2) {
		int dominator = 0;
		
		for(int i = 0; i < values.length; i++){
			if (values[i] < tuple2.values[i]){
				if  (dominator == 1)
					return 0;
				dominator = -1;
			}
			else if (values[i] > tuple2.values[i]){
				if  (dominator == -1)
					return 0;
				dominator = 1;
			}
		}
		return dominator;
	}
	
	/**
	 * The l1-Norm (i.e.: sum of all coordinates) of this Tuple.
	 * @return
	 */
	public float getL1Norm() {
		float l1Norm = 0f;
		for (float value : values){			
			l1Norm = l1Norm + value;
		}
		return l1Norm;
	}
	
	@Override
	public boolean equals(Object obj) {
        if(obj instanceof Tuple) {
        	Tuple objTuple = (Tuple) obj;
        	if(dimensions != objTuple.dimensions)
        		return false;
        	if(dimensions == 0)
        		return true;
        	if (values[0] == ((Tuple) obj).values[0]) {
        		if(dimensions > 1)
        			return equals(objTuple, 1);
        		return true;
        	}
        	return false;
        }
        else
        	return (this == obj);
    }
	
	public boolean equals(Tuple tuple, int i) {
		if(values[i] == tuple.values[i]){
			if (dimensions > i+1)
				return equals(tuple, i+1);
			return true;
		}
		return false;
	}

	public float[] getValues() {
		return values;
	}

	public void setValues(float[] values) {
		this.values = values;
	}
	
	public float getValue(int dimension){
		return values[dimension];
	}
	
	public void setValue(int dimension, float value){
		values[dimension] = value;
	}
	
	public int size(){
		return values.length;
	}
	
	/**
	 * Does this Tuple dominate the Tuple T?
	 * @param t
	 * @return
	 */
	public boolean dominate(Tuple t) {
		for(int i = 0; i < dimensions; i++){
			if (values[i] > t.values[i]){
				return false;
			}
		}
		return true;
    }

	/**
	 * The Values of this Tuple
	 * @return
	 */
	public String printTuple(){
		return printTuple(0);
	}
	
	public String printTuple(int i){
		if (i < values.length)
			return values[i] + " " + printTuple(i+1);
		return "";
	}

	public int getDimensions() {
		return dimensions;
	}

	public void setDimensions(int dimensions) {
		this.dimensions = dimensions;
	}
}
