package controllers;

import controllers.security.CredentialsAction;
import org.pac4j.core.profile.CommonProfile;
import play.mvc.Controller;
import play.mvc.With;

import java.util.List;

@With(CredentialsAction.class)
public class GenericController extends Controller {

    protected boolean isLogged() {
        return !( (List) ctx().args.get("profiles") ).isEmpty();
    }

    protected List<CommonProfile> getRoles() {
        return (List<CommonProfile>) ctx().args.get("profiles");
    }
}
