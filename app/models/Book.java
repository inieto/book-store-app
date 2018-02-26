package models;

import java.util.HashSet;
import java.util.Set;

import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Book extends Model {

    @Id
    @Constraints.Required
    public Integer id;
    @Constraints.MinLength(5)
    @Constraints.MaxLength(255)
    @Constraints.Required
    public String title;
    @Constraints.Required
    @Constraints.Min(5)
    @Constraints.Max(100)
    public Integer price;
    @Constraints.Required
    public String author;

    public Book(){}

    public Book(Integer id, String title, Integer price, String author) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.author = author;
    }

    public static Finder<Integer, Book> find = new Finder<>(Book.class);    //Integer es el tipo de dato de la PK (@id)

    /** IMPLEMENTACIÓN EN MEMORIA
    private static Set<Book> books;

    static {
        books = new HashSet<>();
        books.add(new Book(1, "C++", 20, "ABC"));
        books.add(new Book(2, "Java", 30, "XYZ"));
    }


    public static Set<Book> allBooks (){
        return books;
    }

    public static Book findById(Integer id){
        for(Book book: books) {
            if(id.equals(book.id)) {
                return book;
            }
        }
        return null;
    }

    public static void add(Book book) {
        books.add(book);
    }

    public static boolean remove(Book book) {
        return books.remove(book);
    }
    */

}
