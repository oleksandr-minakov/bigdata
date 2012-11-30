import java.util.*;

public class Student {
	
	private int id;
	private String name;
	private String surname;
	private String patronymic;
	private Calendar date;
	private String address;
	private long phone;
	private String faculty;
	private byte course;
	private String group;
	
	public void setStudentId(int ido) {
		id = ido;
	}	
	public void setStudentName(String name) {
		this.name = name;
	}	
	public void setStudentSurname(String surname) {
		this.surname = surname;
	}
	public void setStudentPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}
	public void setStudentDate(Calendar date) {
		this.date = date;
	}
	public void setStudentAddress(String address) {
		this.address = address;
	}
	public void setStudentPhone(long phone) {
		this.phone = phone;		
	}
	public void setStudentFaculty(String faculty) {
		this.faculty = faculty;
	}
	public void setStudentCourse(byte course) {
		this.course = course;
	}
	public void setStudentGroup(String group) {
		this.group = group;
	}
	
	public int getStudentId() {
		return id;
	}
	public String getStudentName() {
		return name;
	}
	public String getStudentSurname() {
		return surname;
	}
	public String getStudentPatronymic() {
		return patronymic;
	}
	public Calendar getStudentDate() {
		return date;
	}
	public String getStudentAddress() {
		return address;
	}
	public long getStudentPhone() {
		return phone;
	}
	public String getStudentFaculty() {
		return faculty;
	}
	public byte getStudentCourse() {
		return course;
	}
	public String getStudentGroup() {
		return group;
	}
	public String toString() {
		return "" + surname + "" name + "" patronymic + "" + 
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Student student;
		String answ1 = "ДА";
		String answ2 = "НЕТ";
		List<Student> students = new ArrayList<Student>();
		System.out.println("Хотите ввести данные о студенте? Наберите в командной строке" +
				"ДА или НЕТ.");
		Scanner sc = new Scanner(System.in);
		String answ = sc.next();
		while(true) {
			if(answ.equals(answ1)) {
				student = new Student();
				System.out.println("Введите имя");
				String n = sc.next();
				student.setStudentName(n);
				System.out.println("Введите фамилию");
				String sn = sc.next();
				student.setStudentSurname(sn);
				System.out.println("Введите отчество");
				String p = sc.next();
				student.setStudentPatronymic(p);
				System.out.println("Введите дату рождения, нажимая <ENTER> после каждого числа" +
						"год, месяц, число");
				Calendar date = Calendar.getInstance();
				date.set(sc.nextInt(), sc.nextInt(), sc.nextInt());
				student.setStudentDate(date);
				System.out.println("Введите адрес");
				String addr = sc.next();
				student.setStudentAddress(addr);
				System.out.println("Введите телефон (только цифры)");
				long phone = sc.nextLong();
				student.setStudentPhone(phone);
				System.out.println("Введите факультет");
				student.faculty = sc.next();
				System.out.println("Введите курс (1-6)");
				student.course = sc.nextByte();
				System.out.println("Введите группу");
				student.group = sc.next();
				students.add(student);
				for(Student s: students) {
					System.out.println()
				}
				
			}
			
		}
		
	}

}
