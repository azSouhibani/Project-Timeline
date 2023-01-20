package de;

import java.util.ArrayList;
//import java.util.Iterator;

public class stageList {

    private ArrayList<stage> stages = new ArrayList<stage>();

    public void addComStage(String[] stage, String[] det) {

        String[] comInfo = new String[10];
        int i;

        for(i=0; i < 7; i++) {
            comInfo[i] = stage[i];
        }

        for(i=0; i < 3; i++) {
            comInfo[i+7] = det[i+2];
        }

        stage s = new stage(comInfo);
        stages.add(s);

    }


    public stage getCertainStage(String nodeID, String DocNum) {
        stage s;

        for (int i = 0; i < stages.size(); i++) {

            s = stages.get(i);

            if (s.getProjectRef().equals(nodeID) && s.getDocument().equals(DocNum)) {
                return s;
            }

        }
        return null;
    }


    public ArrayList<stage> getRelStages(String nodeID) {

        ArrayList<stage> pStages = new ArrayList<>();

        stage s;

        for (int i = 0; i < stages.size(); i++) {

            s = stages.get(i);

            if (s.getProjectRef().equals(nodeID)) {
                pStages.add(s);
            }

        }

        return pStages;
    }

}