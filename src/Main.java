import java.io.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
public class Main {

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
		Collections.sort(stocks, new ProfitComparator());
		Collections.sort(stocks, new DateComparator());
		
		//removing all stocks that are not the highest payoff for a given date
		ArrayList<Stock> bestStocksPerDate = new ArrayList<>();
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
		
		
		in.close();
	}

}
