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
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import framework.Tuple;
import framework.TupleList;
import inputHandling.InputHandler.FileInputInterface;

/**
 * Reads the Weather File
 * @author Sven Wiemann
 *
 */
public class FileImporterWeather implements FileInputInterface{
	private File file;	
	private static final Logger logger = Logger.getLogger(FileImporterWeather.class.getName());
	
	// Custom BreakCondition: stop if enough Rows are read
	public int rowsToRead = -1, rowsRead = 0;
	
	public FileImporterWeather(File file) {
		this.file = file;
	}

	@Override
	public TupleList readFile() {
		try {
			logger.info("Reading Weather File " + file.toString() + " to List");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			
			String[] values = line.split(" ");
			int numDimensions = values.length-2;
			
			NumberFormat format = NumberFormat.getInstance(Locale.US);
			
			TupleList tupleList = new TupleList(numDimensions);
			rowsRead = 0;
			while (line != null){				
				boolean validLine = true;
				values = line.split(" ");
				Tuple tuple = new Tuple(numDimensions);
				
				// Count the Days between one special data and the date of this entry
				Calendar cal = new GregorianCalendar();
				cal.set(Integer.parseInt(values[1].substring(0,4)), Integer.parseInt(values[1].substring(4,6))-1, 
						Integer.parseInt(values[1].substring(6,8)), Integer.parseInt(values[1].substring(8,10)), 
						Integer.parseInt(values[1].substring(10,12)));
				Date date = new Date(cal.getTimeInMillis());
				cal.set(Integer.parseInt(values[1].substring(0,4)), 9, 15);
				Date date2 = new Date(cal.getTimeInMillis());
				long diffDays = getDifferenceDays(date,date2);
				tuple.setValue(0, (diffDays > 182) ? Math.abs(365 - diffDays) : Math.abs(diffDays));
				
				// No negative Temp
				//System.out.println(values[5]);
				if(!values[8].startsWith("*"))
					values[8] = (Float.parseFloat(values[8]) + 200f) + "";
				
				// The first three columns contain data which isn't needed for our case,
				// so we start in the 4th column				
				for (int i = 3; i < values.length; i++){
					if(values[i].startsWith("*"))
						validLine = false;
					else{
						tuple.setValue(i-2, format.parse(values[i]).floatValue());
					}
				}
				
				// Use only data which doesn't contain empty data fields
				if(validLine){
					tupleList.add(tuple);
					rowsRead++;
				}
				
				// Another Break-Condition: stop if rowsToRead-Rows are read				
				if(rowsRead == rowsToRead)
					line = null;
				else
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
		} catch (ParseException e) {
			logger.severe("Parse Exception");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * How many days are between two Dates?
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static long getDifferenceDays(Date d1, Date d2) {
	    long diff = d2.getTime() - d1.getTime();
	    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public int getRowsToRead() {
		return rowsToRead;
	}

	public void setRowsToRead(int rowsToRead) {
		this.rowsToRead = rowsToRead;
	}

}
