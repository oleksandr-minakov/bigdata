package com.mirantis.aminakov.cli;


import java.text.ParseException;
import java.util.*;
import java.io.*;

import com.mirantis.aminakov.exceptions.*;
import com.mirantis.aminakov.storage.InMemoryHashMapStorage;
import com.mirantis.aminakov.storage.Storage;
import com.mirantis.aminakov.student.Student;
import com.mirantis.aminakov.ups.UniversalSetter;

public class Cli {
	Scanner sc = new Scanner(System.in);
	Storage storage = new InMemoryHashMapStorage();
	OutputStream os = new PrintStream(System.out);
	
	public void run() {
		while (true) {
			System.out.println("Select the action:" + "\n"
					+ "Add student. Press 1." + "\n"
					+ "Delete student. Press 2." + "\n"
					+ "Find student. Press 3." + "\n" + "Show list. Press 4."
					+ "\n" + "Exit. Press 5.");
			int answ = sc.nextInt();
			switch (answ) {
			case 1:
				Student student = new Student();
				System.out.println("Surname");
				String sn = sc.next();
				student.setSurname(sn);
				System.out.println("Name");
				String n = sc.next();
				student.setName(n);
				System.out.println("Patronymic");
				String p = sc.next();
				student.setPatronymic(p);
				System.out.println("Enter date of birth (12 December 1990)");
				int attempts = 0;
				sc.nextLine();
				while(true) {
					try {
						String date = sc.nextLine();
						student.setDate(date);
						break;
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						System.err.println("Error " + e);
						attempts++;
						if(attempts == 5) {
							System.out.println("Exit ...");
							sc.close();
							break;	
						}
						System.out.println("Try again");
						
					}
				}
				System.out.println("Enter the address");
				student.setAddress(sc.next());
				System.out.println("Enter phone");
				student.setPhone(sc.next());
				System.out.println("Enter faculty");
				student.setFaculty(sc.next());
				System.out.println("Enter course (1-6)");
				student.setCourse(sc.nextInt());
				System.out.println("Enter group");
				student.setGroup(sc.next()); 
				System.out.println(student.toString());
				try {
					storage.addStudent(student);
				} catch (AddException e1) {
					// TODO Auto-generated catch block
					System.out.println("Error adding ---> " + e1);
				}	
				continue;
			case 2:
				System.out.println("Enter ID");
				String idDel = sc.next();
				try {
					storage.deleteStudent(idDel);
				} catch (DeleteException e1) {
					// TODO Auto-generated catch block
					System.out.println("Error removing ---> " + e1);
				} 		
				continue;
			case 3:
				HashMap<String, String> findMap = new HashMap<>();
				System.out.println("Enter name of field and value (name=Vasya or phone=123456789)");
				sc.nextLine();
				String findPar = sc.nextLine();
//				StringTokenizer st = new StringTokenizer(findPar, "=");
				int i;
				i = findPar.indexOf('=');
				String part1 = findPar.substring(0, i);
				String part2 = findPar.substring(i+1, findPar.length());
				/*while (st.hasMoreTokens()) {
					part1 = st.nextToken();
					part2 = st.nextToken();
					System.out.println(part1);
					System.out.println(part2);
					findMap.put(part1, part2);
				}*/
				System.out.println("Key = " + part1);
				System.out.println("Value = " + part2);
				findMap.put(part1, part2);
				Student std = Student.buildExampleStudent();
				UniversalSetter u = new UniversalSetter(std);
				u.setFields(findMap);
				System.out.println(std);
				for(Student s : storage.findByExample(std)) {
					System.out.println(s.toString());
				}
				continue;
			case 4:
				storage.printList(os);	
				continue;
			case 5:
				System.out.println("Exit...");
				break;
			default:
				System.out.println("Exit...");
				break;
			}
			break;
		}
	}
	
	public static void main(String[] args) {
		Cli cli = new Cli();
		cli.run();	
	}				
}