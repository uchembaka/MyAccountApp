package myaccountapp;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Entries  implements Serializable, Comparable<Entries>{
	private String id;
	private String category;
	private double amount;
	private String description;
	private LocalDate date;
	
	
	public Entries(String id, String category, double amount, String description, LocalDate date) {
		this.id = id;
		this.category = category;
		this.amount = amount;
		this.description = description;
		this.date = date;
	}//constructor()
	
	@Override
	public int compareTo(Entries entry) {
		return date.compareTo(entry.date);
	}//compare
	
	public String getID() {
		return id;
	}//getID()
	
	public String getCategory() {
		return category;
	}//getCategory()
	
	public double getAmount() {
		return amount;
	}//getAmount()
	
	public String getDescription() {
		return description;
	}//getDescription()
	
	public String getDate() {
		return date.toString();
	}//getDate()
	
	
	public void setCategory(String category) {
		this.category = category;
	}//setCategory()
	
	public void setAmount(double amount) {
		this.amount = amount;
	}//setAmount()
	
	public void setDescription(String description) {
		this.description = description;
	}//setDescription()
	
	public void setDate(LocalDate date) {
		this.date = date;
	}//setDate()

}//class
