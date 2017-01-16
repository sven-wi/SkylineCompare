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
package tests;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import framework.Result;
import framework.SkylineAlgorithm;
import framework.TupleList;
import inputHandling.InputHandler;
import logFormatters.SimpleFormatter;
import logFormatters.WriteFileFormatter;

/**
 * Every Testcase extends this Class, or a already extendes child from this Class.
 * This Class generalizes the Outputgeneration.
 * @author Sven Wiemann
 *
 */
public abstract class TestCase {
	public static final Logger logger = Logger.getLogger(TestCase.class.getName());
	// The File the Output is written to
	public File outputFile;
	// The Folder where the Output is saved
	public String outputDir = "src/main/resources/output/";
	// Handler that writes the Results into the Result File. Should be added ONLY if the Results of a
	// Testcase are written into the File.
	public Handler fileHandler;
	
	public TestCase() {
		try {
			genOutputLogger();
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Runs the Test 
	 * @param handler
	 * @param algorithm
	 * @return Result Object, containing the measured data
	 */
	public abstract Result run(InputHandler handler, SkylineAlgorithm algorithm, boolean firstIteration);
	/**
	 * Runs the Test
	 * @param tupleList
	 * @param algorithm
	 * @param firstIteration
	 * @return Result Object, containing the measured data
	 */
	public abstract Result run(TupleList tupleList, SkylineAlgorithm algorithm, boolean firstIteration);
	
	/**
	 * Write the measured Data into the Result File
	 */
	public abstract void genOutput();

	/**
	 * Generate the Log-Logger and the Result-Logger
	 * @throws SecurityException
	 * @throws IOException
	 */
	public void genOutputLogger() throws SecurityException, IOException{
		// FileOutput 1: Only for Testresults which should be interpreted by TeX/gnuplot
		// Add this Logger before Writing those Outputs, remove it afterwards
		outputFile = new File(outputDir + this.getClass().getSimpleName() + "-" + System.currentTimeMillis() + ".dat");		
		fileHandler = new FileHandler(outputFile.toString());
		fileHandler.setLevel(Level.FINER);		
		fileHandler.setFormatter(new WriteFileFormatter());
		
		// Captions
		logger.getParent().addHandler(fileHandler);
		//			 3  \t5    \t8		\t16			   \t16              \t5    \t
		logger.info("Thr\tDim  \tTuples  \tCalcTime        \tInputTime       \tFlag \tAlgorithm");
		logger.getParent().removeHandler(fileHandler);
		logger.removeHandler(fileHandler);
		
		// Setting up the Logoutput, Console + File
		Handler handler = new ConsoleHandler();
		handler.setLevel(Level.FINER);		
		handler.setFormatter(new SimpleFormatter());
		for(Handler handler2 : logger.getParent().getHandlers()) {
			logger.getParent().removeHandler(handler2);
		}

		//logger.getParent().removeHandler(logger.getParent().getHandlers()[0]);
		logger.getParent().addHandler(handler);
		logger.setLevel(Level.FINEST);
		
		File outputLogFile = new File(outputDir + this.getClass().getSimpleName() + "-" + System.currentTimeMillis() + ".log");
		Handler outputHandler = new FileHandler(outputLogFile.toString());
		outputHandler.setLevel(Level.FINEST);
		// Use another Logformatter here if needed
		outputHandler.setFormatter(new SimpleFormatter());
		logger.getParent().addHandler(outputHandler);
	}

}
