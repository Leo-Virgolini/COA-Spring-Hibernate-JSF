package ar.com.leo.filter;

/**
 *
 * @author Leo
 */
import ar.com.leo.coa.mb.SessionManagedBean;
import ar.com.leo.coa.model.Usuario;
import ar.com.leo.coa.service.LoginService;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import javax.faces.bean.ManagedProperty;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
//
//        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
//        if (roles.contains("ROLE_ADMIN")) {
//            httpServletResponse.sendRedirect("admin/home.html");
//        }
//        //
//
//        //do some logic here if you want something to be done whenever
//        //the user successfully logs in.
//        HttpSession session = httpServletRequest.getSession();
//        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        session.setAttribute("username", authUser.getUsername());
//        session.setAttribute("authorities", authentication.getAuthorities());
//
//        //set our response to OK status
//        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
//
//        //since we have created our custom success handler, its up to us to where
//        //we will redirect the user after successfully login
//        httpServletResponse.sendRedirect("/admin/homeAdmin.xhtml");
//
//    }
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            System.out.println("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
        //perform other required operation
        Boolean login = true;
        HttpSession session = request.getSession();
        session.setAttribute("login", login);

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(Authentication authentication) {

        boolean isAdmin = false;
        boolean isAlumno = false;
        boolean isProfesor = false;
        boolean isEscuelaSede = false;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority grantedAuthority : authorities) {
            switch (grantedAuthority.getAuthority()) {
                case "ROLE_ALUMNO":
                    isAlumno = true;
                    break;
                case "ROLE_PROFESOR":
                    isProfesor = true;
                    break;
                case "ROLE_ESCUELA_SEDE":
                    isEscuelaSede = true;
                    break;
                case "ROLE_ADMIN":
                    isAdmin = true;
                    break;
            }
        }

        if (isAlumno) {
            return "/alumno/homeAlumno.xhtml";
        } else if (isProfesor) {
            return "/profesor/homeProfesor.xhtml";
        } else if (isEscuelaSede) {
            return "/escuelaSede/homeEscuelaSede.xhtml";
        } else if (isAdmin) {
            return "/admin/homeAdmin.xhtml";
        } else {
            throw new IllegalStateException();
        }
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
}
