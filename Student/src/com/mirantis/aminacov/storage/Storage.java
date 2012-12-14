package com.mirantis.aminacov.storage;


import java.io.*;
import java.util.List;
import com.mirantis.aminakov.exceptions.*;
import com.mirantis.aminakov.student.Student;

public interface Storage {
	int addStudent (Student student) throws AddException;
	int deleteStudent(String id) throws DeleteException;
	List<Student> findByExample(Student student);
	boolean isCapacityReached();
	void printList(OutputStream stream);
}