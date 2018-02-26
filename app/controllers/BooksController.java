package controllers;

import controllers.security.CredentialsAction;
import models.Book;
import org.pac4j.play.java.Secure;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import play.mvc.With;
import views.html.books.create;
import views.html.books.edit;
import views.html.books.index;
import views.html.books.show;
import views.html.errors._404;

import javax.inject.Inject;
import java.util.List;

@With(CredentialsAction.class)
public class BooksController extends GenericController {

    @Inject
    FormFactory formFactory;

    //for all books
    public Result index() {
        List<Book> books = Book.find.all();
        return ok(index.render(books, isLogged()));
    }

    @Secure(clients = "FormClient" /*, authorizers = "admin"*/)
    public Result create() {
        Form<Book> bookForm = formFactory.form(Book.class);
        return ok(create.render(bookForm, isLogged()));
    }

    @Secure(clients = "FormClient" /*, authorizers = "admin"*/)
    public Result save() {
        Form<Book> bookForm = formFactory.form(Book.class).bindFromRequest();
        if(bookForm.hasErrors()) {
            flash("danger", "Please correct the form below");
            return badRequest(create.render(bookForm, isLogged()));
        }
        Book book = bookForm.get();
        book.save();
        flash("success", "Book saved successfully");
        return redirect(routes.BooksController.index());
    }

    @Secure(clients = "FormClient"/*, authorizers = "admin"*/)
    public Result edit(Integer id) {
        Book book = Book.find.byId(id);
        if(book == null) {
            return notFound(_404.render(isLogged()));
        }
        Form<Book> bookForm = formFactory.form(Book.class).fill(book);
        return ok(edit.render(bookForm, isLogged()));
    }

    @Secure(clients = "FormClient" /*, authorizers = "admin"*/)
    public Result update() {
        Form<Book> bookForm = formFactory.form(Book.class).bindFromRequest();
        if(bookForm.hasErrors()) {
            flash("danger", "Please correct the form below");
            return badRequest();
        }
        Book book = bookForm.get();
        Book oldBook = Book.find.byId(book.id);
        if(oldBook == null) {
            flash("danger", "Book not found");
            return notFound();
        }
        oldBook.title = book.title;
        oldBook.author = book.author;
        oldBook.price = book.price;
        oldBook.update();

        flash("success", "Book updated successfully");
        return ok();
    }

    //for book details
    public Result show(Integer id) {
        Book book = Book.find.byId(id);
        if(book == null) {
            return notFound(_404.render(isLogged()));
        }

        return ok(show.render(isLogged(), book));
    }


    @Secure(clients = "FormClient", authorizers = "admin")
    public Result destroy(Integer id) {
        Book book = Book.find.byId(id);
        if(book == null) {
            flash("danger", "Book Not Found");
            return notFound();
        }
        book.delete();
        flash("success", "Book Deleted Successfully");
        return ok();
    }
}

