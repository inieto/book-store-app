package controllers.security;

import com.google.inject.Inject;
import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.util.CommonHelper;
import repositories.UserRepository;

public class UserAuthenticator implements Authenticator<UsernamePasswordCredentials> {

    @Inject
    private UserRepository userRepository;

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
        if(userRepository.findByUsernameAndPassword(username, password) == null) {
            throwsException("Invalid credentials");
        }

        final CommonProfile profile = new CommonProfile();
        profile.addRole("ROLE_ADMIN");   //este admin coincide con el de SecurityModule:L64
        //luego, cada @Secure(autorizer="admin") deberá coincidir con el "admin" de SecurityModule:L64
        profile.setId(username);
        profile.addAttribute(Pac4jConstants.USERNAME, username);
        credentials.setUserProfile(profile);
    }

    private void throwsException(final String message) throws CredentialsException {
        throw new CredentialsException(message);
    }
}
