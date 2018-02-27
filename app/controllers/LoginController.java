package controllers;

import com.google.inject.Inject;
import models.User;
import org.pac4j.core.config.Config;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.http.client.indirect.FormClient;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import views.html.login;

public class LoginController extends GenericController {

    @Inject
    private Config config;

    @Inject
    private FormFactory formFactory;

    public Result login() throws TechnicalException {

        if (isLogged()) return redirect(routes.BooksController.index());  //already logged

        final FormClient formClient = (FormClient) config.getClients().findClient("FormClient");
        Form<User> loginForm = formFactory.form(User.class);
        //TODO ver si se puede reemplazar la callbackUrl de formClient ahora que tenemos el request en lugar de ponerlo en la inicialización en el SecurityModule
        // request().
        return ok(login.render(formClient.getCallbackUrl(), isLogged(), loginForm));
    }

}
