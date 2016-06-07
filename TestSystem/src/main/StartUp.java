package main;

import View.ComandView;
import View.GUIView;
import javafx.application.Application;
import javafx.stage.Stage;

public class StartUp{
	public static void main(String[] args){
		ComandView view = new ComandView();
		while(true){
			view.printFirstMenu();
		}
		
		//new GUIView().launchGUI(args);
		
	}
}
