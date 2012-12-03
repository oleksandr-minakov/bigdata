import java.awt.Graphics;
import java.util.Calendar;
import java.util.Date;



public class DateTaskApplet extends javax.swing.JApplet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8673683581939572600L;
	
	
	public String surname = "Minakov";
	public Date date = new Date();
	public Calendar dedline = Calendar.getInstance();
	
	public void init() {
		dedline.set(Calendar.YEAR, 2012);
		dedline.set(Calendar.MONTH, 11);
		dedline.set(Calendar.DAY_OF_MONTH, 3);
		setSize(500,300);
	}
	
	public void paint(Graphics app) {
		app.drawString("Разработчик " + surname +
				" получил задание " + date, 20, 20);
		app.drawString("Задание должно бьть выполнено к " +
				dedline.getTime(), 20, 50);
	}
	
}
