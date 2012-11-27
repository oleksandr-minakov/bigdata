
public class InverseOutArgs {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for(int j = args.length - 1; j >= 0; j--)
			System.out.println("Аргумент № " + j + " -> " + args[j]);

	}

}
