import java.util.Date;

public class BorrowedBook {
    private String bookTitle;
    private String bookAuthor;
    private String bookISBN;
    private String bookStatus;
    private String clientName;
    private String clientMemberShip;
    private Date reserveDate;


    public BorrowedBook(String bookISBN, String clientMemberShip){
        this.bookISBN = bookISBN;
        this.clientMemberShip = clientMemberShip;
    }

    public BorrowedBook(String bookTitle, String bookAuthor, String bookISBN, String bookStatus, String clientName, Date reserveDate, String clientMemberShip) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookISBN = bookISBN;
        this.bookStatus = bookStatus;
        this.clientName = clientName;
        this.reserveDate = reserveDate;
        this.clientMemberShip = clientMemberShip;
    }

    public String getClientMemberShip() {
        return clientMemberShip;
    }
    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public String getClientName() {
        return clientName;
    }

    public Date getReserveDate() {
        return reserveDate;
    }
}
