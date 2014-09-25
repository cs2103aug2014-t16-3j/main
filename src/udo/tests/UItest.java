package udo.tests;

import udo.main.UserInterface;

public class UItest {
	
	public static void main (String[] args){
		UserInterface ui = new UserInterface();
		while(true){
			System.out.println(ui.getInput());
		}
		
	}
	
}
