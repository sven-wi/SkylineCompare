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

import java.util.Comparator;

import framework.Tuple;

/**
 * SuperClass of all Preferences. SubClasses need to implement the Methods that calculate
 * the transformed Values of each Tuple.
 * @author Sven Wiemann
 *
 */
public abstract class Preference implements Comparator<Tuple>{
	/**
	 * This Method calculates the transformed value of one Tuple Entry
	 * @param value the Value which should be transformed
	 * @return the transformed Value
	 */ 
	public abstract float calc(float value);
	
	/**
	 * This Method calculates the transformed value a complete Tuple
	 * @param tuple the Tuple which should be transformed
	 * @return the transformed Tuple
	 */
	public abstract float[] calc(float[] tuple);
}
