package com.mirantis.aminakov.student;

import java.io.OutputStream;
import java.util.List;

public interface Storage {
	int addStudent(Student student);
	void deleteStudent(String id);
	List<Student> findByExample(Student student);
	boolean isCapacityReached();
	void printList(OutputStream stream);
}