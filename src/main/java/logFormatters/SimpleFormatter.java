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
package logFormatters;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Log-Entries should be one-liner with this Formatter.
 * @author Sven Wiemann
 *
 */
public class SimpleFormatter extends Formatter {

	@Override
	public String format(LogRecord record) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSS");		
		String string = String.format("%20s %32s %24s %12s %s\n", sdf.format(new Date()), 
				record.getSourceClassName(), record.getSourceMethodName(),
				record.getLevel(), record.getMessage());
        return string;
	}

}
