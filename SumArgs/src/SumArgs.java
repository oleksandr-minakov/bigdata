
public class SumArgs {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int sum = 0;
		try {
			for (int i = 0; i < args.length; i++)
				sum += Integer.parseInt(args[i]);
			System.out.println("Summ " + sum);
		} catch(NumberFormatException err) {
			System.err.println("Ошибка " + err);
		}
		
	}

}
