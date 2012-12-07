package com.mirantis.aminakov.student;

import java.text.ParseException;
import java.util.Scanner;
import java.io.*;

public class Cli {
	Scanner sc = new Scanner(System.in);
	Storage storage = new InMemoryHashMapStorage();
	OutputStream os = new PrintStream(System.out);
	
	public void run() {
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
				int id = sc.nextInt();
				/*		for(int i = 0; i < students.size(); i++) {
							student = students.get(i);
							if(student.getId() == id){
								student.toString();
								students.remove(student);
							}*/
				storage.deleteStudent(id); 		
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
					Student stud = new Student();
					switch (answer) {
						case 1:
						System.out.println("Enter ID.");
						int idd = sc.nextInt();
						stud.setId(idd);
						storage.findByExample(stud);
						break;
					case 2:
						System.out.println("Enter surname.");
						String sur = sc.next();
						stud.setSurname(sur);
						storage.findByExample(stud);
						break;
					case 3:
						System.out.println("Enter name.");
						String nam = sc.next();
						stud.setName(nam);
						storage.findByExample(stud);
						break;
					case 4:
						System.out.println("Enter patronymic.");
						String pat = sc.next();
						stud.setPatronymic(pat);
						storage.findByExample(stud);
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
						storage.findByExample(stud);
						
/*//						System.out.println("Read line = " + find_date);
						for (Student s : students.values()) {
							
							SimpleDateFormat sdf = new SimpleDateFormat(
									"dd MMMM yy", Locale.ROOT);
							Date d = null;
//							System.out.println("Befor block <try>");
							try {
								d = sdf.parse(find_date);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								//e.printStackTrace();
								System.err.println("Error " + e);
								System.out.println("Error");
								break;
							}
//							System.out.println("After block <try>");
							String string_date = d.toString();
//							System.out.println("string_date = " + find_date);
							if (s.getDate().toString().equals(string_date))
								System.out.println(s.toString());
						}*/
						
						break;
					case 6:
						System.out.println("Enter address.");
						String adr = sc.next();
						stud.setAddress(adr);
						storage.findByExample(stud);						
						break;
					case 7:
						System.out.println("Enter phone.");
						String phn = sc.next();
						stud.setPhone(phn);
						storage.findByExample(stud);
						break;
					case 8:
						System.out.println("Enter faculty.");
						String fac = sc.next();
						stud.setFaculty(fac);
						break;
					case 9:
						System.out.println("Enter course.");
						int crs = sc.nextInt();
						stud.setCourse(crs);
						storage.findByExample(stud);
						break;
					case 10:
						System.out.println("Enter group.");
						String grp = sc.next();
						stud.setGroup(grp);
						storage.findByExample(stud);
						break;
					case 11: 
						break all;
					default:
						break all;
					}
				}
				continue all_outer;
			case 4:
				storage.printList(os);		//
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
		Cli cli=new Cli();
		cli.run();	
	}				
}
