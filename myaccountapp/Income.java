package myaccountapp;

import java.io.Serializable;
import java.time.LocalDate;

public class Income extends Entries implements Serializable{

		public Income(String id, String category, double amount, String description, LocalDate date) {
			super(id, category, amount, description, date);
		}//constructor()
		
		public static String genID(int size) {
			return ("in"+size);
		}

		
}//class
