package com.mirantis.aminakov.student;

import java.util.*;
import java.text.*;
import com.mirantis.aminakov.annotation.InputIgnore;

public class Student {
	@InputIgnore(true)
	private int id;
	@InputIgnore(false)
	private String name;
	@InputIgnore(false)
	private String surname;
	@InputIgnore(false)
	private String patronymic;
	@InputIgnore(false)
	private String date;
	@InputIgnore(false)
	private String address;
	@InputIgnore(false)
	private String phone;
	@InputIgnore(false)
	private String faculty;
	@InputIgnore(false)
	private int course;
	@InputIgnore(false)
	private String group;
	
	
	public static Student buildExampleStudent() {
		Student student = new Student();
		student.id = -1;
		student.surname = "";
		student.patronymic = "";
		student.name = "";
		student.date = "1 january 1";
		student.address = "";
		student.phone = "";
		student.faculty = "";
		student.course = -1;
		student.group = "";
		return student;
	}
	
	public void setId(int id) {
		this.id = id;
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

}

