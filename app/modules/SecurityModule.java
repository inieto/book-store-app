package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import controllers.LoginController;
import controllers.security.SecurityHttpActionAdapter;
import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.client.direct.AnonymousClient;
import org.pac4j.core.config.Config;
import org.pac4j.http.client.indirect.FormClient;
import org.pac4j.play.CallbackController;
import org.pac4j.play.LogoutController;
import org.pac4j.play.deadbolt2.Pac4jHandlerCache;
import be.objectify.deadbolt.java.cache.HandlerCache;
import org.pac4j.play.deadbolt2.Pac4jRoleHandler;
import org.pac4j.play.store.PlayCacheSessionStore;
import org.pac4j.play.store.PlaySessionStore;
import play.Environment;
import play.cache.SyncCacheApi;

public class SecurityModule extends AbstractModule {

    private final com.typesafe.config.Config configuration;

    private final String baseUrl;

    private static class MyPac4jRoleHandler implements Pac4jRoleHandler { }

    public SecurityModule(final Environment environment, final com.typesafe.config.Config configuration) {
        this.configuration = configuration;
        this.baseUrl = configuration.getString("baseUrl");
    }

    @Override
    protected void configure() {
        bind(HandlerCache.class).to(Pac4jHandlerCache.class);
        bind(Pac4jRoleHandler.class).to(MyPac4jRoleHandler.class);
        final PlayCacheSessionStore playCacheSessionStore = new PlayCacheSessionStore(getProvider(SyncCacheApi.class));
        bind(PlaySessionStore.class).to(PlayCacheSessionStore.class);

        // callback
        final CallbackController callbackController = new CallbackController();
        callbackController.setDefaultUrl("/");
        callbackController.setMultiProfile(true);
        bind(CallbackController.class).toInstance(callbackController);

        // logout
        final LogoutController logoutController = new LogoutController();
        logoutController.setDefaultUrl("/?defaulturlafterlogout");
        bind(LogoutController.class).toInstance(logoutController);
    }

    @Provides
    protected FormClient provideFormClient() {
        return new FormClient(baseUrl + "/login", new LoginController());
    }

    @Provides
    protected Config provideConfig(FormClient formClient) {
        final Clients clients = new Clients(baseUrl + "/callback", formClient, new AnonymousClient());

        final Config config = new Config(clients);
        config.addAuthorizer("admin", new RequireAnyRoleAuthorizer("ROLE_ADMIN"));
        //config.addAuthorizer("custom", new CustomAuthorizer());
        //config.addMatcher("excludedPath", new PathMatcher().excludeRegex("^/facebook/notprotected\\.html$"));
        config.setHttpActionAdapter(new SecurityHttpActionAdapter());
        return config;
    }
}
