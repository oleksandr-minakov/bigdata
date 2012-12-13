package com.mirantis.aminakov.student;

import java.lang.reflect.*;

public class UniversalSetter {
	public static int setFields(Object obj/*, String[] fields, String[] values*/) {
		
		Method[] methods = obj.getClass().getMethods();
		for (Method m : methods) {
			if (m.getName().contains("set")) {
				System.out.println(m.getName());
			}
		}
		return 0;
	}
}
