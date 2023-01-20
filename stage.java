package de;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class stage {
	
	
	String combined_info[] = new String[10];

    public stage(String[] stage, String[] det) {


        String[] comInfo = new String[10];
        int i;

        for(i=0; i < 7; i++) {
            comInfo[i] = stage[i];
        }

        for(i=0; i < 3; i++) {
            comInfo[i+7] = det[i+2];
        }

        combined_info=comInfo.clone();


    }

    public stage(String[] data) {
        combined_info = data.clone();
    }

    public String getProjectRef() {
        return combined_info[0];
    }

    public String getDocument() {
        return combined_info[1];
    }

    public String getFieldName() {
        return combined_info[2];
    }

    public String getchangeIndicator() {
        return combined_info[3];
    }

    public String gettextFlag() {
        return combined_info[4];
    }

    public String getNewVal() {
    	
        return combined_info[5];
        
    }

    public String getOldVal() {
    	
        return combined_info[6];
        
    }
   

    public Date getDate() {
        String StageDate = combined_info[7];
        if (StageDate != null) {
        	
        Date date = null;
        try {
			date = new SimpleDateFormat("dd-MMM-yyyy").parse(StageDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
	    return date;
	}
    return null;
    
    }

    public String getTime() {
        return combined_info[8];
    }

    public String getLanguage() {
    	return combined_info[9];
    }
    
    public int getStatus() {
    	// when the returned value is 1 >> the stage in green flag
    	// when the returned value is 0 >> the stage in red flag
    	// when the returned value is -1 >> there are missing values
    	
    	if(getNewVal() != null && getOldVal() != null) {
    		
    		int newNum = -100;
    		int oldNum = -100;
    		
    		try {
    			
    			newNum = (int )Float.parseFloat(getNewVal());
    			oldNum = (int) Float.parseFloat(getOldVal());
    			
    		}
    		
    		catch (Exception ex) {
    			ex.printStackTrace();
    			
    		}
    		
    		// green flag
    		if(newNum > oldNum) {
    			
    			return 1;
    		}

    		//red flag
    		else 
    			return 0;
    	}
    	
    	// values are missing
    	else 
    		return -1;
    }
    public int getReWorks() {
    	
    	
    	if (getNewVal() != null && getOldVal() != null) {
    		int newNum = (int )Float.parseFloat(getNewVal());
    		int oldNum = (int) Float.parseFloat(getOldVal());
    		boolean award = (newNum>=5) || (oldNum>=5);
    	
    		if (oldNum>newNum & !award) {
    			return 1;
    			
    		}
    		else if (oldNum>newNum & award) {
    			return 2;
    		}
    		
    	}
		return 0;
    	
    	
    }
   
    

}
