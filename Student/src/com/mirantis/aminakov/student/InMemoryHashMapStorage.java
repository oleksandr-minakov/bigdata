package com.mirantis.aminakov.student;

import java.io.*;
import java.util.*;


public class InMemoryHashMapStorage implements Storage {
	private Map<String, Student> students = new HashMap<>();
	private int maxCapacity = 100;
	private int id = 0;
	private int capacity = 0;
	
	public int addStudent (Student student) throws AddException {
		if (capacity < maxCapacity) {
			student.setId(id);
			students.put(String.valueOf(student.getId()), student);
			capacity++;
			id++;
			return student.getId();
		} else {
			throw new AddException();
		}
	}
	
	public int deleteStudent(String idDel) throws DeleteException {
		if (!students.isEmpty()) {
			students.remove(idDel);
			return 1;
		} else {
			throw new DeleteException();
//			System.out.println("List is empty!!!"); //Replace by the log4j
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
		List<Student> findStudents = new ArrayList<>();
		if (!students.isEmpty()) {
			if (!(student.getId() == -1)) {
				for (Student s : students.values()) {
					if (s.getId() == student.getId())
				//Student s = students.get(student.getId());
				findStudents.add(s);
				}	
			} else if (!(student.getSurname() == "")) {
				for (Student s : students.values()) {
					if (s.getSurname().equalsIgnoreCase(student.getSurname()))
						findStudents.add(s);
				}
			} else if (!(student.getName() == "")) {
				for (Student s : students.values()) {
					if (s.getName().equalsIgnoreCase(student.getName()))
						findStudents.add(s);
				}
			} else if (!(student.getPatronymic() == "")) {
				for (Student s : students.values()) {
					if (s.getPatronymic().equalsIgnoreCase(student.getPatronymic()))
						findStudents.add(s);
				}
			} else if (!(student.getDate() == "1 january 1")) {
				for (Student s : students.values()) {
					if (s.getDate().toString().equals(student.getDate().toString()))
						findStudents.add(s);
				}
			} else if (!(student.getAddress() == "")) {
				for (Student s : students.values()) {
					if (s.getAddress().equalsIgnoreCase(student.getAddress()))
						findStudents.add(s);
				}
			} else if (!(student.getPhone() == "")) {
				for (Student s : students.values()) {
					if (s.getPhone().equalsIgnoreCase(student.getPhone()))
						findStudents.add(s);
				}
			} else if (!(student.getFaculty() == "")) {
				for (Student s : students.values()) {
					if (s.getFaculty().equalsIgnoreCase(student.getFaculty()))
						findStudents.add(s);
				}
			} else if (!(student.getCourse() == -1)) {
				for (Student s : students.values()) {
					if (s.getCourse() == student.getCourse())
						findStudents.add(s);
				}
			} else if (!(student.getGroup() == "")) {
				for (Student s : students.values()) {
					if (s.getGroup().equalsIgnoreCase(student.getGroup()))
						findStudents.add(s);
				}
			}
		} else {
			//logging
//			System.out.println("List is empty!!!"); //Replace by the log4j
		}
		return findStudents;
	}

	public boolean isCapacityReached() {
		if (capacity < maxCapacity) {
			return false;
		} else {
			return true;
		}
	}
}
