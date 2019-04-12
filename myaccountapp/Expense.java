package myaccountapp;

import java.io.Serializable;
import java.time.LocalDate;

public class Expense extends Entries implements Serializable{
	
	
	public Expense(String id, String category, double amount, String description, LocalDate date) {
		super(id, category, amount, description, date);
	}//constructor()
	
	public static String genID(int size) {
		return ("ex"+size);
	}
}
