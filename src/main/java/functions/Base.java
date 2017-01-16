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
package functions;
import java.text.DecimalFormat;
import java.util.List;

import framework.Tuple;

/**
 * Some helper functions.
 * @author Sven Wiemann
 *
 */
public class Base {
	/**
	 * Time Format
	 * @param time
	 * @return
	 */
	public static String convertTime(double time) {
		return String.format("fff.ffff", (time / 1000d), (time % 1000d));
	}
	
	/**
	 * Time Format
	 * @param time
	 * @param df
	 * @param divider
	 * @return
	 */
	public static String convertTime(long time, DecimalFormat df, double divider) {
		return df.format(time / divider);
	}
	
	/**
	 * Returns an maximum Array. Entry i of the Array is the Maximum in the i-th dimension of the
	 * Input Data.
	 * @param data
	 * @return
	 */
	public static float[] getMaximumValues(List<float[]> data){
		float[] maximum = data.get(0);
		for (float[] tuple : data){
			for (int j = 0; j < tuple.length; j++)
				if(maximum[j] < tuple[j])
					maximum[j] = tuple[j];
		}
		return maximum;
	}

	public static void printTupleArray(Tuple[] tuples){
		for (int i = 0; i < tuples.length; i++){
			for (int j = 0; j < tuples[i].size(); j++)
				System.out.print(tuples[i].getValue(j) + ", ");
			System.out.println();
		}
	}
	
	public static float arraySum(float[] array) {
		float sum = 0;
		for (float value : array) {
			sum = sum + value;
		}
		return sum;
	}
	
	public static float min(float[] array) {
		float min = array[0];
		for (int i = 1; i < array.length; i++)
			if (min > array[i])
				min = array[i];
		return min;
	}
}
