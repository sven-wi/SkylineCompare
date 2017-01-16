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
package inputHandling;

import framework.TupleList;

/**
 * This File has to be implemented by each Input Generator/Reader
 * @author Sven Wiemann
 *
 */
public abstract interface InputHandler {

	/**
	 * A File should be read by this Class
	 * @author Sven Wiemann
	 *
	 */
	public interface FileInputInterface extends InputHandler{
		/**
		 * Read a File, save the Values of the File into a TupleList
		 * @return
		 */
		public abstract TupleList readFile();
	}
	
	/**
	 * Data will be created by this class
	 * @author Sven Wiemann
	 *
	 */
	public interface DataGenInterface extends InputHandler {
		/**
		 * Create a dimensions-dimensional data-set, with tupleCount tuples.
		 * @param dimensions
		 * @param tupleCount
		 * @return
		 */
		public abstract TupleList genData(int dimensions, int tupleCount);
	}
}


