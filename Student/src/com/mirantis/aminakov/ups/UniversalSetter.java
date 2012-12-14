package com.mirantis.aminakov.ups;

import java.lang.reflect.*;
import java.util.HashMap;

import com.mirantis.aminakov.annotation.InputIgnore;


public class UniversalSetter {
	
	private Object o;
	
	public Object getO() {
		return o;
	}

	private void setO(Object o) {
		this.o = o;
	}
	
	public UniversalSetter(Object obj) {
		setO(obj);
	}
	
	public Object setFields(HashMap<String, String> map) {
		Class<?> c = this.o.getClass();
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields) {
			InputIgnore i = field.getAnnotation(InputIgnore.class);
			if (i.value() == false) {
				if (!(map.get(field.getName()) == null)) {
					field.setAccessible(true);
					String type = field.getType().getSimpleName();
					type.toLowerCase();
					switch(type) {
					case "int":
						int value = Integer.parseInt(map.get(field.getName()));
					}
					try {
						field.set(this.o, map.get(field.getName()));
					} catch (IllegalArgumentException | IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
//				System.out.println(field.getName() + " IGNOGE FOR INPUT");
			} else {
//				System.out.println(field.getName() + " INPUT THIS FIELD");	
			}
		}
		return o;
	}
}

