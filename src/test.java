import java.util.*;
import java.text.*;
import java.text.*;
import java.time.*;

public class test {
	public static void main (String args[]) throws ParseException {
		Scanner in = new Scanner (System.in);
		DateFormat df 	= DateFormat.getDateInstance(DateFormat.SHORT);
		
		LocalDate date1;
		LocalDate date2 = (LocalDate) LocalDate.parse(in.next());
		
		//System.out.println(date1.equals(date2));
		//System.out.println(date1.compareTo(date2));
		//date1.setDate(date1.getDate() - 1);
		//System.out.println(getPreviousWeekday(date1));
		//System.out.println(getNextWeekday(getNextWeekday(date1)));
		date1 = date2.plusDays(3);
		System.out.println(date1);
		System.out.println(date2);

	}
	public static Date nDaysAfter (int n, Date toFindAfter) {
		Date toChange = new Date(toFindAfter.getDate());
		for (int i = 0; i < n; i++) {
			toChange = getNextWeekday(toChange);
		}
		return toChange;
	}
	
	public static Date nDaysBefore (int n, Date toFindBefore) {
		Date toChange = toFindBefore;
		for (int i = 0; i < n; i++) {
			toChange = getPreviousWeekday(toChange);
		}
		return toChange;
	}


	public static Date getPreviousWeekday (Date d) {
		int day = d.getDay();
		if (day == 1) {
			d.setDate(d.getDate() - 3);
		}
		else {
			d.setDate(d.getDate() - 1);
		}
		
		return d;
	}
	
	public static Date getNextWeekday (Date d) {
		int day = d.getDay();
		if (day == 5) {
			d.setDate(d.getDate() + 3);
		}
		else {
			d.setDate(d.getDate() + 1);
		}
		
		return d;
	}
}

/*
 * 06/31/2016
 * 06/30/2016
 */
