package de;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;


public class HelloApplication extends Application implements EventHandler {
	
	Button button = new Button("Search");
	TextField textField = new TextField();
	XLS reader = new XLS();
	projectList pl;
    stageList sl;
    ArrayList<Line> unit = new ArrayList<Line>();
    
	ArrayList<Integer> positionContainer = new ArrayList<Integer>();
    ArrayList<Circle> circleContainer = new ArrayList<Circle>();
    Label timeLabel;
    Label warning;
    Line time;
    int beforeAward = 0;
    int afterAward=0;
    Line startUnit;
	Line endtUnit;
	Line baseLine;
	Line redLine;
	Label label;
	ArrayList<Label> stageDates= new ArrayList<Label>();
    Label reWorks = new Label("");
    Group root = new Group();
    
    @Override
    public void start(Stage stage) throws IOException, ParseException {
        

    	String pF = "/Users/abdulaziz/Desktop/SWE-316/HWs/Samples/Projects.xls";
 		
 		String sF = "/Users/abdulaziz/Desktop/SWE-316/HWs/Samples/Stages.xls";
 		
 		String sDF = "/Users/abdulaziz/Desktop/SWE-316/HWs/Samples/Stages_Detailed.xls";

        
		pl = reader.readProject(pF);
		sl = reader.readStage(sF, sDF);
		
        ListView<String> listView = new ListView<String>();
        listView.setPrefWidth(280);

        listView.prefHeight(500);


        listView.getItems().add("\t\tProject ID" + "\t" + "\t Stages");

        project p;

        for (int i =0; i<pl.projects.size(); i++) {

            p = pl.projects.get(i);
            p.setStages(sl.getRelStages(p.getNodeID()));


            listView.getItems().add(i + "\t\t" + p.getProjectID()+ "\t\t" + p.getStage());

        }
        
        textField.setTranslateX(350);
        textField.setTranslateY(50);


        Label label1 = new Label("Project_ID");
        label1.setTranslateX(290);
        label1.setTranslateY(50);
        

        button.setTranslateX(530);
        button.setTranslateY(50);
        
        button.setOnAction(this);

        
 
        root.getChildren().add(listView);
        root.getChildren().addAll(textField);
        root.getChildren().addAll(label1);
        root.getChildren().addAll(button);

        Scene scene = new Scene(root, 1000, 500);
        scene.setFill(Color.ALICEBLUE);

        stage.setTitle("JavaFX TableView");

        stage.setScene(scene);


        stage.show();




    }

    public static void main(String[] args) {
        launch();
    }

	@Override
	public void handle(Event arg0){
		if(arg0.getSource() == button) {
			
			String pname = textField.getText();
			
			project certainp = pl.getProjectByID(pname);
			
			
			root.getChildren().remove(warning);
			if (certainp == null) {
				warning = new Label("There is no project with this ID!");
				warning.setTranslateX(600);
				warning.setTranslateY(50);
				root.getChildren().add(warning);
				return;
			}
			if(certainp.getDurationBetweenEvents().size() == 0) {
					
				warning = new Label("There is no date for this project!");
				warning.setTranslateX(600);
				warning.setTranslateY(50);
				root.getChildren().add(warning);
				return;
				
			}
			
			
			int durationIndex = certainp.getDurationBetweenEvents().size()-1;
			
			int durr= certainp.getDurationBetweenEvents().get(durationIndex);
			
			ArrayList<Integer> stagess = certainp.getDurationBetweenEvents();
			
			
			System.out.println(stagess.toString());
			
			drawBaseLine(durr);
			drawUnits(durr);
			drawDurationLine(durr);
			addStages(stagess,certainp);
			getReWorks( certainp);
			

			root.getChildren().remove(timeLabel);
			
			timeLabel = new Label("Duration:"+durr+ " days");
			timeLabel.setTranslateX(370);
			timeLabel.setTranslateY(315);
			timeLabel.setStyle("-fx-stroke: red;");
			
			root.getChildren().add(timeLabel);
		
		}
		
	}
	
	public void drawBaseLine(int totalDuration) {
		
		root.getChildren().remove(startUnit);
		root.getChildren().remove(endtUnit);
		root.getChildren().remove(baseLine);		
		
		int len = (Utility.durationInMonths(totalDuration) * 30) * 5 + 100;
		
		startUnit = new Line(100,595,100,605);
		
		endtUnit = new Line(len,595,len,605);
		
		baseLine = new Line (100 , 600, len , 600);
		
		root.getChildren().add(baseLine);
		root.getChildren().add(startUnit);
		root.getChildren().add(endtUnit);

		
	}
	
	public void drawUnits(int totalDuration) {
		
		root.getChildren().removeAll(unit);
		unit.clear();
		
		// this is number of days where each unit represent a day (this is after rounding off)
		int numberOfunits = Utility.durationInMonths(totalDuration) * 30;

		// this is the width between each unit in pixel
		int width = 5;
		
		int incr = 100;
		
		for (int j = 0; j < numberOfunits; j++) {
			
			unit.add(new Line( incr + width ,595, incr + width ,600));
			incr = incr + width;

		}
		
		root.getChildren().addAll(unit);
		
	}
	
	public void drawDurationLine(int totalDuration) {
		
		root.getChildren().remove(redLine);
		
		int val = 100 + totalDuration * 5;
		
		redLine = new Line(100,470,val,470);
		redLine.setStyle("-fx-stroke: red; -fx-color: red");
		
		root.getChildren().add(redLine);
	}

	public void addStages(ArrayList<Integer> totalDuration, project certainp) {
		
			
		
		int spaces=0;
		root.getChildren().removeAll(circleContainer);
		root.getChildren().removeAll(stageDates);
		stageDates.clear();
		circleContainer.clear();
		positionContainer.clear();
		int startDatePosition = 100 ;
		positionContainer.add(startDatePosition);
		
		label = new Label(Utility.toStr(certainp.getStartDate().toString()));
		label.setTranslateX(startDatePosition-9);
		label.setTranslateY(610);
		label.setFont(new Font("Arial", 7));
		label.setRotate(60);
		stageDates.add(label);
		
		//System.out.println(certainp.allEventLabels().toString());
		for (int i =0; i<totalDuration.size(); i++) {
			
			// 113 113 113 113
			int position = 100 + totalDuration.get(i) * 5;
			if (positionContainer.contains(position)) {
				 spaces = spaces -9;
				Circle circle = new Circle(position, 590 +spaces, 3);
				
				stageIndicator(circle,certainp.relStgs.get(i));
				
			
				circleContainer.add(circle);
			}
			else {
				
			Circle circle = new Circle(position, 590 , 3);
			Label label = new Label(certainp.allEventLabels().get(i));
			label.setTranslateX(position-9);
			label.setTranslateY(620);
			label.setFont(new Font("Arial", 7));
			label.setRotate(60);
			
			stageIndicator(circle,certainp.relStgs.get(i));
			circleContainer.add(circle);
			positionContainer.add(position);
			stageDates.add(label);
			
			}
		
		}
		
		
		root.getChildren().addAll(circleContainer);
		root.getChildren().addAll(stageDates);
		
		
	}
	
	public void stageIndicator(Circle circle,stage stage) {
		if (stage.getStatus()>0) {
			circle.setFill(Color.LIGHTGREEN);
			}
			else if (stage.getStatus()==0) {
				circle.setFill(Color.RED);
			}
			else if (stage.getStatus()==-1) {
				circle.setFill(Color.BLACK);
			}
	}
	
	public void getReWorks( project certainp) {
		root.getChildren().remove(reWorks);
		 this.beforeAward = 0;
		 this.afterAward=0;
		for (int i =0; i<certainp.relStgs.size(); i++) {
			
			if (certainp.relStgs.get(i).getReWorks()==1) {
				this.beforeAward= this.beforeAward+1;
			}
			if (certainp.relStgs.get(i).getReWorks()==2) {
				this.afterAward=this.afterAward+1;
			}
			
		}
		System.out.println(this.beforeAward);
		System.out.println(this.afterAward);
		reWorks = new Label ("Reworks before award: " + this.beforeAward + "        " + "Reworks after award: " + " " + this.afterAward);
		reWorks.setTranslateX(500);
		reWorks.setTranslateY(315);
		reWorks.setStyle("-fx-stroke: red;");
		root.getChildren().add(reWorks);
	}
		
	}
	


