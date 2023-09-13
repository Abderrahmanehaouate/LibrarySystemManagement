public class Book {
    private String title;
    private String author;
    private String ISBN;
    private String status;

    public Book(){
    }
    public Book(String title, String author, String ISBN, String status) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.status = status;
    }
    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public String setTitle(String title) {
        this.title = title;
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus(){
        return status;
    }

    public void setISBN(String ISBN){
        this.ISBN = ISBN;
    }
    public String getISBN() {
        return ISBN;
    }
}
