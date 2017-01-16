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
package preferences;

import framework.Tuple;

/**
 * Implements the "highest" Preference. Tuples are sorted from large to small entries
 * @author Sven Wiemann
 *
 */
public class PreferenceHighest extends Preference{
	// The Dimension each Tuple is transformed by this Preference
	int dimension;
	// The maximum value of one Tuple in this dimension 
	float maximum;

	/**
	 * Creates a Preference of this type
	 * @param dimension The Dimension each Tuple is transformed by this Preference
	 * @param maximum The maximum value of one Tuple in this dimension.
	 */
	public PreferenceHighest(int dimension, float maximum){
		this.dimension = dimension;
		this.maximum = maximum;
	}
	
	public PreferenceHighest(int dimension){

	}

	@Override
	public float calc(float value) {
		return maximum - value;
	}

	@Override
	public float[] calc(float[] tuple) {
		// The lowest Values become the highest...
		tuple[dimension] = maximum - tuple[dimension];
		return tuple;
	}

	@Override
	public int compare(Tuple o1, Tuple o2) {
		return Math.round(o2.getValue(dimension)-o1.getValue(dimension));
	}

	public float getMaximum() {
		return maximum;
	}

	public void setMaximum(float maximum) {
		this.maximum = maximum;
	}
}
