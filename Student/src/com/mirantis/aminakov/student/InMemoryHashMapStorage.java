package com.mirantis.aminakov.student;

import java.io.OutputStream;
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
			return student.getId();
		} else {
			return -1;
		}
	}
	
	public void deleteStudent(int id) {
		if (!students.isEmpty()) {
			students.remove(id);
		} else {
			System.out.println("List is empty!!!"); //Replace by the log4g
		}
		
	}
	
	/*public static void findStudent(HashMap<String, Student> students, Scanner sc) {
		if (!students.isEmpty()) {
			all: while (true) {
				System.out.println("Find by..." + "\n" + "ID. Press 1." + "\n"
						+ "surname. Press 2." + "\n" + "name. Press 3." + "\n"
						+ "patronymic. Press 4." + "\n" + "date. Press 5."
						+ "\n" + "address. Press 6." + "\n" + "phone. Press 7."
						+ "\n" + "faculty. Press 8." + "\n"
						+ "course. Press 9." + "\n" + "group. Press 10." 
						+ "\n" + "EXIT. Press 11.");
				int answ = sc.nextInt();
				Student student;
				switch (answ) {
					case 1:
					System.out.println("Enter ID.");
					String id = sc.next();
//					if(id > 0 && id < (students.size() - 1)) {
						student = students.get(id);
						student.toString();
//					} else {
//						System.out.println("Wrong ID!!!");
//					}
					break;
				case 2:
					System.out.println("Enter surname.");
					String surname = sc.next();
					for (Student s : students.values()) {
						if (s.getSurname().equalsIgnoreCase(surname))
							System.out.println(s.toString());
					}
					break;
				case 3:
					System.out.println("Enter name.");
					String name = sc.next();
					for (Student s : students.values()) {
							if (s.getName().equalsIgnoreCase(name))
							System.out.println(s.toString());
					}
					break;
				case 4:
					System.out.println("Enter patronymic.");
					String patronymic = sc.next();
					for (Student s : students.values()) {
						if (s.getPatronymic().equalsIgnoreCase(patronymic))
							System.out.println(s.toString());
					}
					break;
				case 5:
					System.out.println("Enter date, (12 December 1990)");
					sc.nextLine();
					String find_date = sc.nextLine();
//					System.out.println("Read line = " + find_date);
					for (Student s : students.values()) {
						
						SimpleDateFormat sdf = new SimpleDateFormat(
								"dd MMMM yy", Locale.ROOT);
						Date d = null;
//						System.out.println("Befor block <try>");
						try {
							d = sdf.parse(find_date);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
							System.err.println("Error " + e);
							System.out.println("Error");
							break;
						}
//						System.out.println("After block <try>");
						String string_date = d.toString();
//						System.out.println("string_date = " + find_date);
						if (s.getDate().toString().equals(string_date))
							System.out.println(s.toString());
					}
					break;
				case 6:
					System.out.println("Enter address.");
					String address = sc.next();
					for (Student s : students.values()) {
						if (s.getAddress().equalsIgnoreCase(address))
							System.out.println(s.toString());
					}
					break;
				case 7:
					System.out.println("Enter phone.");
					String phone = sc.next();
					for (Student s : students.values()) {
						if (s.getPhone().equalsIgnoreCase(phone))
							System.out.println(s.toString());
					}
					break;
				case 8:
					System.out.println("Enter faculty.");
					String faculty = sc.next();
					for (Student s : students.values()) {
						if (s.getFaculty().equalsIgnoreCase(faculty))
							System.out.println(s.toString());
					}
					break;
				case 9:
					System.out.println("Enter course.");
					int course = sc.nextInt();
					for (Student s : students.values()) {
						if (s.getCourse() == course)
							System.out.println(s.toString());
					}
					break;
				case 10:
					System.out.println("Enter group.");
					String group = sc.next();
					for (Student s : students.values()) {
						if (s.getGroup().equalsIgnoreCase(group))
							System.out.println(s.toString());
					}
					break;
				case 11: 
					break all;
				default:
					break all;
				}
			}
		} else { 
			System.out.println("List is empty!!!");
		}
	}
	*/
	
	public void printList(OutputStream os) {
		
		for(Student s: students.values()) {
			System.out.println(s.toString());
		}
	}

	public List<Student> findByExample(Student student) {
		List<Student> find_students = new ArrayList<>();
		if (!students.isEmpty()) {
			if (!(student.getId() == -1)) {
				Student s = students.get(id);
				find_students.add(s);
			} else if (!(student.getSurname() == null)) {
				for (Student s : students.values()) {
					if (s.getSurname().equalsIgnoreCase(student.getSurname()))
						find_students.add(s);
				}
			} else if (!(student.getName() == null)) {
				for (Student s : students.values()) {
					if (s.getName().equalsIgnoreCase(student.getName()))
						find_students.add(s);
				}
			} else if (!(student.getPatronymic() == null)) {
				for (Student s : students.values()) {
					if (s.getPatronymic().equalsIgnoreCase(student.getPatronymic()))
						find_students.add(s);
				}
			} else if (!(student.getDate() == null)) {
				for (Student s : students.values()) {
					if (s.getDate().toString().equals(student.getDate().toString()))
						find_students.add(s);
				}
			} else if (!(student.getAddress() == null)) {
				for (Student s : students.values()) {
					if (s.getAddress().equalsIgnoreCase(student.getAddress()))
						find_students.add(s);
				}
			} else if (!(student.getPhone() == null)) {
				for (Student s : students.values()) {
					if (s.getPhone().equalsIgnoreCase(student.getPhone()))
						find_students.add(s);
				}
			} else if (!(student.getFaculty() == null)) {
				for (Student s : students.values()) {
					if (s.getFaculty().equalsIgnoreCase(student.getFaculty()))
						find_students.add(s);
				}
			} else if (!(student.getCourse() == -1)) {
				for (Student s : students.values()) {
					if (s.getCourse() == student.getCourse())
						find_students.add(s);
				}
			} else if (!(student.getGroup() == null)) {
				for (Student s : students.values()) {
					if (s.getGroup().equalsIgnoreCase(student.getGroup()))
						find_students.add(s);
				}
			}
		} else {
			System.out.println("List is empty!!!"); //Replace by the log4g
		}
		return find_students;
	}

	public boolean isCapacityReached() {
		if (capacity < 100) {
			return false;
		} else {
			return true;
		}
	}
}
