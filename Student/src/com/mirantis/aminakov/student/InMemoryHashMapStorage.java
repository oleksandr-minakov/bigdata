package com.mirantis.aminakov.student;

import java.io.*;
import java.util.*;
import org.apache.log4j.*;


public class InMemoryHashMapStorage implements Storage {
	private Map<String, Student> students = new HashMap<>();
	private int maxCapacity = 100;
	private int id = 0;
	private int capacity = 0;
	public static final Logger log = Logger.getRootLogger();
	
	public int addStudent (Student student) throws AddException {
		if (capacity < maxCapacity) {
			student.setId(id);
			students.put(String.valueOf(student.getId()), student);
			capacity++;
			id++;
			log.info("Add student ---> " + student.toString());
			return student.getId();
		} else {
			AddException addExc = new AddException();
			log.error("Add error ", addExc );
			throw addExc;
		}
	}
	
	public int deleteStudent(String idDel) throws DeleteException {
		if (!students.isEmpty()) {
			if(students.remove(idDel) == null) {
				DeleteException delExc = new NoStudent();
				log.error("Delete error. No student ---> ", delExc);
				throw delExc;
			} else {
				log.info("Student " + idDel + " delete.");
				return 0;
			}	
		} else {
			DeleteException delExc = new ListIsEmpty();
			log.error("Delete error. List is empty --->", delExc);
			throw delExc;
	
		}
	}
	
	public void printList(OutputStream os) {
		Writer osw = new OutputStreamWriter(os);
		for(Student s: students.values()) {
			try {
				osw.write(s.toString() + "\n");
				osw.flush();
			} catch (IOException e) {
				log.error("IOException --->", e);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		log.info("Printed list of students.");
	}

	public List<Student> findByExample(Student student) {
		List<Student> findStudents = new ArrayList<>();
		if (!students.isEmpty()) {
			if (!(student.getId() == -1)) {
				for (Student s : students.values()) {
					if (s.getId() == student.getId())
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
			log.info("Find..... List is empty.");
		}
		log.info("Found students.");
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
