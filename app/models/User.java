package models;

import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
//import java.util.UUID;

@Entity
public class User extends Model {

    @Id
    public Long id;    //Cambiar Integer por UUID cuando funcione

    @Constraints.MinLength(5)
    @Constraints.MaxLength(50)
    @Constraints.Required
    @Constraints.Email
    public String username; //PAC4J limits us to name it to "username" and not i.e.: "email"

    @Constraints.Min(5)
    @Constraints.Max(100)
    @Constraints.Required
    public String password;

    @Constraints.MinLength(5)
    @Constraints.MaxLength(255)
    public String fullname;

    public User(){}

    public User(Long id, String username, String password, String fullname) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
    }

}
