import java.io.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
public class Main {
	
	static ArrayList<Stock> bestStocksPerDate = new ArrayList<Stock>();
	
	public static void main(String[] args) throws FileNotFoundException, ParseException{
		// TODO Auto-generated method stub
		Scanner in = new Scanner (new File("in.txt"));
		in.useDelimiter("\\t|\\r|\\n");
		
		ArrayList<Stock> stocks = new ArrayList<>();
		while (in.hasNext()) {
			DateFormat df 	= DateFormat.getDateInstance(DateFormat.SHORT);
			String next = in.next();
			if (next.equals("")){
				continue;
			}
			
			String symbol 		= next;
			String name 		= in.next();
			Date dividendDate	= (Date) df.parse(in.next());
			double oldIncome	= Double.parseDouble(in.next());
			double costOfStock	= Double.parseDouble(in.next());
			
			stocks.add(new Stock(symbol, name, dividendDate, oldIncome, costOfStock));
			
		}
		in.close();
		Collections.sort(stocks, new ProfitComparator());
		Collections.sort(stocks, new DateComparator());
		
		//removing all stocks that are not the highest payoff for a given date
		bestStocksPerDate.add(stocks.get(0));
		for (int i = 1; i < stocks.size(); i++) {
			if (!(stocks.get(i).dividendDate.equals(stocks.get(i-1).dividendDate))) {
				bestStocksPerDate.add(stocks.get(i));
			}
		}
		
		double totalDividend = 0;
		for (Stock s: bestStocksPerDate) {
			totalDividend += s.profit;
			s.print();
		}
		
		
		
		DecimalFormat df = new DecimalFormat("#.##");
		int numberStocks = bestStocksPerDate.size();
		double totalInvestment = 10000*numberStocks;
		double percentageProfit = totalDividend/totalInvestment*100;
		
		System.out.println("Number of Stocks: " + numberStocks);
		System.out.println("Total Investment Needed: " + totalInvestment);
		System.out.println("Total Dividend Earned: " + df.format(totalDividend));
		System.out.println("Percentage Profit: " + df.format(percentageProfit));
		System.out.println("\n\n\n");
		System.out.println("Here is your first strategy: ");
		getStrategy(0);
		
	}
	
	//Enter 0 for start with the first input stock, 1, 2, 3, for subsequent days
	public static void getStrategy (int startDay) {
		int counter = startDay;
		Date date = bestStocksPerDate.get(counter).dividendDate;
		printAStock(bestStocksPerDate.get(counter));
		eightDaysAfter (date, date);
		counter++;
		System.out.println(date);
		
		while (counter < bestStocksPerDate.size()) {
			if ((bestStocksPerDate.get(counter).dividendDate).compareTo(date) > 0) {
				printAStock(bestStocksPerDate.get(counter));
				eightDaysAfter(date, bestStocksPerDate.get(counter).dividendDate);
				counter++;
				System.out.println(date);
			}
			counter++;
		}
	}
	
	
	public static void printAStock (Stock s) {
		s.print();
		System.out.println("\t\tBuy Date: " + s.buyDate);
		System.out.println("\t\tSell Date: " + s.sellDate);
		System.out.println("\t\tMoney in Da Bank: " + s.moneyBankDate);
	}
	
	public static void eightDaysAfter (Date toChange, Date toFindAfter) {
		toChange = getNextWeekday(toFindAfter);
		for (int i = 0; i < 7; i++) {
			toChange = getNextWeekday(toChange);
		}
	}
	
	
	@SuppressWarnings("deprecation")
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
	
	@SuppressWarnings("deprecation")
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
 * 	
 *	Stock purchase date: dividend date - 3
 *	Stock sell day: dividend date + 1
 *
 */
