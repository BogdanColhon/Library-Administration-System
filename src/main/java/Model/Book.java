package Model;

public class Book {

    private String title;
    private String author;
    private String publishinghouse;
    private int date;
    private String category;

    public Book(String title, String author, String publishinghouse, int date, String category){
        this.title = title;
        this.author = author;
        this.publishinghouse = publishinghouse;
        this.date = date;
        this.category = category;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;
        if(!title.equals(book.title))   return false;
        if(!author.equals(book.author)) return false;
        if(!publishinghouse.equals(book.publishinghouse))   return false;
        if(date != book.date) return false;
        return category.equals(book.category);
    }

    @Override
    public String toString(){
        return "{\n"+"   title: "+this.title+"\n"+"   author: "+this.author+"\n"+"   publishinghouse: "+this.publishinghouse+"\n"+"   date: "+this.date+"\n"+"   category: "+this.category+"\n}\n";
    }
}
