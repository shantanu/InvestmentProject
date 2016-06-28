import java.util.*;
import java.text.*;

public class test {
	public static void main (String args[]) throws ParseException {
		Scanner in = new Scanner (System.in);
		DateFormat df 	= DateFormat.getDateInstance(DateFormat.SHORT);
		
		Date date1 = (Date) df.parse(in.next());
		Date date2 = (Date) df.parse(in.next());
		
		System.out.println(date1.equals(date2));
		System.out.println(date1.compareTo(date2));
		
	}
}

/*
 * 06/31/2016
 * 06/30/2016
 */
