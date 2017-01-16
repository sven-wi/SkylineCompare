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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Random;
import java.util.logging.Logger;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import framework.TupleList;

/**
 * Implementation of a Data Generator.
 * BruteForce Method, generated Data will be stored into the resources Folder.
 * @author Sven Wiemann
 *
 */
public class DataGen {
	private static final Logger logger = Logger.getLogger(DataGen.class.getName());
	
	/**
	 * Creates normal or uniform distributed Test-Data, without correlation.
	 * The resulting Data is stored in the resources Folder.
	 * @param dimensions
	 * 		The dimension count of the resulting Data
	 * @param rowCount
	 * 		How many data tuples should be created?
	 * @throws IOException
	 * 		If Stream to a File couldn't be written/closed 
	 */
	public static void genData(int dimensions, int rowCount) throws IOException{
		logger.info("Generating uniform random Data with " + rowCount + " Tuples in " + dimensions + " dimensions");
		Writer fw = new FileWriter("src/main/resources/random-" + rowCount + "-" + dimensions + ".dat");
		Random gen = new Random();	
		
		for (int i = 1; i <= rowCount; i++){
			// Each Row should start with the Row count
			String row = i + " ";
			// Files should be created OS/Language independent
			DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
			dfs.setDecimalSeparator('.');
			NumberFormat nf = new DecimalFormat("0.000000000", dfs);
			
			for(int j = 0; j < dimensions; j++){
				Float n = gen.nextFloat();				
				row = row + nf.format(n) + " "; 
			}
			fw.write(row);
			fw.append(System.getProperty("line.separator"));
		}
		fw.close();
		logger.info(rowCount + " entries generated");
	}
	
	/**
	 * Creates anti-correlated Test-Data with the specified Seed
	 * The resulting Data is stored in the resources Folder.
	 * @param dimensions
	 * 		The dimension count of the resulting Data
	 * @param rowCount
	 * 		How many data tuples should be created?
	 * @param gaussianData
	 * 		Should we create gaussian distributed Data?
	 * 		if false: uniform distributed Data will get created.
	 * @param std
	 * 		standard deviation value
	 * @param center
	 * 		center of the distribution
	 * @param saveData
	 * 		should the Data get Saved?
	 * @param seed
	 * 		the seed for the Random Generations
	 * @throws IOException
	 * 		If Stream to a File couldn't be written/closed 
	 */
	public static CustomRealMatrix genAntiCorrData(int dimensions, int rowCount, boolean gaussianData, float std, float center, boolean saveData, long seed) throws IOException{
		File file = null;
		// Generate the Export Files
		if(gaussianData) {
			logger.info("Generating gaussian anti-correlated Data with " + rowCount + " Tuples in " + dimensions + " dimensions");
			if(saveData)
				file = new File("src/main/resources/anticorr-gaussian-" + rowCount + "-" + dimensions + ".dat");
		}
		else {
			logger.info("Generating uniform anti-correlated Data with " + rowCount + " Tuples in " + dimensions + "dimensions");
			if (saveData)
				file = new File("src/main/resources/anticorr-normal-" + rowCount + "-" + dimensions + ".dat");
		}
		
		Random gen = new Random(seed);
		//PearsonsCorrelation corr = new PearsonsCorrelation();
		//RealMatrix corrMatrix = null;
		
		// Files should be created OS/Language independent
		DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
		dfs.setDecimalSeparator('.');
		NumberFormat nf = new DecimalFormat("0.000000000", dfs);
		
		// Create a good Start-Matrix: a (dimension x dimension) matrix which fulfills the Anti-Correlation Condition
		CustomRealMatrix values = new CustomRealMatrix(rowCount, dimensions);
		int validTupleCount = 0;
		
		// Create the remaining Tuples
		while (validTupleCount < rowCount){
			boolean minimalEntryFound = false;
			double entry = 0d;
			for(int j = 0; j < dimensions; j++){		
				if(gaussianData)
					entry = std*gen.nextGaussian()+center;
				else
					entry = std*gen.nextDouble()+center;
				
				if (!minimalEntryFound && entry < center)
					minimalEntryFound = true;
				else if (minimalEntryFound){
					while (entry < center){
						if(gaussianData)
							entry = std*gen.nextGaussian()+center;
						else
							entry = std*gen.nextDouble()+center;
					}
				}
				values.setEntry(validTupleCount, j, entry);
			}
			validTupleCount = validTupleCount + 1;
		}
		logger.info(values.getRowDimension() + " entries generated");
		if(saveData){
			Writer fw = new FileWriter(file);
			saveData(values, fw, nf);
		}
		
		return values;
	}
	
	/**
	 * Creates anti-correlated Test-Data. The current System.time is used as a Seed for the Random Generation.
	 * The resulting Data is stored in the resources Folder.
	 * @param dimensions
	 * 		The dimension count of the resulting Data
	 * @param rowCount
	 * 		How many data tuples should be created?
	 * @param gaussianData
	 * 		Should we create gaussian distributed Data?
	 * 		if false: uniform distributed Data will get created.
	 * @param std
	 * 		standard deviation value
	 * @param center
	 * 		center of the distribution
	 * @param saveData
	 * 		should the Data get Saved?
	 * @param seed
	 * 		the seed for the Random Generations
	 * @throws IOException
	 * 		If Stream to a File couldn't be written/closed 
	 */
	public static CustomRealMatrix genAntiCorrData(int dimensions, int rowCount, boolean gaussianData, float std, float center, boolean saveData) throws IOException{
		long seed = System.currentTimeMillis();
		return genAntiCorrData(dimensions, rowCount, gaussianData, std, center, saveData, seed);
	}
	
	/**
	 * Do the yet created data stored in the matrix fulfill the Anti-Correlation condition?
	 * Returns true if more than the half of all correlation coefficients are below the supremum coefficient.
	 * @param corrMatrix
	 * 		The Data-Matrix
	 * @param corrCoeff
	 * 		the Supremum correlation coefficient
	 * @param dimensions
	 * 		how many dimensions does the test file have?
	 * @return
	 * 		true if more than the half of all correlation coefficients are below the supremum coefficient.
	 */
	public static boolean isCorrMatrixValid(RealMatrix corrMatrix, double corrCoeff, int dimensions){
		int count = 0;
		int maxVariance = sum(dimensions-1)/2;
		for(int i = 1; i < corrMatrix.getColumnDimension(); i++){
			for(int j = 0; j < i; j++){
				if(corrMatrix.getEntry(i, j) > corrCoeff){
					if(count < maxVariance)
						count++;
					else
						return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Do the yet created data stored in the matrix fulfill the Anti-Correlation condition?
	 * Returns true if all correlation coefficients are below the Supremum correlation coefficient.
	 * @param corrMatrix
	 * 		The Data-Matrix
	 * @param corrCoeff
	 * 		the Supremum correlation coefficient
	 * @param dimensions
	 * 		how many dimensions does the test file have?
	 * @return
	 * 		true if all correlation coefficients are below the Supremum correlation coefficient.
	 */
	public static boolean isCorrMatrixCompleteValid(RealMatrix corrMatrix, double corrCoeff, int dimensions){
		for(int i = 1; i < corrMatrix.getColumnDimension(); i++){
			for(int j = 0; j < i; j++){
				if(corrMatrix.getEntry(i, j) > corrCoeff){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Saves the Data
	 * @param values
	 * 		The values which should be saved.
	 * @param fw
	 * 		The File-Writer including the File location
	 * @param nf
	 * 		How should the data get formatted?
	 * @throws IOException
	 * 		If Stream to a File couldn't be written/closed 
	 */
	public static void saveData(Array2DRowRealMatrix values, Writer fw, NumberFormat nf) throws IOException{
		for (int i = 0; i < values.getRowDimension(); i++){
			String row = i+1 + " ";
			//String row = "";
			
			for(int j = 0; j < values.getColumnDimension(); j++){				
				row = row + nf.format(values.getEntry(i, j)) + " "; 
			}
			fw.write(row);
			fw.append(System.getProperty("line.separator"));
		}
		fw.close();
	}
	
	/**
	 * Save the Date with the FileWriter
	 * @param values
	 * @param fw
	 * @param nf
	 * @throws IOException
	 */
	public static void saveData(TupleList values, Writer fw, NumberFormat nf) throws IOException{
		for (int i = 0; i < values.size(); i++){
			String row = i+1 + " ";
			//String row = "";
			
			for(int j = 0; j < values.get(i).size(); j++){				
				row = row + nf.format(values.get(i).getValue(j)) + " "; 
			}
			fw.write(row);
			fw.append(System.getProperty("line.separator"));
		}
		fw.close();
	}
	
	/**
	 * Returns the sum from 1 to n.
	 * @param n
	 * @return
	 */
	public static int sum(int n){
		if (n == 1)
			return 1;
		return n + sum(n-1);
	}
}
