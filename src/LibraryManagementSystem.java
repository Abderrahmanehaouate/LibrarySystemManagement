import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.System.*;

public class LibraryManagementSystem {

    public static void main(String[] args) {
        start();
    }
    private static void start(){
        String[] options = getStrings();
        Scanner scanner = new Scanner(in);
        while (true){
            printMenu(options);
            try {
                int option = scanner.nextInt();

                switch(option){
                    case 1:
                        addBooks();
                        break;
                    case 2:
                        updateBook();
                        break;
                    case 3:
                        deleteBook();
                        break;
                    case 4:
                        displayBooks();
                        break;
                    case 5:
                        searchBook();
                        break;
                    case 6:
                        borrowBook();
                        break;
                    case 7:
                        returnBook();
                        break;
                    case 8:
                        generateStatisticalReport();
                        break;
                    case 9: break;
                    case 10:
                        exit();
                    break;
                    default:
                        System.out.println("\n Option not found, please select an existing option.\n");
                        break;
                }
            }catch (Exception ex){
                out.println("\nPlease enter an integer value between 1 and 9\n");
                scanner.next();
            }
        }
    }
    private static String[] getStrings() {
        String[] options;

        options = new String[]{
                "------------- Library Menu Options ------------- |",
                "-------------------------------------------------|",
                "1 - Add a New Book                               |",
                "-------------------------------------------------|",
                "2 - Modify a Book                                |",
                "-------------------------------------------------|",
                "3 - Delete a Book                                |",
                "-------------------------------------------------|",
                "4 - Display Books                                |",
                "-------------------------------------------------|",
                "5 - Search for a Book                            |",
                "-------------------------------------------------|",
                "6 - Borrow a Book by ISBN                        |",
                "-------------------------------------------------|",
                "7 - Return a Book                                |",
                "-------------------------------------------------|",
                "8 - Show Library Statistics                      |",
                "-------------------------------------------------|",
                "9 - Exit                                         |",
                "-------------------------------------------------|",
        };

        return options;
    }
    public static void printMenu(String[] options){
        out.println("\t\t\t\t __________________________________________________");
        for (String option : options){
            out.println("\t\t\t\t| " + option);
        }
        out.print("\n\t\t\t\t Choose your option : ");
    }
    public static String validateInput(String prompt){
        Scanner scanner = new Scanner(in);
        boolean state = true;
        String input = "";
        while (state) {
            out.print("Enter the book "+ prompt + ": ");
            input = scanner.nextLine();

            if (input.isEmpty()) {
                out.println("Please enter a value that is not empty for the " + prompt + ": ");
            } else {
                state = false;
            }
        }
        return input;
    }
    public static String validateInputMemberShip(){
        Scanner scanner = new Scanner(in);
        boolean state = true;
        String input = "";
        while (state) {
            out.print("Enter The MemberShip Code: ");
            input = scanner.nextLine();

            if (input.isEmpty()) {
                out.print("Please enter a value that is not empty for the MemberShip Code : ");
            } else {
                state = false;
            }
        }
        return input;
    }
    public static void displayBooks(){
        Scanner scanner = new Scanner(in);
        while (true){
            try {
                out.println("1 - Would you like to display all books?");
                out.println("2 - Would you like to display available books?");
                out.println("3 - Would you like to display borrowed books?");
                out.println("4 - Would you like to display missing books?");
                out.println("5 - go back to menu");
                out.println("6 - Exit ");
                out.print("Choose your option : ");
                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        getAllBooks();
                        break;
                    case 2:
                        getBooksByStatus("available");
                        break;
                    case 3:
                        getBorrowedBooks();
                        break;
                    case 4:
                        getBooksByStatus("missing");
                        break;
                    case 5:
                        start();
                        break;
                    case 6: exit();
                        break;
                    default:
                        System.out.print("Invalid option. Please choose a valid option.");
                        scanner.next();
                }

            }catch (Exception ex){
                out.print("Please enter an integer value between 1 and 6");
                scanner.next();
            }
        }
    }
    public static void getAllBooks() {
        Library library = new Library();
        List<Book> books;
        books = library.getAllBooks();

        if (books.isEmpty()) {
            System.out.println("No books found in the database.");
        } else {
            System.out.println("List of books:");
            int counter = 1;
            for (Book book : books) {
                out.println("Book number " + counter);
                System.out.println("Title:   " + book.getTitle());
                System.out.println("Author:  " + book.getAuthor());
                System.out.println("ISBN:    " + book.getISBN());
                System.out.println("Status:  " + book.getStatus());
                System.out.println("----------------------------");
                counter++;
            }
        }
    }
    public static void getBooksByStatus(String status){
        Library library = new Library();
        List<Book> books = library.getBooksByStatus(status);

        if (books.isEmpty()) {
            out.println("No books found in the database.");
        } else {
            out.println("List of books:");
            int counter = 1;
            for (Book book : books) {
                out.println("Book number " + counter);
                out.println("Title:   " + book.getTitle());
                out.println("Author:  " + book.getAuthor());
                out.println("ISBN:    " + book.getISBN());
                out.println("Status:  " + book.getStatus());
                out.println("----------------------------");
                counter++;
            }
        }
    }
    public static void getBorrowedBooks(){
        Library library = new Library();
        List<BorrowedBook> borrowedBooks;
        borrowedBooks = library.getAllBorrowedBooks();

        if (borrowedBooks.isEmpty()) {
            out.println("No books borrowed found in the database.");
        } else {
            System.out.println("List of borrowed books:");
            int counter = 1;
            for (BorrowedBook borrowedBook : borrowedBooks) {
                out.println("Book number " + counter);
                out.println("---------------------------------------------");
                out.println("Title:           " + borrowedBook.getBookTitle());
                out.println("Author:          " + borrowedBook.getBookAuthor());
                out.println("ISBN:            " + borrowedBook.getBookISBN());
                out.println("Status:          " + borrowedBook.getBookStatus());
                out.println("Client:          " + borrowedBook.getClientName());
                out.println("memberShip:      " + borrowedBook.getClientMemberShip());
                out.println("Date Borrowing:  " + borrowedBook.getReserveDate());
                out.println("---------------------------------------------");
                counter++;
            }
        }
    }

    public static void addBooks() {
        Library library = new Library();

        String title = validateInput("title");
        String author = validateInput("author");
        String ISBN = validateInput("ISBN");

        Book book = new Book(title, author, ISBN);

        if(library.checkIsbnIfAlreadyExist(ISBN)){
            out.println("\nThis book is already exist.\n");
            optionsAddBooks();
        }else{
            library.addBook(book);
            optionsAddBooks();
        }

    }
    public static void optionsAddBooks(){
        while (true){
            Scanner scanner = new Scanner(in);
            try {
                out.println("1 - Would you like to add another book?");
                out.println("2 - Do you want to return to the main menu?");
                out.println("3 - exit ");
                out.print("Choose your option : ");
                int option = scanner.nextInt();
                if (option == 1){
                    addBooks();
                }else if (option == 2){
                    start();
                } else if (option == 3) {
                    exit();
                }else out.print("option not found, please add an exist option : ");
            }catch (Exception e){
                out.print("Please enter an integer value between 1 and 3");
                scanner.next();
            }
        }
    }
    public static void deleteBook(){
        Scanner scanner = new Scanner(in);
        String ISBN = validateInput("ISBN");
        Library library = new Library();
        library.deleteBook(ISBN);
        while (true){
            out.println("1 - Would you like to delete another book?");
            out.println("2 - Do you want to return to the main menu?");
            out.println("3 - exit ");
            out.print("Choose your option : ");
            int option = scanner.nextInt();
            if (option == 1){
                deleteBook();
            }else if (option == 2){
                start();
            } else if (option == 3) {
                exit();
            }else out.print("option not found, please add an exist option : ");
        }

    }
    public static void getBookByISBN(String ISBN){
        Library library = new Library();
        List<Book> books;
        books = library.getBooksByISBN(ISBN);
        if (books.isEmpty()) {
            System.out.print("\n No book found with ISBN " + "\"" + ISBN + "\"" +  " Try again.\n");
            SubOptionsUpdate();
        }
        for (Book book : books) {
            out.println("\n this is old information of this book that do you want to update it.");
            out.println("----------------------------");
            out.println("Title:   " + book.getTitle());
            out.println("Author:  " + book.getAuthor());
            out.println("ISBN:    " + book.getISBN());
            out.println("Status:  " + book.getStatus());
            out.println("----------------------------");
        }
    }

    public static void updateBook(){
        String ISBN = validateInput("ISBN");
        getBookByISBN(ISBN);
        optionsUpdateBook(ISBN);
    }
    public static void optionsUpdateBook(String ISBN){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                out.println("1 - Would you like to update all information of book?");
                out.println("2 - Do you want to update title?");
                out.println("3 - Do you want to update author?");
                out.println("4 - Do you want to update ISBN?");
                out.println("5 - Do you want to update status?");
                out.println("6 - Do you want to update another book?");
                out.println("7 - Go back to menu");
                out.println("8 - Exit :)");
                out.print("\n Choose your option: ");
                int option = scanner.nextInt();

                switch (option) {
                    case 1:
                        updateAllInformationBook(ISBN);
                        SubOptionsUpdate();
                        break;
                    case 2:
                        updateTitle(ISBN);
                        SubOptionsUpdate();
                        break;
                    case 3:
                        updateAuthor(ISBN);
                        SubOptionsUpdate();
                        break;
                    case 4:
                        updateISBN(ISBN);
                        SubOptionsUpdate();
                        break;
                    case 5:
                        updateBookStatus(ISBN);
                        SubOptionsUpdate();
                        break;
                    case 6:
                        updateBook();
                        break;
                    case 7:
                        start();
                        break;
                    case 8:
                        exit();
                    default:
                        System.out.print("\n Option not found, please select an existing option.\n");
                        break;
                }
            }catch (Exception ex){
                out.print("\n Please enter an integer value between 1 and 8 \n");
                scanner.next();
            }

        }

    }
    public static void SubOptionsUpdate(){
        while (true) {
            Scanner scanner = new Scanner(System.in);
            try {
                System.out.println("1 - Would you like to update another book?");
                System.out.println("2 - Would you like to return the main menu?");
                System.out.println("3 - Exit ");
                System.out.print("Choose your option: ");

                int option = scanner.nextInt();

                switch (option) {
                    case 1:
                        updateBook();
                        break;
                    case 2:
                        start();
                        break;
                    case 3:
                        exit();
                    default:
                        System.out.print("Option not found, please select an existing option.");
                        scanner.next();
                        break;
                }
            }catch (Exception ex){
                out.print("\nPlease enter an integer value between 1 and 3\n");
                scanner.next();
            }
        }
    }
    public static void updateAllInformationBook(String ISBN){
        Library library = new Library();

        String title = validateInput("title");
        String updatedISBN = validateInput("ISBN");
        String author = validateInput("author");

        Book book = new Book(title, author, updatedISBN);

        if(library.checkIsbnIfAlreadyExist(updatedISBN)){
            out.println("This book is already exist. Try to another time to update it with another code of ISBN");
            SubOptionsUpdate();
        }else{
            library.updateAllInformationBook(book, ISBN);
        }
    }
    public static void updateTitle(String ISBN){
        String title = validateInput("title");
        Book book = new Book();
        book.setTitle(title);
        Library library = new Library();
        library.updateTitle(book, ISBN);
    }
    public static void updateAuthor(String ISBN){
        String author = validateInput("author");

        Book book = new Book();
        book.setAuthor(author);
        Library library = new Library();
        library.updateAuthor(book, ISBN);
    }
    public static void updateISBN(String ISBN){
        Library library = new Library();
        Book book = new Book();

        String updatedISBN = validateInput("ISBN");

        if(library.checkIsbnIfAlreadyExist(updatedISBN)){
            out.println("\nThis ISBN is already exist because any book have unique ISBN. Try to update it with another code of ISBN\n");
            SubOptionsUpdate();
        }else{
            book.setISBN(updatedISBN);
            library.updateISBN(book, ISBN);
        }


    }
    public static void updateBookStatus(String ISBN){
        Scanner scanner = new Scanner(in);
        Library library = new Library();
        while (true){
            try {
                out.println("2 - make the book missing");
                out.print("Choose your option: ");

                int option = scanner.nextInt();

                switch (option) {
                    case 1:
                        library.updateBookStatus(ISBN, "missing");
                        SubOptionsUpdate();
                        break;
                    default:
                        System.out.println("Option not found, please select an existing option.");
                        scanner.next();
                        break;
                }
            }catch (Exception ex){
                out.print("Please enter an integer value between 1 and 3");
                scanner.next();
            }
        }

    }
    public static void searchBook() {
        Scanner scanner = new Scanner(System.in);
        while (true){
            try {
                out.println("Search for books by:");
                out.println("1 - Title");
                out.println("2 - Author");
                out.println("3 - Return to main menu");
                out.print("Choose your search criteria: ");

                int searchOption = scanner.nextInt();
                scanner.nextLine();

                switch (searchOption) {
                    case 1:
                        searchByCriteria("title");
                        break;
                    case 2:
                        searchByCriteria("author");
                        break;
                    case 3:
                        start();
                        return;
                    default:
                        System.out.print("\nInvalid option. Please choose a valid search criteria.\n");
                        break;
                }
            }catch (Exception ex){
                out.print("\nPlease enter an integer value between 1 and 3\n");
                scanner.next();
            }
        }
    }

    public static void searchByCriteria(String criteria){
        List<Book> matchingBooks = null;
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        String keyword = "";
        boolean state = true;
        while (state) {
            System.out.print("Enter your search keyword: ");
            keyword = scanner.nextLine();

            if (keyword.isEmpty()) {
                out.println("Please enter a value that is not empty for the keyword: ");
            } else {
                state = false;
            }
        }

        matchingBooks = library.searchBook(criteria, keyword);
        if (matchingBooks != null) {
            displaySearchResults(matchingBooks);
        }
        generalOptions();
    }
    public static void displaySearchResults(List<Book> matchingBooks) {
        if (matchingBooks.isEmpty()) {
            System.out.println("\n No books found matching the search criteria.\n");
        } else {
            System.out.println("\nBooks matching the search criteria:\n");
            for (Book book : matchingBooks) {
                out.println("Title: " + book.getTitle());
                out.println("Author: " + book.getAuthor());
                out.println("ISBN: " + book.getISBN());
                out.println("Status: " + book.getStatus());
                out.println("----------------------------");
            }
        }
    }

    public static void borrowBook(){
        while (true) {
            Scanner scanner = new Scanner(System.in);
            try {
                out.println("1 - Current library member");
                out.println("2 - Join the library as a member");
                out.println("3 - Back to main menu");
                out.println("4 - Exit");
                out.print("Select an option: ");
                int option = scanner.nextInt();

                switch (option) {
                    case 1:
                        borrowBookFromOldClient();
                        break;
                    case 2:
                        borrowBookFromNewClient();
                        break;
                    case 3:
                        start();
                        break;
                    case 4:
                        exit();
                        break;
                    default:
                        System.out.print("\nOption not found, please select an existing option.\n");
                        scanner.next();
                        break;
                }
            }catch (Exception ex){
                out.print("\nPlease enter an integer value between 1 and 3\n");
                scanner.next();
            }
        }
    }
    public static void borrowBookFromNewClient(){
        Scanner scanner = new Scanner(in);
        boolean state = true;
        String name = "";
        while (state) {
            out.print("Enter your fullName : ");
            name = scanner.nextLine();

            if (!name.isEmpty()) {
                state = false;
            } else {
                out.print("\n Please enter a value that is not empty for the fullName: \n");
            }
        }

        String memberShip = generateMembershipCode();

        Client client = new Client(name, memberShip);
        Library library = new Library();
        library.addClient(client);
        out.println("\n Membership Code: " + memberShip + " \n");

        borrowBookFromClient(memberShip);
    }
    public static void borrowBookFromOldClient(){
        Library library = new Library();

        String memberShip = checkAndGetMemberShipClient();

        if(library.checkIfClientAlreadyReservedBook(memberShip)){
            out.println("\nAlready you have a book, you can't borrow this book if you don't return the previous book.\n");
            generalOptions();
        }else{
            borrowBookFromClient(memberShip);
        }
    }
    public static void borrowBookFromClient(String memberShip){
        String ISBN = validateInput("ISBN");
        Library library = new Library();
        List<Book> books;
        books = library.getAvailableBooksByISBN(ISBN);
        if (books.isEmpty()) {
            System.out.println("\n No book found with ISBN " + "\"" + ISBN + "\"" +  " Try again.\n");
            // todo : another subOptionsBoroowBook for make client who have a memberShip go direct to reserve book , not go to first steps .
            generalOptions();
        }else{
            for (Book book : books) {
                out.println("\n This is information about the book you want to borrow. \n");
                out.println("----------------------------");
                out.println("Title:   " + book.getTitle());
                out.println("Author:  " + book.getAuthor());
                out.println("ISBN:    " + book.getISBN());
                out.println("Status:  " + book.getStatus());
                out.println("----------------------------");
            }
            BorrowedBook borrowedBook = new BorrowedBook(ISBN, memberShip);
            library.borrowBookFromClient(borrowedBook);
            generalOptions();
        }
    }

    public static String checkAndGetMemberShipClient(){
        String memberShip = validateInputMemberShip();

        Library library = new Library();
        library.checkMemberShip(memberShip);
        if (library.checkMemberShip(memberShip)) {
            return memberShip;
        } else {
            System.out.println("\nMembership Code not found. Please try again or be a memberShip By to go Join the library option.\n");
            generalOptions();
            return null;
        }
    }
    public static void generalOptions(){
        while (true) {
            Scanner scanner = new Scanner(System.in);
            try {
                out.println("1 - Would you like to return the main menu?");
                out.println("2 - Exit ");
                out.print("Choose your option: ");

                int option = scanner.nextInt();

                switch (option) {
                    case 1:
                        start();
                        break;
                    case 2:
                        exit();
                    default:
                        System.out.print("\nOption not found, please select an existing option.");
                        scanner.next();
                        break;
                }
            }catch (Exception ex){
                out.print("Please enter an integer value between 1 and 3");
                scanner.next();
            }
        }
    }

    public static String generateMembershipCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());
        Random random = new Random();

        int randomNumber = 10 + random.nextInt(90);
        String membershipCode = "M" + timestamp + randomNumber;

        return membershipCode;
    }

    public static void returnBook(){
        Library library = new Library();
        String memberShip = validateInputMemberShip();
        library.checkMemberShip(memberShip);
        if(!library.checkMemberShip(memberShip)){
            System.out.println("\nMembership Code not found. Please try again or be a memberShip By to go Join the library option.\n");
            generalOptions();
        }

        if(library.checkIfClientAlreadyReservedBook(memberShip)){

            String ISBN = library.getReservedBookISBN(memberShip);

            List<Book> books;
            books = library.getBooksByISBN(ISBN);
                for (Book book : books) {
                    out.println("\n This is information about the book you reserved . \n");
                    out.println("----------------------------");
                    out.println("Title:   " + book.getTitle());
                    out.println("Author:  " + book.getAuthor());
                    out.println("ISBN:    " + book.getISBN());
                    out.println("----------------------------\n");
                }

            library.returnBook(ISBN, memberShip);
            generalOptions();
        }else {
            out.println("This Client have no Book reserved");
            generalOptions();

        }
    }

    public static int getTotalBooks() {
        Library library = new Library();
        List<Book> books;
        books = library.getAllBooks();

        int totalBooks = 0;

        for (Book book : books) {
            totalBooks++;
        }

        return totalBooks;
    }

    public static void generateStatisticalReport() {
        Library library = new Library();
        int totalBooks = getTotalBooks();

        if (totalBooks == 0) {
            System.out.println("No books in the library.");
            generalOptions();
        }

        int availableBooks = library.getBooksByStatus("available").size();
        int borrowedBooks = library.getBooksByStatus("borrowed").size();
        int missingBooks = library.getBooksByStatus("missing").size();

        double availablePercentage = (double) availableBooks / totalBooks * 100;
        double borrowedPercentage = (double) borrowedBooks / totalBooks * 100;
        double missingPercentage = (double) missingBooks / totalBooks * 100;

        out.println("Statistical Report:\n");
        out.println("Total Books: " + totalBooks);
        out.println("Available Books: " + availableBooks + " (" + String.format("%.2f", availablePercentage) + "%)");
        out.println("Borrowed Books: " + borrowedBooks + " (" + String.format("%.2f", borrowedPercentage) + "%)");
        out.println("Missing Books: " + missingBooks + " (" + String.format("%.2f", missingPercentage) + "%)\n");

        String report = "Statistical Report:\n" +
                "\nTotal Books: " + totalBooks + "\n" +
                "Available Books: " + availableBooks + " (" + String.format("%.2f", availablePercentage) + "%)\n" +
                "Borrowed Books: " + borrowedBooks + " (" + String.format("%.2f", borrowedPercentage) + "%)\n" +
                "Missing Books: " + missingBooks + " (" + String.format("%.2f", missingPercentage) + "%)\n";

        String fileName = "statistical_report.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(report);
            System.out.println("Statistical report written to " + fileName + "\n");
        } catch (IOException e) {
            System.err.println("An error occurred while writing the report to a file: " + e.getMessage());
        }

        generalOptions();
    }

    public static void exit(){
        Library library = new Library();
        library.close();
        System.exit(0);
    }

}