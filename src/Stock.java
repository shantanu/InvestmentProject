import java.util.*;
import java.math.RoundingMode;
import java.text.*;
public class Stock {
	public String symbol;
	public String name;
	public Date dividendDate;
	public double pastIncome;
	public double costPerStock;
	
	public int unitsPerTenK;
	public double profit;
	
	
	public Stock (String s, String n, Date date, double p, double c) {
		symbol = s;
		name = n;
		dividendDate = date;
		pastIncome = p;
		costPerStock = c;
		
		unitsPerTenK = (int) (10000/costPerStock);
		profit = pastIncome*unitsPerTenK;
		
		
	}
	
	public void print() {
		DecimalFormat df = new DecimalFormat("#.##");
		String profitString = df.format(profit);
		System.out.println(symbol + "\t" + name + "\t" + dividendDate + "\t" + pastIncome + "\t" + costPerStock + "\t" + unitsPerTenK + "\t" + profitString);
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
