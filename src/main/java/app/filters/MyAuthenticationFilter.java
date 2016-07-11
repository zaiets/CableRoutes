package app.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import app.security.MyAuthenticationService;
import app.security.MyAuthenticationServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationFilter extends GenericFilterBean {

    @Autowired
    private MyAuthenticationService authenticationService = new MyAuthenticationServiceImpl();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        boolean authenticated = false;
        String token = httpRequest.getHeader("X-Auth-Token");
        if (token == null) {
            authenticated = false;
        }
        if (authenticationService.checkToken(token)) {

            //TODO else?

            authenticated = true;
        }
        if (!authenticated) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
