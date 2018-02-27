package filters;

import com.google.inject.Inject;
import org.pac4j.play.filters.SecurityFilter;
import play.http.HttpFilters;
import play.mvc.EssentialAction;
import play.mvc.EssentialFilter;

import java.util.Arrays;
import java.util.List;

public class Filters extends EssentialFilter implements HttpFilters {

    private final SecurityFilter securityFilter;

    @Inject
    public Filters(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Override
    public List<EssentialFilter> getFilters() {
        return Arrays.asList(securityFilter.asJava());
    }

    @Override
    public EssentialAction apply(EssentialAction next) {
        return securityFilter.asJava().apply(next);
    }
}
