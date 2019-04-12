package myaccountapp;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

public class AccountRegister implements Serializable{
	ArrayList<Entries> entriesList;
	
	public static AccountRegister entriesCollection = null;
	
	private AccountRegister() {
		entriesList = new ArrayList<Entries>();
	}//constructor()
	
	public static AccountRegister getAccountRegister() {// method to create new AccountCollection 
		if(entriesCollection == null) {
			return new AccountRegister();
		}
		return entriesCollection;
	}//getBudgetCollection
	
	public int getSize() {
		return entriesList.size();
	}//getSize();
	
	public void add(Entries obj) {
		entriesList.add(obj);
		Collections.sort(entriesList);
	}//add()
	
	public void delete(String id) {
		for (int i = 0; i < entriesList.size(); i++) {
			if (entriesList.get(i).getID().equals(id)) {
				entriesList.remove(i);
				break;
			}
		}
	}//delete()
	
	public void singleEntryView(String id) {
		for (int i = 0; i < entriesList.size(); i++) {
			if(entriesList.get(i).getID().equals(id)) {
				Entries item = entriesList.get(i);
				if(item instanceof Expense) {
					tabHead("Expense");
					row(item);

				}else {
					tabHead("Income");
					row(item);
				}
			}
		}
	}//singleEntryView()
	
//	public String getEntry(int index) {
//		return entriesList.get(index).getID();
//	}//getEntry()
	
	public Entries getEntry(int index) {
		return entriesList.get(index);
	}//getEntry()
	
	private void tabHead(String type) {
		lines("2nf");
		//System.out.print("|");
		System.out.print(String.format("%-15s", "|Date"));
		System.out.print(String.format("%-15s", "|ID"));
		System.out.print(String.format("%-30s", "|Category"));
		System.out.print(String.format("%-30s", "|Description"));
		System.out.print(String.format("%-30s", "|"+type));
		System.out.print("|");
		System.out.println();
		lines("2nf");
	}//tabHead()

	
	public void viewAll() {
		lines("2f");
		//System.out.print("|");
		System.out.print(String.format("%-15s", "|Date"));
		System.out.print(String.format("%-15s", "|ID"));
		System.out.print(String.format("%-30s", "|Category"));
		System.out.print(String.format("%-30s", "|Description"));
		System.out.print(String.format("%-30s", "|Income"));
		System.out.print(String.format("%-30s", "|Expense"));
		System.out.print("|");
		System.out.println();
		lines("2f");
		for (Entries entries : entriesList) {
			if (entries instanceof Income) {
				System.out.print(String.format("%-15s", "|"+entries.getDate()));
				System.out.print(String.format("%-15s", "|"+entries.getID()));
				System.out.print(String.format("%-30s", "|"+entries.getCategory()));
				if(entries.getDescription().length() > 29) {
					System.out.print(String.format("%-30s", "|"+entries.getDescription().substring(0,28)));
				}else {
					System.out.print(String.format("%-30s", "|"+entries.getDescription()));
				}
				System.out.print(String.format("%-30s", "|"+entries.getAmount()));
				System.out.print(String.format("%-30s", "|")+"|");
				System.out.println();
				lines("1f");
			}else {
				System.out.print(String.format("%-15s", "|"+entries.getDate()));
				System.out.print(String.format("%-15s", "|"+entries.getID()));
				System.out.print(String.format("%-30s", "|"+entries.getCategory()));
				if(entries.getDescription().length() > 29) {
					System.out.print(String.format("%-30s", "|"+entries.getDescription().substring(0,28)));
				}else {
					System.out.print(String.format("%-30s", "|"+entries.getDescription()));
				}
				System.out.print(String.format("%-30s", "|"));
				System.out.print(String.format("%-30s", "|"+entries.getAmount())+"|");
				System.out.println();
				lines("1f");
			}
		}//for
		System.out.print(String.format("%-90s", "|Total"));
		System.out.print(String.format("%-30s", "|"+getTotalIncome()));
		System.out.print(String.format("%-30s", "|"+getTotalExpense()));
		System.out.print("|");
		System.out.println();
		lines("1f");
		
	}//viewAll()
	
	private double getTotalIncome() {
		double sum = 0;
		for(Entries entries: entriesList) {
			if(entries instanceof Income) {
				sum = sum + entries.getAmount();
			}
		}
		return sum;
	}//getTotalIncome
	
	private double getTotalExpense() {
		double sum = 0;
		for(Entries entries: entriesList) {
			if(entries instanceof Expense) {
				sum = sum + entries.getAmount();
			}
		}
		return sum;
	}//getTotalExpense
	
	public void viewIncome() {
		tabHead("Income");
		for(Entries entries: entriesList) {
			if(entries instanceof Income) {
				row(entries);
			}
		}//for
		totalRow("Income");
	}//viewIncome()
	
	public void viewExpense() {
		tabHead("Expense");
		for(Entries entries: entriesList) {
			if(entries instanceof Expense) {
				row(entries);
			}
		}//for
		totalRow("Expense");
	}//viewExpense()
	
	private void row(Entries entries) {
		System.out.print(String.format("%-15s", "|"+entries.getDate()));
		System.out.print(String.format("%-15s", "|"+entries.getID()));
		System.out.print(String.format("%-30s", "|"+entries.getCategory()));
		if(entries.getDescription().length() > 29) {
			System.out.print(String.format("%-30s", "|"+entries.getDescription().substring(0,28)));
		}else {
			System.out.print(String.format("%-30s", "|"+entries.getDescription()));
		}
		System.out.print(String.format("%-30s", "|"+entries.getAmount())+"|");
		System.out.println();
		lines("1nf");
	}//row()
	
	private void totalRow(String type) {

		System.out.print(String.format("%-90s", "|Total"));
		
		if(type.equals("Income"))System.out.print(String.format("%-30s", "|"+getTotalIncome()));
		else System.out.print(String.format("%-30s", "|"+getTotalExpense()));
		
		System.out.print("|");
		System.out.println();
		lines("1nf");
	}//totalRow()
	
	
	private void lines(String type) {
		if(type.equals("1f")) {
			System.out.println("-------------------------------------------------------------------"
					+ "------------------------------------------------------------------------------------");
		}else if(type.equals("2f")){
			System.out.println("-------------------------------------------------------------------"
					+ "------------------------------------------------------------------------------------");
			System.out.println("-------------------------------------------------------------------"
					+ "------------------------------------------------------------------------------------");
		}else if(type.equals("1nf")) {
			System.out.println("-------------------------------------------------------------------"
					+ "------------------------------------------------------");
		}else {
			System.out.println("-------------------------------------------------------------------"
					+ "------------------------------------------------------");
			System.out.println("-------------------------------------------------------------------"
					+ "------------------------------------------------------");
		}
	}//lines()
	
	private double getCatSum(String category) {
		double sum = 0;
		for(Entries entry : entriesList) {
			if (entry.getCategory().equals(category)) sum = sum + entry.getAmount();
		}
		return sum;
	}//getCatSum()
	
	private void printBar(double sum, String type) {
		int n;
		double percent  = 0;
		if(type.equals("Income")) {
			n = (int) ((sum/getTotalIncome())*100);
			if (sum <= 0) percent = 0;
			else percent = (sum/getTotalIncome())*100;
		} else {
			n = (int) ((sum/getTotalExpense())*100);
			if (sum <= 0) percent = 0;
			else percent = (sum/getTotalExpense())*100;
		}
		
		for (int i = 1; i <= n; i++) {
			System.out.print("=");
		}
		
		System.out.println(" | "+percent+"%");
		
		System.out.println();
	}//printBar()
	
	public void viewStat() {
		
		lines("1f");
		System.out.println(String.format("%95s", "Income and Expense Stats by Categories"));
		lines("1f");
		System.out.println();
		lines("1f");
		System.out.println(String.format("%85s", "Income Statistics"));
		System.out.print(String.format("%30s", "Salary | "));
		printBar(getCatSum("Salary"), "Income");
		System.out.print(String.format("%30s", "investment Returns | "));
		printBar(getCatSum("Investment Returns"), "Income");
		System.out.print(String.format("%30s", "Gifts | "));
		printBar(getCatSum("Gifts"), "Income");
		System.out.print(String.format("%30s", "Friends/Families | "));
		printBar(getCatSum("Friends/Families"), "Income");
		System.out.print(String.format("%30s", "Others | "));
		printBar(getCatSum("Others"), "Income");
		System.out.println();
		lines("1f");
		System.out.println(String.format("%85s", "Expense Statistics"));
		System.out.print(String.format("%30s", "Groceries | "));
		printBar(getCatSum("Groceries"), "Expense");
		System.out.print(String.format("%30s", "Entertainment | "));
		printBar(getCatSum("Entertainment"), "Expense");
		System.out.print(String.format("%30s", "Transportation | "));
		printBar(getCatSum("Transportation"), "Expense");
		System.out.print(String.format("%30s", "Food | "));
		printBar(getCatSum("Food"), "Expense");
		System.out.print(String.format("%30s", "Rent and/or House Bills | "));
		printBar(getCatSum("Rent and/or House Bills"), "Expense");
		System.out.print(String.format("%30s", "Business | "));
		printBar(getCatSum("Business"), "Expense");
		System.out.print(String.format("%30s", "Wardrobe | "));
		printBar(getCatSum("Wardrobe"), "Expense");
		System.out.print(String.format("%30s", "Miscellaneous | "));
		printBar(getCatSum("Misc"), "Expense");
		System.out.println();
		lines("1f");
	}//viewStat()
	
	public void clearRegister() {
		entriesList.clear();
	}
	
	
}//class
