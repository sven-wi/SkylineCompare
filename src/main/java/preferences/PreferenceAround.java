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
 * Implements the "around" Preference. Tuples are sorted based on their distance to a value in dimension i
 * @author Sven Wiemann
 *
 */
public class PreferenceAround extends Preference{
	// The Dimension each Tuple is transformed by this Preference
	int dimension;
	// The Distance is calculated to this value
	float aroundValue;
	
	/**
	 * Creates a Preference of this type
	 * @param dimension The Dimension each Tuple is transformed by this Preference
	 * @param value The Distance is calculated to this value
	 */
	public PreferenceAround(int dimension, float value){
		this.dimension = dimension;
		this.aroundValue = value;
	}
	
	@Override
	public float calc(float value) {
		return Math.abs(value - aroundValue);
	}
	
	@Override
	public float[] calc(float[] tuple) {
		tuple[dimension] = Math.abs(tuple[dimension] - aroundValue);
		return tuple;
	}

	@Override
	public int compare(Tuple o1, Tuple o2) {
		return Math.round(Math.abs(o1.getValue(dimension) - aroundValue) - Math.abs(o2.getValue(dimension) - aroundValue));
	}
}
