import java.util.Scanner;

public class BankSystem {

    // Date
    public static class Date {
        private int day;
        private String month;
        private int year;

        public Date() {
            this.day = 1;
            this.month = "January";
            this.year = 2000;
        }

        public Date(int day, String month, int year) {
            this.day = day;
            this.month = month;
            this.year = year;
        }

        public int getDay() { return day; }
        public void setDay(int day) { this.day = day; }

        public String getMonth() { return month; }
        public void setMonth(String month) { this.month = month; }

        public int getYear() { return year; }
        public void setYear(int year) { this.year = year; }

        @Override
        public String toString() {
            return day + " " + month + " " + year;
        }
    }

    // Person
    public static class Person {
        private String name;
        private String surname;
        private int age;
        private Date bDate;

        public Person() {
            this.name = "";
            this.surname = "";
            this.age = 0;
            this.bDate = new Date();
        }

        public Person(String name, String surname) {
            this.name = name;
            this.surname = surname;
            this.age = 0;
            this.bDate = new Date();
        }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getSurname() { return surname; }
        public void setSurname(String surname) { this.surname = surname; }

        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }

        public Date getBDate() { return bDate; }
        public void setBDate(Date bDate) { this.bDate = bDate; }

        @Override
        public String toString() {
            return name + " " + surname + ", Age: " + age + ", Birth Date: " + bDate;
        }
    }

    // Account
    public static class Account {
        private int id;
        private double balance;
        private double annualInterestRate;
        private Date dateCreated;
        private Person objPerson;

        public Account() {
            this.id = 0;
            this.balance = 0.0;
            this.annualInterestRate = 0.0;
            this.dateCreated = new Date();
            this.objPerson = new Person();
        }

        public Account(int id, double balance) {
            this.id = id;
            this.balance = balance;
            this.annualInterestRate = 0.0;
            this.dateCreated = new Date();
            this.objPerson = new Person();
        }

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public double getBalance() { return balance; }
        public void setBalance(double balance) { this.balance = balance; }

        public double getAnnualInterestRate() { return annualInterestRate; }
        public void setAnnualInterestRate(double annualInterestRate) { this.annualInterestRate = annualInterestRate; }

        public Date getDateCreated() { return dateCreated; }
        public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }

        public Person getObjPerson() { return objPerson; }
        public void setObjPerson(Person objPerson) { this.objPerson = objPerson; }

        public double getMonthlyInterestRate() {
            return annualInterestRate / 12;
        }

        public double getMonthlyInterest() {
            int currentYear = 2025; 
            int accountAge = currentYear - dateCreated.getYear();

            if(accountAge < 0) {
                return 0;
            } else {
                return balance * (getMonthlyInterestRate() / 100);
            }
        }

        public void withdraw(double amount) {
            if (amount <= balance) {
                balance -= amount;
            } else {
                System.out.println("Insufficient balance for withdrawal");
            }
        }

        public void deposit(double amount) {
            balance += amount;
        }

        public void transferMoney(Account acc1, double amount) {
            if (amount <= balance) {
                this.withdraw(amount);
                acc1.deposit(amount);
                System.out.println("Successfully transferred " + amount + " to account " + acc1.getId());
            } else {
                System.out.println("Insufficient balance for transfer");
            }
        }

        @Override
        public String toString() {
            return "Account ID: " + id + "\nBalance: " + balance + 
                   "\nAnnual Interest Rate: " + annualInterestRate + "%" + 
                   "\nDate Created: " + dateCreated + 
                   "\nOwner: " + objPerson;
        }
    }

    // SAVING ACCOUNT
    public static class SavingAccount extends Account {
        @Override
        public void transferMoney(Account acc1, double amount) {
            double fee = 20; 
            double totalAmount = amount + fee;
            if (totalAmount <= getBalance()) {
                withdraw(totalAmount);
                acc1.deposit(amount);
                System.out.println("Successfully transferred " + amount + " with a fee of " + fee + " to account " + acc1.getId());
            } else {
                System.out.println("Insufficient balance for transfer with fee");
            }
        }
    }

    // FIX ACCOUNT
    public static class FixAccount extends Account {

        @Override
        public void withdraw(double amount) {
            int currentYear = 2025; 
            int accountYear = getDateCreated().getYear();
            if(currentYear - accountYear < 1) {
                System.out.println("Cannot withdraw money because the withdrawal year must be more than one year from the account creation year");
            } else {
                super.withdraw(amount);
            }
        }

        @Override
        public void transferMoney(Account acc1, double amount) {
            System.out.println("FixAccount type cannot transfer money");
        }
    }

    // MAIN
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Test SavingAccount
        System.out.println("=== SavingAccount Test ===");
        SavingAccount sa = new SavingAccount();
        sa.setId(1123);
        sa.setBalance(20000);
        sa.setAnnualInterestRate(4.5);
        
        System.out.print("First Name: ");
        String name = sc.nextLine();
        System.out.print("Last Name: ");
        String surname = sc.nextLine();
        System.out.print("Age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Birth Day: ");
        int day = sc.nextInt();
        sc.nextLine();
        System.out.print("Birth Month: ");
        String month = sc.nextLine();
        System.out.print("Birth Year: ");
        int year = sc.nextInt();
        sc.nextLine();
        
        Person p = new Person(name, surname);
        p.setAge(age);
        p.setBDate(new Date(day, month, year));
        sa.setObjPerson(p);
        sa.setDateCreated(new Date(1, "January", 2024));
        
        sa.withdraw(2500);
        sa.deposit(3000);
        Account targetAcc1 = new Account(1100, 10000);
        sa.transferMoney(targetAcc1, 1000);
        
        System.out.println("Source Account:");
        System.out.println(sa);
        System.out.println("Monthly Interest: " + sa.getMonthlyInterest());
        
        System.out.println("\nTarget Account:");
        System.out.println(targetAcc1);
        System.out.println("Monthly Interest: " + targetAcc1.getMonthlyInterest());
        
        // Test FixAccount
        System.out.println("\n=== FixAccount Test ===");
        FixAccount fa = new FixAccount();
        fa.setId(1124);
        fa.setBalance(20000);
        fa.setAnnualInterestRate(7);

        System.out.print("First Name: ");
        name = sc.nextLine();
        System.out.print("Last Name: ");
        surname = sc.nextLine();
        System.out.print("Age: ");
        age = sc.nextInt();
        sc.nextLine();
        System.out.print("Birth Day: ");
        day = sc.nextInt();
        sc.nextLine();
        System.out.print("Birth Month: ");
        month = sc.nextLine();
        System.out.print("Birth Year: ");
        year = sc.nextInt();
        sc.nextLine();

        Person p2 = new Person(name, surname);
        p2.setAge(age);
        p2.setBDate(new Date(day, month, year));
        fa.setObjPerson(p2);
        fa.setDateCreated(new Date(1, "January", 2024));

        fa.withdraw(2500);
        fa.deposit(3000);

        Account targetAcc2 = new Account(1100, 10000);
        fa.transferMoney(targetAcc2, 1000);

        System.out.println("FixAccount:");
        System.out.println(fa);
        System.out.println("Monthly Interest: " + fa.getMonthlyInterest());

        System.out.println("\nTarget Account:");
        System.out.println(targetAcc2);
        System.out.println("Monthly Interest: " + targetAcc2.getMonthlyInterest());

        sc.close();
    }
}
