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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

import framework.Tuple;
import framework.TupleList;
import inputHandling.InputHandler.FileInputInterface;

/**
 * Imports a Float File.
 * Settings: First column: rownumber, remaining columns: data
 * @author Sven Wiemann
 *
 */
public class FileImporterFloat implements FileInputInterface{
	private File file;
	
	private static final Logger logger = Logger.getLogger(FileImporterFloat.class.getName());
	
	public FileImporterFloat(File file) {
		this.file = file;

	}

	@Override
	public TupleList readFile() {
		try {
			logger.info("Reading File " + file.toString() + " to TupleList");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			
			String[] values = line.split(" ");
			int numDimensions = values.length-1;
			
			TupleList tupleList = new TupleList(numDimensions);
			while (line != null){
				
				values = line.split(" ");
				Tuple tuple = new Tuple(numDimensions); 
				
				// The first column contains the rownumber, so we start to read
				// at the second column
				for (int i = 1; i < values.length; i++){
					tuple.setValue(i-1, Float.parseFloat(values[i]));
				}
				tupleList.add(tuple);
				
				line = br.readLine();
				
			}
			br.close();
			return tupleList;
			
		} catch (FileNotFoundException e) {
			logger.severe("File Not Found!");
			e.printStackTrace();
		} catch (IOException e) {
			logger.severe("IO/Exception");
			e.printStackTrace();
		}
		return null;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
