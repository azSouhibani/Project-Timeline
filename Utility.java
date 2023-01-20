package de;

import java.util.Date;

public class Utility {
	
	public static long durationInDays(Date d1, Date d2) {
		
		if(d1 != null && d2 != null) {
			long dateBeforeInMs = d1.getTime();
	    	
		    long dateAfterInMs = d2.getTime();

		    long timeDiff = Math.abs(dateAfterInMs - dateBeforeInMs);
		    
		    long days = timeDiff / 1000 / 60 / 60 / 24;
		    
		    return days;
		}
		
		else
			return -1;
		
	    
	}
	
	
	public static String toStr(String date) {
		
		String str = date.substring(4,7);
		str = str + " " + date.substring(24,28);
		
		return str;
	}
	
	
	public static int durationInMonths(int totalDuration) {
		
		return (totalDuration/30) + 2;
		
		
	}

}
