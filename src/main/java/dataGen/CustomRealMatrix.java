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
package dataGen;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;

import framework.Tuple;
import framework.TupleList;

/**
 * Multidimensional Matrix
 * Adds a Converter to the Apache.Commons.Math Matrices. Matrices could be converted into
 * a TupleList with this Converter.
 * @author Sven Wiemann
 *
 */
public class CustomRealMatrix extends Array2DRowRealMatrix{

	private static final long serialVersionUID = 1L;

	public CustomRealMatrix(int rowCount, int dimensions) {
		super(rowCount, dimensions);
	}
	
	/**
	 * Transform this Matrix into a TupleList
	 * @return the TupleList
	 */
	public TupleList transformToList(){
		int dim = this.getColumnDimension();
		TupleList tupleList = new TupleList(dim);		
		for(int i = 0; i < this.getRowDimension(); i++){
			Tuple tuple = new Tuple(dim);
			
			for (int j = 0; j < dim; j++){
				tuple.setValue(j, (float) this.getEntry(i, j));
			}
			tupleList.add(tuple);			
		}
		return tupleList;
	}

}
