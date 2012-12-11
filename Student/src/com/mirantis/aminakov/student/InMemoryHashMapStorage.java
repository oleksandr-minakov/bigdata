package com.mirantis.aminakov.student;

import java.io.*;
import java.util.*;


public class InMemoryHashMapStorage implements Storage {
	private Map<String, Student> students = new HashMap<>();
	private int maxCapacity = 100;
	private int id = 0;
	private int capacity = 0;
	
	public int addStudent(Student student) {
		if (capacity < maxCapacity) {
			student.setId(id);
			students.put(String.valueOf(student.getId()), student);
			capacity++;
			id++;
			return student.getId();
		} else {
			return -1;
		}
	}
	
	public void deleteStudent(String id_del) {
		if (!students.isEmpty()) {
			students.remove(id_del);
		} else {
			System.out.println("List is empty!!!"); //Replace by the log4j
		}
		
	}
	
	public void printList(OutputStream os) {
		Writer osw = new OutputStreamWriter(os);
		for(Student s: students.values()) {
			try {
				osw.write(s.toString() + "\n");
				osw.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public List<Student> findByExample(Student student) {
		List<Student> find_students = new ArrayList<>();
		if (!students.isEmpty()) {
			if (!(student.getId() == -1)) {
				for (Student s : students.values()) {
					if (s.getId() == student.getId())
				//Student s = students.get(student.getId());
				find_students.add(s);
				}	
			} else if (!(student.getSurname() == "")) {
				for (Student s : students.values()) {
					if (s.getSurname().equalsIgnoreCase(student.getSurname()))
						find_students.add(s);
				}
			} else if (!(student.getName() == "")) {
				for (Student s : students.values()) {
					if (s.getName().equalsIgnoreCase(student.getName()))
						find_students.add(s);
				}
			} else if (!(student.getPatronymic() == "")) {
				for (Student s : students.values()) {
					if (s.getPatronymic().equalsIgnoreCase(student.getPatronymic()))
						find_students.add(s);
				}
			} else if (!(student.getDate() == "1 january 1")) {
				for (Student s : students.values()) {
					if (s.getDate().toString().equals(student.getDate().toString()))
						find_students.add(s);
				}
			} else if (!(student.getAddress() == "")) {
				for (Student s : students.values()) {
					if (s.getAddress().equalsIgnoreCase(student.getAddress()))
						find_students.add(s);
				}
			} else if (!(student.getPhone() == "")) {
				for (Student s : students.values()) {
					if (s.getPhone().equalsIgnoreCase(student.getPhone()))
						find_students.add(s);
				}
			} else if (!(student.getFaculty() == "")) {
				for (Student s : students.values()) {
					if (s.getFaculty().equalsIgnoreCase(student.getFaculty()))
						find_students.add(s);
				}
			} else if (!(student.getCourse() == -1)) {
				for (Student s : students.values()) {
					if (s.getCourse() == student.getCourse())
						find_students.add(s);
				}
			} else if (!(student.getGroup() == "")) {
				for (Student s : students.values()) {
					if (s.getGroup().equalsIgnoreCase(student.getGroup()))
						find_students.add(s);
				}
			}
		} else {
			//logging
			System.out.println("List is empty!!!"); //Replace by the log4j
		}
		return find_students;
	}

	public boolean isCapacityReached() {
		if (capacity < maxCapacity) {
			return false;
		} else {
			return true;
		}
	}
}
