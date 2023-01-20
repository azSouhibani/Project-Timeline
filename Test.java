package de;


import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class Test {

    public static void main(String[] args) throws IOException, ParseException {
        // TODO Auto-generated method stub

    	String pF = "/Users/abdulaziz/Desktop/SWE-316/HWs/Samples/Projects.xls";
		
		String sF = "/Users/abdulaziz/Desktop/SWE-316/HWs/Samples/Stages.xls";
		
		String sDF = "/Users/abdulaziz/Desktop/SWE-316/HWs/Samples/Stages_Detailed.xls";


        XLS reader = new XLS();

//		construct a list that contains all project
        projectList pl = reader.readProject(pF);
//		construct a list that contains all stages
        stageList sl = reader.readStage(sF, sDF);

        project p;

//        for(int i = 0; i < pl.projects.size(); i ++) {
//            p = pl.projects.get(i);
//            
//            p.setStages(sl.getRelStages(p.getNodeID()));
//            
//            
//            if(p.getStartDate() != null && p.getEndDate() != null) 
//            	
//            	System.out.println(p.getProjectID() +"\t" + p.getDuration());
//              	
//        }
        
        for(int i = 0; i < pl.projects.size(); i ++) {
            p = pl.projects.get(i);
            
            p.setStages(sl.getRelStages(p.getNodeID()));
            
            System.out.println(p.getProjectID() + "\t" + p.getStagesNo());
            System.out.println(p.allEventLabels().toString());
            ArrayList<stage> stageArr = (ArrayList<stage>) p.relStgs.clone();
            
            for(int j = 0; j < stageArr.size(); j++) {
            	
            	String dateFormatted = Utility.toStr(stageArr.get(j).getDate().toString());
            	
            	System.out.print(dateFormatted + ", ");
            }
            
            System.out.println("\n");
        }
         
        
    }
       
        
      
    
    
	public static String tostr(Date date) {
		
		return (date.getYear()+1900) + "/" + (date.getMonth() + 1) + "/" + date.getDay() ;
		
	}
	
	
	public String tosStr(String date) {
		
		String str = date.substring(4,7);
		str = str + " " + date.substring(24,28);
		
		return str;
	}


}
