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
			if (i.value() == true) {
//				System.out.println(field.getName() + " IGNOGE FOR INPUT");
			} else {
//				System.out.println(field.getName() + " INPUT THIS FIELD");
				if (!(map.get(field.getName()) == null)) {
					field.setAccessible(true);
					String type = field.getType().getSimpleName();
					try {
						switch(type.toLowerCase()) {
						case "int":
							int intVal = Integer.parseInt(map.get(field.getName()));
							field.setInt(this.o, intVal);
							break;
						case "string":
							String strVal = map.get(field.getName());
							field.set(this.o, strVal);
							break;
						case "boolean":
							boolean boolVal = Boolean.parseBoolean(map.get(field.getName()));
							field.setBoolean(this.o, boolVal);
							break;
						case "byte":
							byte byteVal = Byte.parseByte(map.get(field.getName()));
							field.setByte(this.o, byteVal);
							break;
						case "char":
							char charVal = map.get(field.getName()).charAt(0);
							field.setChar(this.o, charVal);
							break;
						case "short":
							short shortVal = Short.parseShort(map.get(field.getName()));
							field.setShort(this.o, shortVal);
							break;
						case "long":
							long longVal = Long.parseLong(map.get(field.getName()));
							field.setLong(this.o, longVal);
							break;
						case "double":
							double doubleVal = Double.parseDouble(map.get(field.getName()));
							field.setDouble(this.o, doubleVal);
							break;
						case "float":
							float floatVal = Float.parseFloat(map.get(field.getName()));
							field.setFloat(this.o, floatVal);
							break;
						}
					} catch ( IllegalArgumentException| IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return o;
	}
}

