package controllers.security;

import com.google.inject.Inject;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.store.PlaySessionStore;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.util.List;
import java.util.concurrent.CompletionStage;

public class CredentialsAction extends Action.Simple{

    @Inject
    private PlaySessionStore playSessionStore;

    @Override
    public CompletionStage<Result> call(Http.Context ctx) {
        //TODO ver si se puede reemplazar la callbackUrl de formClient ahora que tenemos el request
        //en lugar de ponerlo en la inicialización en el SecurityModule
        ctx.args.put("profiles", getProfiles(ctx));
        return delegate.call(ctx);
    }

    public List<CommonProfile> getProfiles(Http.Context ctx) {
        final PlayWebContext context = new PlayWebContext(ctx, playSessionStore);
        final ProfileManager<CommonProfile> profileManager = new ProfileManager(context);
        return profileManager.getAll(true);
    }
}
