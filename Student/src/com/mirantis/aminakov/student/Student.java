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
	
	public void setId() {
		this.id = next_id;
		next_id++;
	}	
	public void setName(String name) {
		this.name = name;
	}	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}
	public void setDate(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yy", Locale.ROOT);
		Date d = null;
		d = sdf.parse(date);
		this.date = d.toString();
	
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setPhone(String phone) {
		this.phone = phone;		
	}
	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}
	public void setCourse(int course) {
		this.course = course;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}
	public String getPatronymic() {
		return patronymic;
	}
	public String getDate() {
		return date.toString();
	}
	public String getAddress() {
		return address;
	}
	public String getPhone() {
		return phone;
	}
	public String getFaculty() {
		return faculty;
	}
	public int getCourse() {
		return course;
	}
	public String getGroup() {
		return group;
	}
	@Override
	public String toString() {
		return id + " " + surname + " " + name + " " + patronymic + " "  + 
				date + " " + address + " " + phone + " " + faculty +
				" " + course + " " + group + " ";
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
				Storage.addStudent(students, sc);
				continue all ;
			case 2:
				Storage.deleteStudent(students, sc);
				continue all;
			case 3:
				Storage.findStudent(students, sc);
				continue all;
			case 4:
				Storage.printList(students);
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

