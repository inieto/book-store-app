package repositories;

import io.ebean.Finder;
import models.User;

public class UserRepository  extends Finder<Long, User> {

    public static Finder<Long, User> find = new UserRepository();    //Cambiar Integer por UUID cuando funcione

    public UserRepository() {
        super(User.class);
    }

    public User findByUsername(String username) {
        return query().where()
                .like("username", username)
                .findOne();
    }

    public User findByUsernameAndPassword(String username, String password) {
        return query().where()
                .like("username", username)
                .and()
                .like("password", password)
                .findOne();
    }

    /* https://www.playframework.com/documentation/2.6.x/JavaEbean#Configuring-models

    // More complex task query
    List<Task> cocoTasks = Task.find.query().where()
        .ilike("name", "%coco%")
        .orderBy("dueDate asc")
        .setFirstRow(0)
        .setMaxRows(25)
        .findPagedList()
        .getList();
    */

}
