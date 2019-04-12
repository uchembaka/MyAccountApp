package myaccountapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Month;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		AccountRegister myAccountRegister = AccountRegister.getAccountRegister();
		readDat(myAccountRegister);
		System.out.println("Welcome!\nEnter the number of the corresponding menu item\n");
		System.out.println("Always enter the number of the corresponding option\n");
		//displayMenu();
		selectMenu(myAccountRegister);
	}//main()
	
	
	public static void readDat(AccountRegister register) {
		ObjectInputStream datFileIn;
		try {
			datFileIn = new ObjectInputStream(new FileInputStream("save.dat"));
			Entries anEntry = (Entries) datFileIn.readObject();
			register.add(anEntry);
			
			while(anEntry != null) {
				anEntry = (Entries) datFileIn.readObject();
				register.add(anEntry);
			}//while
			
			datFileIn.close();
		} catch (IOException e) {
			if(e instanceof FileNotFoundException) {
				System.out.println("save.dat file not found");
			}
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}//readDat()
	
	public static void readTxt(AccountRegister register) throws IOException {
		File file = new File("save.txt");
		Scanner scn = new Scanner(file);
		
		while(scn.hasNextLine()) {
			String line = scn.nextLine();
			String[] tokens = line.split("~");
			System.out.println(tokens.length);
			String[] date = tokens[4].split("-");
			if(tokens[5].equals("Income")) {
				register.add(new Income(tokens[0], tokens[1], Double.valueOf(tokens[2]), tokens[3], LocalDate.of(Integer.valueOf(date[0]), Month.of(Integer.valueOf(date[1])), Integer.valueOf(date[2]))));
			}else {
				register.add(new Expense(tokens[0], tokens[1], Double.valueOf(tokens[2]), tokens[3], LocalDate.of(Integer.valueOf(date[0]), Month.of(Integer.valueOf(date[1])), Integer.valueOf(date[2]))));
			}

		}
		//file.deleteOnExit();
	}//readTxt
	
	public static void saveTxt(AccountRegister register) {
		PrintWriter txtFileOut;
		try {
			txtFileOut = new PrintWriter(new FileWriter("save.txt"));
			for(int i = 0; i < register.getSize(); i++) {
				if(register.getEntry(i) instanceof Income) {
					txtFileOut.println(register.getEntry(i).getID()+"~"
							+register.getEntry(i).getCategory()+"~"
							+register.getEntry(i).getAmount()+"~"
							+register.getEntry(i).getDescription()+"~"
							+register.getEntry(i).getDate()+"~Income");
				}else {
					txtFileOut.println(register.getEntry(i).getID()+"~"
							+register.getEntry(i).getCategory()+"~"
							+register.getEntry(i).getAmount()+"~"
							+register.getEntry(i).getDescription()+"~"
							+register.getEntry(i).getDate()+"~Expense");
				}

			}//for
			txtFileOut.close();
		}catch(IOException e) {
			System.out.println("IOException: "+e.getMessage());
		}//catch
	}//savetxt()
	
	
	public static void saveDat(AccountRegister register) {
		ObjectOutputStream datFileOut;
		try {
			datFileOut = new ObjectOutputStream(new FileOutputStream("save.dat"));
			for (int i = 0; i < register.getSize(); i++) {
				datFileOut.writeObject(register.getEntry(i));
			}
			datFileOut.close();
		}catch(IOException e) {
			System.out.println("IO Exception: "+e.getMessage());
		}
	}//saveDat()
	
	public static void getCSV(AccountRegister register) {
		PrintWriter csvFileOut;
		try {
			csvFileOut = new PrintWriter("save.csv");
			csvFileOut.println("Date,ID,Category,Description,Entry-Type,Amount");
			for (int i = 0; i < register.getSize(); i++) {
				if(register.getEntry(i) instanceof Income) {
					csvFileOut.println(register.getEntry(i).getDate()+","+register.getEntry(i).getID()+","
							+register.getEntry(i).getCategory()+","+register.getEntry(i).getDescription()+",Income,"+register.getEntry(i).getAmount());
				}else {
					csvFileOut.println(register.getEntry(i).getDate()+","+register.getEntry(i).getID()+","
							+register.getEntry(i).getCategory()+","+register.getEntry(i).getDescription()+",Expense,"+register.getEntry(i).getAmount());
				}
			}//for
			
			csvFileOut.close();
		}catch(IOException e) {
			System.out.println("IOException: "+e.getMessage());
		}//catch()
	}//getCSV()
	
	
	public static void displayMenu() {

		System.out.println("1. View Income");
		System.out.println("2. View Expense");
		System.out.println("3. View All");
		System.out.println("4. Add Expense");
		System.out.println("5. Add Income");
		System.out.println("6. Delete Entry");
		System.out.println("7. View Stat");
		System.out.println("8. Get csv");
		System.out.println("9. Search");
		System.out.println("10. Edit Entry");
		System.out.println("11. Read Data From txt File");
		System.out.println("12. Exit");
		System.out.print(">>> ");
		
	}//displayMenu()
	
	public static void selectMenu(AccountRegister register) {
		Scanner scn = new Scanner(System.in);
		while(true) {
			displayMenu();
			int menuInput = scn.nextInt();
			if (menuInput == 1) {
				register.viewIncome();
			} else if (menuInput == 2) {
				register.viewExpense();
			}else if (menuInput == 3) {
				register.viewAll();
			}else if (menuInput == 4) {
				if(!addExpense(register)) {
					continue;
				}
				System.out.println("*** Expense added to account ***\n");
			}else if(menuInput == 5) {
				if (!addIncome(register)) {
					continue;
				}
				System.out.println("*** Income added to account ***\n");
				continue;
			}else if(menuInput == 6) {
				System.out.println("Enter item ID:");
				scn.nextLine();
				String did = scn.nextLine();
				register.singleEntryView(did);
				System.out.println("Confirm delete 1. Yes 2. No");
				int confirm = scn.nextInt();
				if(confirm == 1) {
					register.delete(did);
					System.out.println("Item with ID: "+did+" has been delete successfully");
					System.out.println("----------------------------------------------\n");
					continue;
				}else {
					continue;
				}
			}else if (menuInput == 7) {
				register.viewStat();
			}else if(menuInput == 8){
				getCSV(register);
				System.out.println("CSV file generated. Open save.csv");
			}else if (menuInput == 9) {
				scn.nextLine();
				System.out.println("Enter item ID:");
				String sid = scn.nextLine();
				register.singleEntryView(sid);
				continue;
			}else if(menuInput == 10) {
				scn.nextLine();
				System.out.println("Enter item ID:");
				String eid = scn.nextLine();
				edit(register, eid);
				continue;
			}else if(menuInput == 11) {
				register.clearRegister();
				try {
					readTxt(register);
				}catch(IOException e) {
					System.out.println("IOException: "+e.getMessage());
					}//catch()
				
			}else if(menuInput == 12){
				saveDat(register);
				saveTxt(register);
				System.out.println("Updates Saved");
				System.out.println("Goodbye");
				break;
			}else {
				System.out.println("Invalid Selection");
			}
		}
		
		
	}//selectMenu()
	
	public static boolean addExpense(AccountRegister register) {
		Scanner scn = new Scanner(System.in);
		System.out.println("Enter new expense to account\n");
		System.out.println("Select Category: ");
		
		System.out.println("1. Groceries");
		System.out.println("2. Entertainment");
		System.out.println("3. Transportation");
		System.out.println("4. Food");
		System.out.println("5. Rent and/or House Bills");
		System.out.println("6. Business");
		System.out.println("7. Wardrobe");
		System.out.println("8. Electronics and Gadgets");
		System.out.println("9. Misc");
		System.out.print(">>> ");
		
		int input = scn.nextInt();
		String cat; 
		switch (input) {
		case 1:
			cat = "Groceries";
			break;
		case 2:
			cat  = "Entertainment";
			break;
		case 3:
			cat  = "Transportation";
			break;
		case 4:
			cat  = "Food";
			break;
		case 5:
			cat  = "Rent and/or House Bills";
			break;
		case 6:
			cat  = "Business";
			break;
		case 7:
			cat  = "Wardrobe";
			break;
		case 8:
			cat  = "Electonics and Gadget";
			break;
		case 9:
			cat  = "Misc";
			break;
		default:
			System.out.println("Invalid input");
			System.out.println("----------------------------------\n");
			return false;
		}//switch()
		
		System.out.println("Category: "+cat);
		
		System.out.print("Enter Description: ");
		scn.nextLine();
		String desc = scn.nextLine();
		
		System.out.print("Enter amount: ");
		double amount = scn.nextDouble();
		
		System.out.println("Do you want to save expense with current date or enter a specific date?\n");
		System.out.println("1. Current date\n2. Enter specific date");
		int dEntry = scn.nextInt();
		LocalDate exDate;
		if (dEntry == 2) {
			System.out.print("Enter date in the format dd/mm/yyyy: ");
			String exD = scn.next();
			String[] token = exD.split("/");
			try {
				exDate = LocalDate.of(Integer.valueOf(token[2]), Integer.valueOf(token[1]), Integer.valueOf(token[0]));
			} catch (Exception e) {
				System.out.println("invalid date input");
				System.out.println("----------------------------------\n");
				return false;
			}
			
			if ((LocalDate.now().compareTo(exDate)) < 0) {
				System.out.println("Date entered is in the future");
				System.out.println("----------------------------------\n");
				return false;
			}
		}else if (dEntry == 1) {
			exDate = LocalDate.now();
		}else {
			System.out.println("Invalid input");
			System.out.println("----------------------------------\n");
			return false;
		}
		
		register.add(new Expense(Expense.genID(register.getSize()), cat, amount, desc, exDate));
		return true;
		
	}//addExpense()
	
	public static boolean addIncome(AccountRegister register) {
		Scanner scn = new Scanner(System.in);
		System.out.println("Enter new income to account\n");
		System.out.println("Select Category: ");
		
		System.out.println("1. salary");
		System.out.println("2. Investment Returns");
		System.out.println("3. Gifts");
		System.out.println("4. Friends/Families");
		System.out.println("5. Others");
		
		int input = scn.nextInt();
		String cat; 
		switch (input) {
		case 1:
			cat = "Salary";
			break;
		case 2:
			cat  = "Investment Returns";
			break;
		case 3:
			cat  = "Gifts";
			break;
		case 4:
			cat  = "Friends/Families";
			break;
		case 5:
			cat  = "Others";
			break;
		default:
			System.out.println("Invalid input");
			System.out.println("----------------------------------\n");
			return false;
		}//switch()
		
		System.out.println("Category: "+cat);
		
		System.out.print("Enter Description: ");
		scn.nextLine();
		String desc = scn.nextLine();
		
		System.out.print("Enter amount: ");
		double amount = scn.nextDouble();
		
		System.out.println("Do you want to save income with current date or enter a specific date?\n");
		System.out.println("1. Current date\n2. Enter specific date");
		int dEntry = scn.nextInt();
		LocalDate exDate;
		if (dEntry == 2) {
			System.out.print("Enter date in the format dd/mm/yyyy: ");
			String exD = scn.next();
			String[] token = exD.split("/");
			try {
				exDate = LocalDate.of(Integer.valueOf(token[2]), Integer.valueOf(token[1]), Integer.valueOf(token[0]));
			} catch (Exception e) {
				System.out.println("invalid date input");
				System.out.println("----------------------------------\n");
				return false;
			}
			
			if ((LocalDate.now().compareTo(exDate)) < 0)  {
				System.out.println("Date entered is in the future");
				System.out.println("----------------------------------\n");
				return false;
			}
		}else if (dEntry == 1) {
			exDate = LocalDate.now();
		}else {
			System.out.println("Invalid input");
			System.out.println("----------------------------------\n");
			return false;
		}
		
		register.add(new Income(Income.genID(register.getSize()), cat, amount, desc, exDate));
		return true;
		
	}//addExpense()
	
	public static void edit(AccountRegister register, String ID) {
		int index = Integer.valueOf(ID.substring(2));
		register.singleEntryView(ID);
		Scanner scn = new Scanner(System.in);
		while (true) {
			System.out.println("Select Column to edit");
			System.out.println("1. Date");
			System.out.println("2. Category");
			System.out.println("3. Description");
			System.out.println("4. Amount");
			System.out.println("5. Exit edit");
			
			int input = scn.nextInt();
			if(input == 1) {
				System.out.println("1. Current date\n2. Enter specific date");
				int dEntry = scn.nextInt();
				LocalDate date = enterDate(dEntry);
				if(date == null) continue;
				else {
					register.getEntry(index).setDate(date);
					System.out.println("Date updated");
				}
			}else if(input == 2) {
				if (register.getEntry(index) instanceof Income) {
					String cat = enterCategory("Income");
					if (cat == null) continue;
					else register.getEntry(index).setCategory(cat);
				}else {
					String cat = enterCategory("Expense");
					if (cat == null) continue;
					else register.getEntry(index).setCategory(cat);
				}
			}else if(input == 3) {
				scn.nextLine();
				System.out.println("Enter New Description: ");
				String desc = scn.nextLine();
				register.getEntry(index).setDescription(desc);
				System.out.println("Description updated");
			}else if(input == 4) {
				System.out.println("Enter new amount");
				double amount = scn.nextDouble();
				register.getEntry(index).setAmount(amount);
				System.out.println("Amount updated");
			}else if(input == 5) {
				//System.out.println("Entry Updated");
				break;
			}else {
				System.out.println("Invalid Selection");
			}
		}
	}//edit()
	
	private static LocalDate enterDate(int opt) {
		Scanner scn = new Scanner(System.in);
		LocalDate date;
		if (opt == 2) {
			System.out.print("Enter date in the format dd/mm/yyyy: ");
			String d = scn.nextLine();
			String[] token = d.split("/");
			try {
				date = LocalDate.of(Integer.valueOf(token[2]), Integer.valueOf(token[1]), Integer.valueOf(token[0]));
			} catch (Exception e) {
				System.out.println("invalid date input");
				System.out.println("----------------------------------\n");
				return null;
			}
			
			if ((LocalDate.now().compareTo(date)) < 0)  {
				System.out.println("Date entered is in the future");
				System.out.println("----------------------------------\n");
				return null;
			}
			
			return date;
		}else if (opt == 1) {
			date = LocalDate.now();
			return date;
		}else {
			System.out.println("Invalid input");
			System.out.println("----------------------------------\n");
			return null;
		}
		
	}//enterDate()
	
	private static String enterCategory(String type) {
		Scanner scn = new Scanner(System.in);
		
		System.out.println("Select Category: ");
		
		if (type.equals("Income")) {
			System.out.println("1. salary");
			System.out.println("2. Investment Returns");
			System.out.println("3. Gifts");
			System.out.println("4. Friends/Families");
			System.out.println("5. Others");
			
			int input = scn.nextInt();
			String cat; 
			switch (input) {
			case 1:
				cat = "Salary";
				break;
			case 2:
				cat  = "Investment Returns";
				break;
			case 3:
				cat  = "Gifts";
				break;
			case 4:
				cat  = "Friends/Families";
				break;
			case 5:
				cat  = "Others";
				break;
			default:
				System.out.println("Invalid input");
				System.out.println("----------------------------------\n");
				return null;
			}//switch()
			
			return cat;
		}else {
			System.out.println("1. Groceries");
			System.out.println("2. Entertainment");
			System.out.println("3. Transportation");
			System.out.println("4. Food");
			System.out.println("5. Rent and/or House Bills");
			System.out.println("6. Business");
			System.out.println("7. Wardrobe");
			System.out.println("9. Electronics and Gadgets");
			System.out.println("8. Misc");
			System.out.print(">>> ");
			
			int input = scn.nextInt();
			String cat; 
			switch (input) {
			case 1:
				cat = "Groceries";
				break;
			case 2:
				cat  = "Entertainment";
				break;
			case 3:
				cat  = "Transportation";
				break;
			case 4:
				cat  = "Food";
				break;
			case 5:
				cat  = "Rent and/or House Bills";
				break;
			case 6:
				cat  = "Business";
				break;
			case 7:
				cat  = "Wardrobe";
				break;
			case 8:
				cat  = "Electonics and Gadget";
				break;
			case 9:
				cat  = "Misc";
				break;
			default:
				System.out.println("Invalid input");
				System.out.println("----------------------------------\n");
				return null;
			}//switch()
			
			return cat;
		}
	}//enterCategory()

	
	
}//class
