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
 * Implements the "lowest" Preference. Tuples are sorted from small to large entries
 * @author Sven Wiemann
 *
 */
public class PreferenceLowest extends Preference{
	int dimension;
	
	/**
	 * Creates a Preference of this type
	 * @param dimension The Dimension each Tuple is transformed by this Preference
	 */
	public PreferenceLowest(int dimension){
		this.dimension = dimension;
	}	

	@Override
	public float calc(float value) {
		return value;
	}

	@Override
	public float[] calc(float[] tuple) {
		return tuple;
	}

	@Override
	public int compare(Tuple o1, Tuple o2) {
		return Math.round(o1.getValue(dimension)-o2.getValue(dimension));
	}

}
