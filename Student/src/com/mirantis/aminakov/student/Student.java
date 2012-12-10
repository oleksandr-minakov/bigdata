package com.mirantis.aminakov.student;

import java.util.*;
import java.text.*;

import javax.annotation.PostConstruct;

public class Student {
	
	private int id;
	private String name;
	private String surname;
	private String patronymic;
	private String date;
	private String address;
	private String phone;
	private String faculty;
	private int course;
	private String group;
	
	public Student() {
		
	}
	
	public Student(int i) {
		id = -1;
		surname = "";
		patronymic = "";
		name = "";
		date = "1 january 1";
		address = "";
		phone = "";
		faculty = "";
		course = -1;
		group = "";
	}
	
	public static Student buildExampleStudent(){
		//TODO: init here
		return null;
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

