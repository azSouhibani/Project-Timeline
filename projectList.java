package de;


import java.util.ArrayList;

class projectList {

    ArrayList<project> projects = new ArrayList<project>();

    public void addProject(String[] info) {
        project p = new project(info);
        projects.add(p);

    }

    public project getProject(String nodeID) {
        project p;
        for(int i=0; i < projects.size(); i++) {
            p = projects.get(i);
            if(p.getNodeID().equals(nodeID))
                return p;
        }
        return null;
    }
    
    
    public project getProjectByID(String ID) {
        project p;
        for(int i=0; i < projects.size(); i++) {
        	
            p = projects.get(i);
            
            if(p.getProjectID().equals(ID))
                return p;
        }
        return null;
    }


}

