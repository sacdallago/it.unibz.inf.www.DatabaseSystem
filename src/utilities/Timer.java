package utilities;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Timer {
	private static Calendar calendar;
	
	public static String getTime(){
		calendar = Calendar.getInstance();
		return String.format("%tH:%tM:%tS", calendar,calendar,calendar);
	}
}
