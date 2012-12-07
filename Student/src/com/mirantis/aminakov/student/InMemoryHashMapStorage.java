package com.mirantis.aminakov.student;

import java.text.*;
import java.util.*;


public class InMemoryHashMapStorage implements Storage {
	private Map<String, Student> students=new HashMap<>();
	private int maxCapacity=100;
	
	public void addStudent(Student student) {
		
		Student student = new Student();
		student.setId();
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
		students.put(String.valueOf(student.getId()), student);
		System.out.println(student.toString());

	}
	public static void deleteStudent(int id) {
//		Student student;
		if (!students.isEmpty()) {
			System.out.println("Enter ID");
			String id = sc.next();
//					for(int i = 0; i < students.size(); i++) {
//						student = students.get(i);
//						if(student.getId() == id){
//							student.toString();
//							students.remove(student);
//						}
			students.remove(id);
		} else {
			System.out.println("List is empty!!!");
		}
		
	}
	public static void findStudent(HashMap<String, Student> students, Scanner sc) {
		if (!students.isEmpty()) {
			all: while (true) {
				System.out.println("Find by..." + "\n" + "ID. Press 1." + "\n"
						+ "surname. Press 2." + "\n" + "name. Press 3." + "\n"
						+ "patronymic. Press 4." + "\n" + "date. Press 5."
						+ "\n" + "address. Press 6." + "\n" + "phone. Press 7."
						+ "\n" + "faculty. Press 8." + "\n"
						+ "course. Press 9." + "\n" + "group. Press 10." 
						+ "\n" + "EXIT. Press 11.");
				int answ = sc.nextInt();
				Student student;
				switch (answ) {
					case 1:
					System.out.println("Enter ID.");
					String id = sc.next();
//					if(id > 0 && id < (students.size() - 1)) {
						student = students.get(id);
						student.toString();
//					} else {
//						System.out.println("Wrong ID!!!");
//					}
					break;
				case 2:
					System.out.println("Enter surname.");
					String surname = sc.next();
					for (Student s : students.values()) {
						if (s.getSurname().equalsIgnoreCase(surname))
							System.out.println(s.toString());
					}
					break;
				case 3:
					System.out.println("Enter name.");
					String name = sc.next();
					for (Student s : students.values()) {
							if (s.getName().equalsIgnoreCase(name))
							System.out.println(s.toString());
					}
					break;
				case 4:
					System.out.println("Enter patronymic.");
					String patronymic = sc.next();
					for (Student s : students.values()) {
						if (s.getPatronymic().equalsIgnoreCase(patronymic))
							System.out.println(s.toString());
					}
					break;
				case 5:
					System.out.println("Enter date, (12 December 1990)");
					sc.nextLine();
					String find_date = sc.nextLine();
//					System.out.println("Read line = " + find_date);
					for (Student s : students.values()) {
						
						SimpleDateFormat sdf = new SimpleDateFormat(
								"dd MMMM yy", Locale.ROOT);
						Date d = null;
//						System.out.println("Befor block <try>");
						try {
							d = sdf.parse(find_date);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
							System.err.println("Error " + e);
							System.out.println("Error");
							break;
						}
//						System.out.println("After block <try>");
						String string_date = d.toString();
//						System.out.println("string_date = " + find_date);
						if (s.getDate().toString().equals(string_date))
							System.out.println(s.toString());
					}
					break;
				case 6:
					System.out.println("Enter address.");
					String address = sc.next();
					for (Student s : students.values()) {
						if (s.getAddress().equalsIgnoreCase(address))
							System.out.println(s.toString());
					}
					break;
				case 7:
					System.out.println("Enter phone.");
					String phone = sc.next();
					for (Student s : students.values()) {
						if (s.getPhone().equalsIgnoreCase(phone))
							System.out.println(s.toString());
					}
					break;
				case 8:
					System.out.println("Enter faculty.");
					String faculty = sc.next();
					for (Student s : students.values()) {
						if (s.getFaculty().equalsIgnoreCase(faculty))
							System.out.println(s.toString());
					}
					break;
				case 9:
					System.out.println("Enter course.");
					int course = sc.nextInt();
					for (Student s : students.values()) {
						if (s.getCourse() == course)
							System.out.println(s.toString());
					}
					break;
				case 10:
					System.out.println("Enter group.");
					String group = sc.next();
					for (Student s : students.values()) {
						if (s.getGroup().equalsIgnoreCase(group))
							System.out.println(s.toString());
					}
					break;
				case 11: 
					break all;
				default:
					break all;
				}
			}
		} else { 
			System.out.println("List is empty!!!");
		}
	}
	public static void printList(HashMap<String, Student> students) {
		for(Student s: students.values()) {
			System.out.println(s.toString());
		}
	}
	@Override
	public int addStudent(Student student) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void deleteStudent(int id) {
		// TODO Auto-generated method stub
		
	}

}
