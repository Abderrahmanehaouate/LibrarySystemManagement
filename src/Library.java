import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


public class Library {
    Connection connection;
    public Library(){
        connection  = DatabaseConnection.connect();
    }
    public void addBook(Book book) {
        PreparedStatement preparedStatement = null;

        try {
            String insertQuery = "INSERT INTO books (title, author, ISBN) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getISBN());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("\n Book added successfully! \n");
            } else {
                System.out.println("\nFailed to add the book.\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String selectQuery = "SELECT * FROM books";
            preparedStatement = connection.prepareStatement(selectQuery);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String ISBN = resultSet.getString("ISBN");
                String status = resultSet.getString("status");
                Book book = new Book(title, author, ISBN, status);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public List<Book> getAvailableBooksByISBN(String ISBN) {
        List<Book> books = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String selectQuery = "SELECT * FROM Books WHERE ISBN = ? AND Status = 'available'";
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, ISBN);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String bookISBN = resultSet.getString("ISBN");
                String status = resultSet.getString("status");
                Book book = new Book(title, author, bookISBN, status);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    public List<Book> getBooksByISBN(String ISBN) {
        List<Book> books = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String selectQuery = "SELECT * FROM Books WHERE ISBN = ?";
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, ISBN);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String bookISBN = resultSet.getString("ISBN");
                String status = resultSet.getString("status");
                Book book = new Book(title, author, bookISBN, status);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    public List<Book> getBooksByStatus(String status) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Book> books = new ArrayList<>();

        try {
            String selectQuery = "SELECT * FROM books WHERE status = ?";
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, status);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("ISBN"),
                        resultSet.getString("status")
                );
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public List<BorrowedBook> getAllBorrowedBooks() {
        List<BorrowedBook> borrowedBooks = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            String sqlQuery = "SELECT B.title, B.author, B.ISBN, B.status, C.name, RB.reservationDate, RB.memberShip "
                    + "FROM Books AS B "
                    + "INNER JOIN ReservedBooks AS RB ON B.ISBN = RB.ISBN "
                    + "INNER JOIN Clients AS C ON RB.memberShip = C.Membership "
                    + "WHERE B.status = 'borrowed'";

            preparedStatement = connection.prepareStatement(sqlQuery);
            resultSet = preparedStatement.executeQuery();

             while (resultSet.next()) {
                 String bookTitle = resultSet.getString("title");
                 String bookAuthor = resultSet.getString("author");
                 String bookISBN = resultSet.getString("ISBN");
                 String bookStatus = resultSet.getString("status");
                 String clientName = resultSet.getString("name");
                 Date reserveDate = resultSet.getDate("reservationDate");
                 String membership = resultSet.getString("memberShip");

                 BorrowedBook reservedBook = new BorrowedBook(bookTitle, bookAuthor, bookISBN, bookStatus, clientName, reserveDate, membership);
                 borrowedBooks.add(reservedBook);
             }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return borrowedBooks;
    }

    public void deleteBook(String ISBN) {
        PreparedStatement preparedStatement = null;

        try {
            String deleteQuery = "DELETE FROM Books WHERE ISBN = ?";
            preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, ISBN);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("\nBook with ISBN " + ISBN + " has been deleted.\n");
            } else {
                System.out.println("\nNo book found with ISBN " + ISBN + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateAllInformationBook(Book book,String ISBN) {
        PreparedStatement preparedStatement = null;

        try {
            String updateQuery = "UPDATE Books SET title = ?, author = ?, ISBN = ? WHERE ISBN = ?";
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getISBN());
            preparedStatement.setString(4, ISBN);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("\n Book has been updated. \n");
            } else {
                System.out.println("\nNo book found. No changes were made.\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateTitle(Book book, String ISBN) {
        PreparedStatement preparedStatement = null;

        try {
            String updateQuery = "UPDATE Books SET title = ? WHERE ISBN = ?";
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, ISBN);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("\nTitle has been updated.\n");
            } else {
                System.out.println("\nNo book found. No changes were made.\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAuthor(Book book, String ISBN) {
        PreparedStatement preparedStatement = null;

        try {
            String updateQuery = "UPDATE Books SET author = ? WHERE ISBN = ?";
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, ISBN);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("\n Author has been updated. \n");
            } else {
                System.out.println("\n No book found. No changes were made. \n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateISBN(Book book, String updatedISBN) {
        PreparedStatement preparedStatement = null;

        try {
            String updateQuery = "UPDATE Books SET ISBN = ? WHERE ISBN = ?";
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, book.getISBN());
            preparedStatement.setString(2, updatedISBN);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("\n ISBN has been updated. \n");
            } else {
                System.out.println(" \n No changes were made. \n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateBookStatus(String ISBN, String status) {
        PreparedStatement preparedStatement = null;

        try {
            String updateQuery = "UPDATE Books SET status = ? WHERE ISBN = ?";
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, ISBN);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("\n Book status updated successfully.\n");
            } else {
                System.out.println("\n No book found with the provided ISBN. \n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Book> searchBook(String searchCriteria, String keyword) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Book> matchingBooks = new ArrayList<>();

        try {
            String searchQuery = "SELECT * FROM Books WHERE " + searchCriteria + " LIKE ?";
            preparedStatement = connection.prepareStatement(searchQuery);
            preparedStatement.setString(1, "%" + keyword + "%");

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String ISBN = resultSet.getString("ISBN");
                String status = resultSet.getString("status");

                Book book = new Book(title, author, ISBN, status);
                matchingBooks.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matchingBooks;
    }
    public void addClient(Client client) {
        PreparedStatement preparedStatement = null;

        try {
            String insertQuery = "INSERT INTO Clients (name, membership) VALUES (?, ?)";

            preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getMembership());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("You has been added.");
            } else {
                System.out.println("You could not be added.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void borrowBookFromClient(BorrowedBook borrowedBook) {
        PreparedStatement preparedStatement = null;

        try {
            String insertQuery = "INSERT INTO reservedbooks (ISBN, memberShip) VALUES (?,?)";
            preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, borrowedBook.getBookISBN());
            preparedStatement.setString(2, borrowedBook.getClientMemberShip());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("\n Book reserved successfully. \n");
            } else {
                System.out.println("\n Failed to reserve the book. \n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkMemberShip(String memberShip) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String selectQuery = "SELECT memberShip FROM Clients WHERE memberShip = ?";
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, memberShip);
            resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkIfClientAlreadyReservedBook(String memberShip){

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String selectQuery = "SELECT memberShip FROM reservedbooks WHERE memberShip = ?";
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, memberShip);
            resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean checkIsbnIfAlreadyExist(String ISBN) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String selectQuery = "SELECT ISBN FROM books WHERE ISBN = ?";
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, ISBN);
            resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean returnBook(String ISBN, String membershipCode) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String checkReservationQuery = "SELECT * FROM ReservedBooks WHERE ISBN = ? AND Membership = ?";
            preparedStatement = connection.prepareStatement(checkReservationQuery);
            preparedStatement.setString(1, ISBN);
            preparedStatement.setString(2, membershipCode);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                System.out.println("No reservation found for ISBN " + ISBN + " and Membership " + membershipCode);
                return false;
            }

            String updateStatusQuery = "UPDATE Books SET Status = 'Available' WHERE ISBN = ?";
            preparedStatement = connection.prepareStatement(updateStatusQuery);
            preparedStatement.setString(1, ISBN);
            preparedStatement.executeUpdate();

            String deleteReservationQuery = "DELETE FROM ReservedBooks WHERE ISBN = ? AND Membership = ?";
            preparedStatement = connection.prepareStatement(deleteReservationQuery);
            preparedStatement.setString(1, ISBN);
            preparedStatement.setString(2, membershipCode);
            preparedStatement.executeUpdate();

            System.out.println("\n Book with ISBN " + ISBN + " has been returned. \n");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public String getReservedBookISBN(String membershipCode) {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            String checkReservationQuery = "SELECT ISBN FROM ReservedBooks WHERE Membership = ?";
            preparedStatement = connection.prepareStatement(checkReservationQuery);
            preparedStatement.setString(1, membershipCode);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("ISBN");
            } else {
                System.out.println("No book reserved by client with Membership " + membershipCode);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void close(){
        DatabaseConnection.close();
    }

}

