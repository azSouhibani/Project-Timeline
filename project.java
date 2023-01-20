package de;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class project implements Comparator<stage>{
    private String[] info = new String[9];

    public ArrayList<stage> relStgs = new ArrayList<>();

    public project() {
    	
    }

    public project(String[] data) {
        this.info = data.clone();
    }

    public String getNodeID() {
        return info[0];
    }

    public String getProjectID() {
        return info[1];
    }
    
    public String getStage() {
        return info[2];
    }
    
	public Date getStartDate() {
		
		String sDate1= info[3];  
		if(sDate1 != null) {
			
			Date date1 = null;
			try {
				date1 = new SimpleDateFormat("dd-MMM-yyyy").parse(sDate1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			
		    return date1;
		}
	    return null;
	}

	public Date getEndDate() {
		
		String sDate1= info[4];
		if(sDate1 != null) {
			
			Date date1= null;
			try {
				date1 = new SimpleDateFormat("dd-MMM-yyyy").parse(sDate1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			
		    return date1;
		}
	    return null;
	}
	
    public String getCustomer() {
        return info[5];
    }

    public String getCurrency() {
        return info[6];
    }

    public String getDofCreation() {
        return info[7];
    }

    public String getModifDate() {
        return info[8];
    }

    public void addstage(stage x) {
        relStgs.add(x);
    }

    public void setStages(ArrayList<stage> stgArr) {
        this.relStgs = stgArr;
        Collections.sort(this.relStgs,new project());
        
    }

	public long getDuration() {
		
	    long dateBeforeInMs = getStartDate().getTime();
	    	
	    long dateAfterInMs = getEndDate().getTime();

	    long timeDiff = Math.abs(dateAfterInMs - dateBeforeInMs);
	    
	    long days = timeDiff / 1000 / 60 / 60 / 24;
	    
	    return days;

	    }

    public int getStagesNo() {
    	
        return relStgs.size();
        
    }

	private ArrayList<Date> getAllEventsDate(){
		
		ArrayList<Date> dates = new ArrayList<>();
		
//		if(getStartDate() != null || getEndDate() != null) {
//			dates.add(getStartDate());
//			dates.add(getEndDate());
//		}
		
		
		
		for(int i = 0; i < relStgs.size(); i++) {
			Date d = relStgs.get(i).getDate();
			if (d != null)
				dates.add(d);
			
		}
		Collections.sort(dates);
		return dates;
		
	}

	public ArrayList<Integer> getDurationBetweenEvents(){
		
		ArrayList<Integer> allDurations = new ArrayList<Integer>();
		ArrayList<Date> allEvents = getAllEventsDate();
		
		for(int i = 1; i < allEvents.size(); i++) {
			
			Date d1 = allEvents.get(0);
			Date d2 = allEvents.get(i);
			
			int diff = (int) Utility.durationInDays(d1,d2);
			allDurations.add(diff);
			
		}
		
		return allDurations;
	}

	public ArrayList<String> allEventLabels(){
		
		ArrayList<Date> allEvents = getAllEventsDate();
		
		ArrayList<String> EventLabels = new ArrayList<>();
		
		for(int i = 0; i < allEvents.size(); i++) {
			String dateBeforeFormat = allEvents.get(i).toString();
			String dateAfterFormat = Utility.toStr(dateBeforeFormat);
			EventLabels.add(dateAfterFormat);
		}
		
		return EventLabels;
		
	}

	

	@Override
	public int compare(stage o1, stage o2) {
		// TODO Auto-generated method stub
		int num = o1.getDate().compareTo(o2.getDate());
		return num;
		//return (int) (o1.getDate().getTime() - o2.getDate().getTime());
	}

}

