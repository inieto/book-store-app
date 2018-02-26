package modules;

import com.google.inject.AbstractModule;
import repositories.Fixture;

public class StartupModule extends AbstractModule {

    @Override
    public void configure() {
        bind(Fixture.class).asEagerSingleton();
    }

}
