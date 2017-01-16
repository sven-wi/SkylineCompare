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
import framework.TupleList;

/**
 * Container-Class. All Preferences for a Data-Set are contained in this class
 * @author Sven Wiemann
 *
 */
public class PreferenceManager {
	// The Preferences for each Dimension
	private Preference[] preferences;
	
	public PreferenceManager(Preference[] preferences){
		this.preferences = preferences;
	}
	
	/**
	 * Every Dimension gets the "lowest" Preference.
	 * @param dimensionCount
	 */
	public PreferenceManager(int dimensionCount){
		this.preferences = new Preference[dimensionCount];
		for (int i = 0; i < preferences.length; i++)
			preferences[i] = new PreferenceLowest(i);
	}	
	
	/**
	 * Transform the List with the given Preferences
	 * @param list
	 * @return
	 */
	public TupleList usePreferences(TupleList list){
		for(int j = 0; j < list.size(); j++)
			list.set(j, usePreferences(list.get(j)));		
		return list;				
	}
	/**
	 * Transform a Tuple with the given Preferences
	 * @param tuple
	 * @return
	 */
	
	public Tuple usePreferences(Tuple tuple){		
		for(int i = 0; i < tuple.getDimensions(); i++){
			tuple.setValue(i, preferences[i].calc(tuple.getValue(i)));
		}
		return tuple;
	}
	
	/**
	 * Set the Maximum value for each Dimension.
	 * @param maximum
	 */
	public void setMaximum(float[] maximum) {
		for (int i = 0; i < preferences.length; i++){
			if (preferences[i].getClass() == PreferenceHighest.class){
				if (((PreferenceHighest) preferences[i]).maximum == 0f)
					((PreferenceHighest) preferences[i]).maximum = maximum[i];
			}
		}
	}
	
	/**
	 * Set the Preference of one Dimension
	 * @param preference
	 * @param dimension
	 */
	public void setPreference(Preference preference, int dimension){
		preferences[dimension] = preference;
	}
	
	public Preference getPreference(int dimension){
		return preferences[dimension];
	}
	
	/**
	 * Get the Comparators used for Preference Calculation
	 * @param dimension
	 * @return
	 */
	public Preference getComperator(int dimension){
		return preferences[dimension];
	}
}
