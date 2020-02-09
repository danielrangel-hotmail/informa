package com.objective.informa.security;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityFacade {

	private String forceCurrentUserLogin;
	private String[] forceCurrentUserAuthorities;
	
	
	public void user(String user, String...authorities) {
		this.forceCurrentUserLogin = user;
		this.forceCurrentUserAuthorities = authorities;
	}
	
	public void resetUser() {
		this.forceCurrentUserLogin = null;
		this.forceCurrentUserAuthorities = null;
	}
	
	public Optional<String> getCurrentUserLogin() {
		if (forceCurrentUserLogin != null) return Optional.of(forceCurrentUserLogin);
		return getRealCurrentUserLogin();
	}
	
    private Optional<String> getRealCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .map(authentication -> {
                if (authentication.getPrincipal() instanceof UserDetails) {
                    UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                    return springSecurityUser.getUsername();
                } else if (authentication.getPrincipal() instanceof String) {
                    return (String) authentication.getPrincipal();
                }
                return null;
            });
    }

    /**
     * Get the JWT of the current user.
     *
     * @return the JWT of the current user.
     */
    public Optional<String> getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .filter(authentication -> authentication.getCredentials() instanceof String)
            .map(authentication -> (String) authentication.getCredentials());
    }

    public boolean isAuthenticated() {
    	return forceCurrentUserLogin != null || isRealAuthenticated();
    }
    
    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise.
     */
    private boolean isRealAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null &&
            getAuthorities(authentication).noneMatch(AuthoritiesConstants.ANONYMOUS::equals);
    }

    public boolean isCurrentUserInRole(String authority) {
    	if (forceCurrentUserAuthorities != null) 
    		return Arrays.stream(forceCurrentUserAuthorities).anyMatch(authority::equals);
    	return isRealCurrentUserInRole(authority);
    }
    
    /**
     * If the current user has a specific authority (security role).
     * <p>
     * The name of this method comes from the {@code isUserInRole()} method in the Servlet API.
     *
     * @param authority the authority to check.
     * @return true if the current user has the authority, false otherwise.
     */
    private boolean isRealCurrentUserInRole(String authority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null &&
            getAuthorities(authentication).anyMatch(authority::equals);
    }

    private static Stream<String> getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority);
    }

}
