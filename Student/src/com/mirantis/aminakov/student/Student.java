package com.mirantis.aminakov.student;

import java.util.*;
import java.text.*;

public class Student {
	
	private int id;
	private static int next_id = 0;
	private String name;
	private String surname;
	private String patronymic;
	private String date;
	private String address;
	private String phone;
	private String faculty;
	private int course;
	private String group;
	
	public void setStudentId() {
		this.id = next_id;
		next_id++;
	}	
	public void setStudentName(String name) {
		this.name = name;
	}	
	public void setStudentSurname(String surname) {
		this.surname = surname;
	}
	public void setStudentPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}
	public void setStudentDate(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yy", Locale.ROOT);
		Date d = null;
		d = sdf.parse(date);
		this.date = d.toString();
	
	}
	public void setStudentAddress(String address) {
		this.address = address;
	}
	public void setStudentPhone(String phone) {
		this.phone = phone;		
	}
	public void setStudentFaculty(String faculty) {
		this.faculty = faculty;
	}
	public void setStudentCourse(int course) {
		this.course = course;
	}
	public void setStudentGroup(String group) {
		this.group = group;
	}
	
	public int getStudentId() {
		return id;
	}
	public String getStudentName() {
		return name;
	}
	public String getStudentSurname() {
		return surname;
	}
	public String getStudentPatronymic() {
		return patronymic;
	}
	public String getStudentDate() {
		return date.toString();
	}
	public String getStudentAddress() {
		return address;
	}
	public String getStudentPhone() {
		return phone;
	}
	public String getStudentFaculty() {
		return faculty;
	}
	public int getStudentCourse() {
		return course;
	}
	public String getStudentGroup() {
		return group;
	}
	@Override
	public String toString() {
		return id + " " + surname + " " + name + " " + patronymic + " "  + 
				date + " " + address + " " + phone + " " + faculty +
				" " + course + " " + group + " ";
	}

	
	public static void addStudent(List<Student> students, Scanner sc) {
		Student student = new Student();
		student.setStudentId();
		System.out.println("Surname");
		String sn = sc.next();
		student.setStudentSurname(sn);
		System.out.println("Name");
		String n = sc.next();
		student.setStudentName(n);
		System.out.println("Patronymic");
		String p = sc.next();
		student.setStudentPatronymic(p);
		System.out.println("Enter date of birth (12 December 1990)");
		int attempts = 0;
		sc.nextLine();
		while(true) {
			try {
				String date = sc.nextLine();
				student.setStudentDate(date);
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
		String addr = sc.next();
		student.setStudentAddress(addr);
		System.out.println("Enter phone");
		String phone = sc.next();
		student.setStudentPhone(phone);
		System.out.println("Enter faculty");
		student.faculty = sc.next();
		System.out.println("Enter course (1-6)");
		student.course = sc.nextByte();
		System.out.println("Enter group");
		student.group = sc.next();
		students.add(student);
		System.out.println(student.toString());

	}
	public static void deleteStudent(List<Student> students, Scanner sc) {
		Student student;
		if (students.size() > 0) {
			System.out.println("Students.size = " + students.size());
			//for(Student s: students) {
			//	System.out.println("Index of = " + students.indexOf(s));
			//}
			System.out.println("Enter ID");
			int id = sc.nextInt();
			//if (id >= 0 && id <= (students.size() - 1)) {
					for(int i = 0; i < (students.size()); i++) {
						student = students.get(i);
						if(student.id == id){
							student.toString();
							students.remove(student);
						} else {
							System.out.println("Wrong ID!!!");
						}
					}
				
		} else {
			System.out.println("List is empty!!!");
		}
		
	}
	public static void findStudent(List<Student> students, Scanner sc) {
		if (students.size() > 0) {
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
					int id = sc.nextInt();
					if(id > 0 && id < (students.size() - 1)) {
						student = students.get(id);
						student.toString();
					} else {
						System.out.println("Wrong ID!!!");
					}
					break;
				case 2:
					System.out.println("Enter surname.");
					String surname = sc.next();
					for (int i = 0; i < (students.size() - 1); i++) {
						student = students.get(i);
						if (student.surname.equalsIgnoreCase(surname))
							System.out.println(student.toString());
					}
					break;
				case 3:
					System.out.println("Enter name.");
					String name = sc.next();
					for (int i = 0; i < (students.size() - 1); i++) {
						student = students.get(i);
						if (student.name.equalsIgnoreCase(name))
							System.out.println(student.toString());
					}
					break;
				case 4:
					System.out.println("Enter patronymic.");
					String patronymic = sc.next();
					for (int i = 0; i < (students.size() - 1); i++) {
						student = students.get(i);
						if (student.patronymic.equalsIgnoreCase(patronymic))
							System.out.println(student.toString());
					}
					break;
				case 5:
					System.out.println("Enter date, (12 December 1990)");
					for (int i = 0; i < (students.size() - 1); i++) {
						student = students.get(i);
						sc.nextLine();
						String find_date = sc.nextLine();
						SimpleDateFormat sdf = new SimpleDateFormat(
								"dd MMMM yy", Locale.ROOT);
						Date d = null;
						try {
							d = sdf.parse(find_date);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
							System.err.println("Error " + e);
							System.out.println("Error");
							break;
						}
						String string_date = d.toString();
						if (student.date.toString().equalsIgnoreCase(
								string_date))
							System.out.println(student.toString());
					}
					break;
				case 6:
					System.out.println("Enter address.");
					String address = sc.next();
					for (int i = 0; i < (students.size() - 1); i++) {
						student = students.get(i);
						if (student.address.equalsIgnoreCase(address))
							System.out.println(student.toString());
					}
					break;
				case 7:
					System.out.println("Enter phone.");
					String phone = sc.next();
					for (int i = 0; i < (students.size() - 1); i++) {
						student = students.get(i);
						if (student.phone.equalsIgnoreCase(phone))
							System.out.println(student.toString());
					}
					break;
				case 8:
					System.out.println("Enter faculty.");
					String faculty = sc.next();
					for (int i = 0; i < (students.size() - 1); i++) {
						student = students.get(i);
						if (student.faculty.equalsIgnoreCase(faculty))
							System.out.println(student.toString());
					}
					break;
				case 9:
					System.out.println("Enter course.");
					int course = sc.nextInt();
					for (int i = 0; i < (students.size() - 1); i++) {
						student = students.get(i);
						if (student.course == course)
							System.out.println(student.toString());
					}
					break;
				case 10:
					System.out.println("Enter group.");
					String group = sc.next();
					for (int i = 0; i < (students.size() - 1); i++) {
						student = students.get(i);
						if (student.group.equalsIgnoreCase(group))
							System.out.println(student.toString());
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
	public static void printList(List<Student> students) {
		for(Student s: students) {
			System.out.println(s.toString());
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		List<Student> students = new ArrayList<Student>();
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
				addStudent(students, sc);
				continue all ;
			case 2:
				deleteStudent(students, sc);
				continue all;
			case 3:
				findStudent(students, sc);
				continue all;
			case 4:
				printList(students);
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

