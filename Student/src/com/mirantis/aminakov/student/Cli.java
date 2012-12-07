package com.mirantis.aminakov.student;

import java.util.HashMap;
import java.util.Scanner;

public class Cli {
	Scanner sc = new Scanner(System.in);
	Storage storage=null;// new InMemoryHash....
	
	public void run(){
		
	}
	
	public static void main(String[] args) {
		Cli cli=new Cli();
		cli.run();
		
		
		
		
		// TODO Auto-generated method stub
		HashMap<String, Student> students = new HashMap<String, Student>();
		all:
		while (true) {
			System.out.println("Select the action:" + "\n"
					+ "Add student. Press 1." + "\n"
					+ "Delete student. Press 2." + "\n"
					+ "Find student. Press 3." + "\n" + "Show list. Press 4."
					+ "\n" + "Exit. Press 5.");
			int answ = sc.nextInt();
			switch (answ) {
			case 1:
				InMemoryHashMapStorage.addStudent(students, sc);
				continue all ;
			case 2:
				InMemoryHashMapStorage.deleteStudent(students, sc);
				continue all;
			case 3:
				InMemoryHashMapStorage.findStudent(students, sc);
				continue all;
			case 4:
				InMemoryHashMapStorage.printList(students);
				continue all;
			case 5:
				System.out.println("Exit...");
				break all;
			default:
				System.out.println("Exit...");
				break all;
			}
		}
	}				
}
