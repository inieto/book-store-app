package controllers.security;

import org.pac4j.core.context.HttpConstants;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.http.DefaultHttpActionAdapter;
import play.mvc.Result;
import views.html.errors.*;

import java.util.List;

import static play.mvc.Results.*;

public class SecurityHttpActionAdapter extends DefaultHttpActionAdapter {

    @Override
    public Result adapt(int code, PlayWebContext context) {
        Boolean isLogged = false;
        //(List) ctx().args.get("profiles") ).isEmpty();
        if (code == HttpConstants.UNAUTHORIZED) {
            return unauthorized(_401.render(isLogged).toString()).as((HttpConstants.HTML_CONTENT_TYPE));
        } else if (code == HttpConstants.FORBIDDEN) {
            return forbidden(_403.render(isLogged).toString()).as((HttpConstants.HTML_CONTENT_TYPE));
        } else {
            return super.adapt(code, context);
        }
    }
}
