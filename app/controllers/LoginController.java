package controllers;

import com.google.inject.Inject;
import io.ebean.Ebean;
import models.User;
import org.pac4j.core.config.Config;
import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.http.client.indirect.FormClient;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import repositories.UserRepository;
import views.html.login;

public class LoginController extends GenericController implements Authenticator<UsernamePasswordCredentials> {

    @Inject
    private Config config;

    @Inject
    FormFactory formFactory;

    @Inject
    UserRepository userRepository;

    public Result login() throws TechnicalException {

        if (isLogged()) return redirect(routes.BooksController.index());  //already logged

        final FormClient formClient = (FormClient) config.getClients().findClient("FormClient");
        Form<User> loginForm = formFactory.form(User.class);
        //TODO ver si se puede reemplazar la callbackUrl de formClient ahora que tenemos el request en lugar de ponerlo en la inicialización en el SecurityModule
        return ok(login.render(formClient.getCallbackUrl(), isLogged(), loginForm));
    }

    @Override
    public void validate(UsernamePasswordCredentials credentials, WebContext context) throws HttpAction, CredentialsException {
        if (credentials == null) {
            throwsException("No credential");
        }
        String username = credentials.getUsername();
        String password = credentials.getPassword();
        if (CommonHelper.isBlank(username)) {
            throwsException("Username cannot be blank");
        }
        if (CommonHelper.isBlank(password)) {
            throwsException("Password cannot be blank");
        }
        //if(userRepository.findByUsernameAndPassword(username, password) == null) {
        if(Ebean.find(User.class).where().like("username", username).and().like("password", password).findOne() == null){
            throwsException("Invalid credentials");
        }

        final CommonProfile profile = new CommonProfile();
        profile.addRole("ROLE_ADMIN");   //este admin coincide con el de SecurityModule:L64
        //luego, cada @Secure(autorizer="admin") deberá coincidir con el "admin" de SecurityModule:L64
        profile.setId(username);
        profile.addAttribute(Pac4jConstants.USERNAME, username);
        credentials.setUserProfile(profile);
    }

    protected void throwsException(final String message) throws CredentialsException {
        throw new CredentialsException(message);
    }

}
