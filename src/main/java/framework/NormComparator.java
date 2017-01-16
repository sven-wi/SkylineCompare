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

import java.util.Comparator;

/**
 * Comparator used for sorting Tuples, based on their l_1-Norm
 * @author Sven Wiemann
 *
 */
public class NormComparator implements Comparator<Tuple>{

	@Override
	public int compare(Tuple arg0, Tuple arg1) {
		if(arg1.getL1Norm() - arg0.getL1Norm() > 0)
			return 1;
		else if (arg1.getL1Norm() - arg0.getL1Norm() < 0)
			return -1;
		return 0;
	}

}
