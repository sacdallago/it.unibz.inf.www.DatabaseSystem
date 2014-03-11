package utilities;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Timer {
	private static Calendar calendar;
	
	public static String getTime(){
		calendar = GregorianCalendar.getInstance();
		return String.format("%tH:%tM:%tS", calendar,calendar,calendar);
	}
}
