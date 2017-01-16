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

import java.util.ArrayList;
import java.util.List;

/**
 * A List of Tuples, with some thread-safe insertion/deletion methods.
 * size() Method provides List.size INCLUDING null Objects!
 * @author Sven Wiemann
 *
 */
public class TupleList extends ArrayList<Tuple>{
	private static final long serialVersionUID = -6655082144597249288L;
	private int index = 0;
	// The maximum value one Tuple of the List has for each dimension
	private float[] maximum;
	// Each Tuple of this List has numDimensions dimensions
	private int numDimensions;
	
	public TupleList(int numDimensions) {
		this.maximum = new float[numDimensions];
		this.numDimensions = numDimensions;
	}
	
	public TupleList(List<Tuple> list){
		super(list);
	}

	@Override
	public boolean add(Tuple e) {
		assert (numDimensions == e.getDimensions()) : "Tuple Dimensions do not match the List Dimensions";
			
		for (int i = 0; i < e.getValues().length; i++)
			if (e.getValues()[i] > maximum[i])
				this.maximum[i] = e.getValues()[i];
		return super.add(e);
	}
	
	/**
	 * Synchronized add Method. Only one Tuple can be added at a time this way
	 * @param e
	 * @return
	 */
	public boolean parallelAdd(Tuple e) {
		synchronized(this) {	
			return super.add(e);
		}
	}
	
	/**
	 * Remove the deleted null Tuples
	 */
	public void removeNull() {
		int i = 0;
		while(i < size()) {
			if (get(i) == null)
				remove(i);
			else
				i++;
		}
	}
	
	public void print(){
		for(Tuple tuple : this)
			System.out.println(tuple.printTuple());
	}
	
	public float getMaximum(int dimension){
		return maximum[dimension];
	}
	
	public float[] getMaximum(){
		return maximum;
	}
	
	public int getDimensions(){
		return this.numDimensions;
	}

	public Tuple next(){
		index++;
		return get(index-1);
	}
	
	public boolean hasNext(){
		return !(index == size());
	}
	
	public void resetIndex() {
		index = 0;
	}
}