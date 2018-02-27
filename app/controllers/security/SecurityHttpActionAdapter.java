package controllers.security;

import org.pac4j.core.context.HttpConstants;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.http.DefaultHttpActionAdapter;
import play.mvc.Result;
import views.html.errors._401;
import views.html.errors._403;

import static play.mvc.Results.forbidden;
import static play.mvc.Results.unauthorized;

public class SecurityHttpActionAdapter extends DefaultHttpActionAdapter {

    @Override
    public Result adapt(int code, PlayWebContext context) {
        Boolean isLogged = false;       //TODO get from context ( (List) ctx().args.get("profiles") ).isEmpty();

        if (code == HttpConstants.UNAUTHORIZED) {
            return unauthorized(_401.render(isLogged).toString()).as((HttpConstants.HTML_CONTENT_TYPE));
        } else if (code == HttpConstants.FORBIDDEN) {
            return forbidden(_403.render(isLogged).toString()).as((HttpConstants.HTML_CONTENT_TYPE));
        } else {
            return super.adapt(code, context);
        }
    }
}
