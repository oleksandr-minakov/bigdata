import java.util.*;



public class DateTask {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String surname = "Minakov";
		Date date = new Date();
		System.out.println("Разработчик " + surname +
				" получил задание " + date);
		Calendar dedline = Calendar.getInstance();
		dedline.set(Calendar.YEAR, 2012);
		dedline.set(Calendar.MONTH, 11);
		dedline.set(Calendar.DAY_OF_MONTH, 3);
		System.out.println("Задание должно бьть выполнено к " +
				dedline.getTime());

	}

}
