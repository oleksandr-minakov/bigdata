import java.io.*;

public class CheckPassword {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String passphrase = "qwerty";
		InputStreamReader is = new InputStreamReader(System.in);
		BufferedReader bis = new BufferedReader(is);
		try {
			System.out.println("Введите пароль и нажмите <Enter>");
			String pass = bis.readLine();
			if (pass.equals(passphrase)) {
				System.out.println("Пароль верный");
			} else {
				System.out.println("Пароль неверный");
			}
		} catch(IOException err) {
			System.err.println("Ошибка " + err);
		}
		
	}

}
