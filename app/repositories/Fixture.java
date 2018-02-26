package repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.Book;
import models.User;
import play.Environment;
import play.inject.ApplicationLifecycle;
import play.libs.Json;

import java.io.IOException;
import java.util.List;

@Singleton
public class Fixture {

    // Inject the application's Environment upon start-up and register hook(s) for shut-down.
    @Inject
    public Fixture(ApplicationLifecycle alc, Environment env, Json j) throws IOException {

        ObjectMapper mapper = Json.mapper();
        List<User> users = UserRepository.find.all();
        if (users.isEmpty()) {
            List<User> defaultUsers = mapper.readValue(
                    env.getFile("conf/init/users.json"), new TypeReference<List<User>>() {});
            defaultUsers.stream().forEach(u -> u.save());
        }

        List<Book> books = Book.find.all();
        if (books.isEmpty()) {
            List<Book> defaultBooks = mapper.readValue(
                    env.getFile("conf/init/books.json"), new TypeReference<List<Book>>() {});
            defaultBooks.stream().forEach(b -> b.save());
        }
    }
}
