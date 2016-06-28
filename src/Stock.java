import java.util.*;
import java.math.RoundingMode;
import java.text.*;
import java.time.*;

public class Stock {
	public String symbol;
	public String name;
	public LocalDate dividendDate;
	public double pastIncome;
	public double costPerStock;
	
	public LocalDate buyDate;
	public LocalDate sellDate;
	public LocalDate moneyBankDate;
	
	public int unitsPerTenK;
	public double profit;
	
	
	public Stock (String s, String n, LocalDate date, double p, double c) {
		symbol = s;
		name = n;
		dividendDate = date;
		pastIncome = p;
		costPerStock = c;
		
		runCalc();

	}
	
	private void runCalc () {
		unitsPerTenK = (int) (10000/costPerStock);
		profit = pastIncome*unitsPerTenK;
		
		//buy date is 3 days previous to dividend date
		buyDate = dividendDate.minusDays(3);
		
		
		//sell date is 1 day after dividend date
		sellDate = getNextWeekday(dividendDate);
		
		//money date is 3 days after sell date 
		moneyBankDate = nDaysAfter(4, dividendDate);
		
	}
	
	public void print() {
		DecimalFormat df = new DecimalFormat("#.##");
		String profitString = df.format(profit);
		System.out.println(symbol + "\t" + name + "\t" + dividendDate + "\t" + pastIncome + "\t" + costPerStock + "\t" + unitsPerTenK + "\t" + profitString + "\t" + buyDate + "\t" + sellDate);
	}
	
	
	public static Date nDaysAfter (int n, Date toFindAfter) {
		Date toChange = new Date (toFindAfter.getDate());
		for (int i = 0; i < n; i++) {
			toChange = getNextWeekday(toChange);
		}
		return toChange;
	}
	
	public static Date nDaysBefore (int n, Date toFindBefore) {
		Date toChange = new Date(toFindBefore.getDate());
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

class DateComparator implements Comparator<Stock> {
	public int compare (Stock a, Stock b) {
		return a.dividendDate.compareTo(b.dividendDate);
	}
}

class ProfitComparator implements Comparator<Stock> {
	public int compare (Stock a, Stock b) {
		return -((Double)a.profit).compareTo(b.profit);
	}
}
