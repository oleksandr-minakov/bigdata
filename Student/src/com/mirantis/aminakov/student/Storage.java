package com.mirantis.aminakov.student;

import java.io.*;
import java.util.List;

public interface Storage {
	int addStudent (Student student) throws AddException;
	int deleteStudent(String id) throws DeleteException;
	List<Student> findByExample(Student student);
	boolean isCapacityReached();
	void printList(OutputStream stream);
}