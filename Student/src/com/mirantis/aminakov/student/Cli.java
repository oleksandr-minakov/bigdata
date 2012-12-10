package com.mirantis.aminakov.student;

import java.text.ParseException;
import java.util.Scanner;
import java.io.*;

public class Cli {
	Scanner sc = new Scanner(System.in);
	Storage storage = new InMemoryHashMapStorage();
	OutputStream os = new PrintStream(System.out);
	
	public void run() {
		//FIXME: remove labels
		all_outer:
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
				//students.put(String.valueOf(student.getId()), student);
				System.out.println(student.toString());
				storage.addStudent(student);	//
				continue all_outer;
			case 2:
				System.out.println("Enter ID");
				String id_del = sc.next();
				storage.deleteStudent(id_del); 		
				continue all_outer;
			case 3:
				all: 
					while (true) {
					System.out.println("Find by..." + "\n" + "ID. Press 1." + "\n"
							+ "surname. Press 2." + "\n" + "name. Press 3." + "\n"
							+ "patronymic. Press 4." + "\n" + "date. Press 5."
							+ "\n" + "address. Press 6." + "\n" + "phone. Press 7."
							+ "\n" + "faculty. Press 8." + "\n"
							+ "course. Press 9." + "\n" + "group. Press 10." 
							+ "\n" + "EXIT. Press 11.");
					int answer = sc.nextInt();
					Student stud = new Student(-1);
					switch (answer) {
						case 1:
						System.out.println("Enter ID.");
						int id_f = sc.nextInt();
						stud.setId(id_f);
						for(Student s : storage.findByExample(stud)) {
							System.out.println(s.toString());
						}
						break;
					case 2:
						System.out.println("Enter surname.");
						String sur = sc.next();
						stud.setSurname(sur);
						for(Student s : storage.findByExample(stud)) {
							System.out.println(s.toString());
						}
						break;
					case 3:
						System.out.println("Enter name.");
						String nam = sc.next();
						stud.setName(nam);
						for(Student s : storage.findByExample(stud)) {
							System.out.println(s.toString());
						}
						break;
					case 4:
						System.out.println("Enter patronymic.");
						String pat = sc.next();
						stud.setPatronymic(pat);
						for(Student s : storage.findByExample(stud)) {
							System.out.println(s.toString());
						}
						break;
					case 5:
						System.out.println("Enter date, (12 December 1990)");
						sc.nextLine();
						String find_date = sc.nextLine();
						try {
							stud.setDate(find_date);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						for(Student s : storage.findByExample(stud)) {
							System.out.println(s.toString());
						}
						break;
					case 6:
						System.out.println("Enter address.");
						String adr = sc.next();
						stud.setAddress(adr);
						for(Student s : storage.findByExample(stud)) {
							System.out.println(s.toString());
						}					
						break;
					case 7:
						System.out.println("Enter phone.");
						String phn = sc.next();
						stud.setPhone(phn);
						for(Student s : storage.findByExample(stud)) {
							System.out.println(s.toString());
						}
						break;
					case 8:
						System.out.println("Enter faculty.");
						String fac = sc.next();
						stud.setFaculty(fac);
						for(Student s : storage.findByExample(stud)) {
							System.out.println(s.toString());
						}
						break;
					case 9:
						System.out.println("Enter course.");
						int crs = sc.nextInt();
						stud.setCourse(crs);
						for(Student s : storage.findByExample(stud)) {
							System.out.println(s.toString());
						}
						break;
					case 10:
						System.out.println("Enter group.");
						String grp = sc.next();
						stud.setGroup(grp);
						for(Student s : storage.findByExample(stud)) {
							System.out.println(s.toString());
						}
						break;
					case 11: 
						break all;
					default:
						break all;
					}
				}
				continue all_outer;
			case 4:
				storage.printList(os);	
				continue all_outer;
			case 5:
				System.out.println("Exit...");
				break all_outer;
			default:
				System.out.println("Exit...");
				break all_outer;
			}
		}
	}
	
	public static void main(String[] args) {
		Cli cli = new Cli();
		cli.run();	
	}				
}