import java.io.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javax.swing.*;

public class Main {
	
	static ArrayList<Stock> bestStocksPerDate = new ArrayList<Stock>();
	static PrintWriter writer;
	public static void main(String[] args) throws FileNotFoundException, ParseException, IOException{
		// TODO Auto-generated method stub
		
		//String fileLocation = JOptionPane.showInputDialog("Please input the file location: ");
		writer = new PrintWriter(System.currentTimeMillis() + "InvestmentStrategy.txt", "UTF-8");
		Scanner in = new Scanner (new File("in.txt"));
		
		in.useDelimiter("\\t|\\r|\\n");
		//Read in the stocks from a tab-delimited file
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
		
		//Sort the list first with profit then with date
		//so that the first stock of each date is the one with the highest dividend
		Collections.sort(stocks, new ProfitComparator());
		Collections.sort(stocks, new DateComparator());
		
		//removing all stocks that are not the highest payoff for a given date
		//and transferring the good ones to a separate arrayList
		bestStocksPerDate.add(stocks.get(0));
		for (int i = 1; i < stocks.size(); i++) {
			if (!(stocks.get(i).dividendDate.equals(stocks.get(i-1).dividendDate))) {
				bestStocksPerDate.add(stocks.get(i));
			}
		}
		/*
		//The following code is if you want to invest 10k in each stock
		double totalDividend = 0;
		for (Stock s: bestStocksPerDate) {
			totalDividend += s.profit;
			s.print();
		}
		
		
		
		DecimalFormat df = new DecimalFormat("#.##");
		int numberStocks = bestStocksPerDate.size();
		double totalInvestment = 10000*numberStocks;
		double percentageProfit = totalDividend/totalInvestment*100;
		
		System.out.println("Investing 10k in each:");
		System.out.println("\tNumber of Stocks: " + numberStocks);
		System.out.println("\tTotal Investment Needed: " + totalInvestment);
		System.out.println("\tTotal Dividend Earned: " + df.format(totalDividend));
		System.out.println("\tPercentage Profit: " + df.format(percentageProfit));
		System.out.println("\n\n\n");
		*/
		
		// *** THE FOLLOWING CODE USES CONSOLE OUTPUT****
		//The following strategies are if you want to only invest $10k
		//but want the highest dividends 
		double maxProfit = 0;
		int bestStrategy = 0;
		for (int i = 0; i < bestStocksPerDate.size(); i++) {
			double tempProfit;
			System.out.println("Strategy #" + (i+1));
			tempProfit = getStrategy(i);
			if (tempProfit > maxProfit) {
				maxProfit = tempProfit;
				bestStrategy = i+1;
			}
		}
		DecimalFormat df = new DecimalFormat("#.##");
		System.out.println("Suggested Investment Strategy: #" + bestStrategy);
		System.out.println("Maximum Possible Percentage Profit: " + df.format(maxProfit) + "%");
		
		/*
		double maxProfit = 0;
		int bestStrategy = 0;
		for (int i = 0; i < bestStocksPerDate.size(); i++) {
			double tempProfit;
			System.out.println("Strategy #" + (i+1));
			tempProfit = getStrategy(i);
			if (tempProfit > maxProfit) {
				maxProfit = tempProfit;
				bestStrategy = i+1;
			}
		}
		DecimalFormat df = new DecimalFormat("#.##");
		writer.println("Suggested Investment Strategy: #" + bestStrategy);
		writer.println("Maximum Possible Percentage Profit: " + df.format(maxProfit) + "%");
		*/
		writer.close();
	}
	
	//Enter 0 for start with the first input stock, 1, 2, 3, for subsequent days
	public static double getStrategy (int startDay) {
		double totalProfit = 0;
		int counter = startDay;
		
		
		Stock temp = bestStocksPerDate.get(counter);
		Date date = (Date) temp.dividendDate;
		totalProfit += temp.profit;
		printAStock(temp);
		
		date = temp.moneyBankDate;
		counter++;
		
		while (counter < bestStocksPerDate.size()) {
			temp = bestStocksPerDate.get(counter);
			
			if ((temp.buyDate).compareTo(date) > 0) {
				
				printAStock(temp);
				totalProfit += temp.profit;
				
				date = temp.moneyBankDate;
			}
			counter++;
		}
		double percentageProfit = totalProfit/10000 *100;
		DecimalFormat df = new DecimalFormat("#.##");
		
		System.out.println("Total Investment: $10,000");
		System.out.println("Total Dividend Earned: " + df.format(totalProfit));
		System.out.println("Percentage Profit: " + df.format(percentageProfit) + "%");
		System.out.println("\n");
		
		/*writer.println("Total Investment: $10,000");
		writer.println("Total Dividend Earned: " + df.format(totalProfit));
		writer.println("Percentage Profit: " + df.format(percentageProfit) + "%");
		writer.println("\n");*/
		
		return percentageProfit;
	}
	
	
	public static void printAStock (Stock s) {
		s.print();
		//System.out.println(s.symbol + "\t" + s.name + "\t" + s.dividendDate + "\t" + s.pastIncome + "\t" + s.costPerStock + "\t" + s.unitsPerTenK + "\t" + s.profit);
		writer.println("\t\tBuy Date: " + s.buyDate);
		writer.println("\t\tSell Date: " + s.sellDate);
		writer.println("\t\tMoney in Da Bank: " + s.moneyBankDate);
		writer.println("\n");
	}

}