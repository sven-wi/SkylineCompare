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

import exceptions.PreferenceException;
import framework.Tuple;

/**
 * Implements the "around" Preference. Tuples are sorted based on their distance to an interval
 * @author Sven Wiemann
 *
 */
public class PreferenceBetween extends Preference{
	// The Dimension each Tuple is transformed by this Preference
	int dimension;
	// The Interval borders
	float minValue, maxValue;
	
	/**
	 * Creates a Preference of this type
	 * @param dimension The Dimension each Tuple is transformed by this Preference
	 * @param minValue The lower Bounds of the Interval
	 * @param maxValue The higher Bounds of the Interval
	 */
	public PreferenceBetween(int dimension, float minValue, float maxValue) throws PreferenceException{
		if(maxValue < minValue)
			throw new PreferenceException();
		this.dimension = dimension;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}
	

	@Override
	public float calc(float value) {
		// The value is between the borders? Distance = 0
		if (value >= minValue && value <= maxValue)
			return 0;
		return (value < minValue) ? minValue - value : value - maxValue;
	}

	@Override
	public float[] calc(float[] tuple) {
		// The value is between the borders? Distance = 0
		if (tuple[dimension] >= minValue && tuple[dimension] <= maxValue)
			tuple[dimension] = 0;
		// Calculate the Distance
		else
			tuple[dimension] = (tuple[dimension] < minValue) ? minValue - tuple[dimension] : tuple[dimension] - maxValue;
		return tuple;
	}

	@Override
	public int compare(Tuple o1, Tuple o2) {
		float value1 = o1.getValue(dimension);
		float value2 = o2.getValue(dimension);
		if (o1.getValue(dimension) >= minValue && value1 <= maxValue)
			return -1;
		else if (o2.getValue(dimension) >= minValue && value2 <= maxValue)
			return 1;
		float distance1 = (value1 < minValue) ? minValue - value1 : value1 - maxValue;
		float distance2 = (value2 < minValue) ? minValue - value2 : value2 - maxValue;
		return Math.round(distance1 - distance2);
	}
}
