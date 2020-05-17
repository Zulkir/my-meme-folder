package com.mymemefolder.mmfgateway.home;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class RedirectToReactFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        var httpRequest = (HttpServletRequest)request;
        var uri = httpRequest.getRequestURI();

        if (uri.startsWith("/api")) {
            chain.doFilter(request, response);
            return;
        }

        if (uri.startsWith("/react")) {
            chain.doFilter(request, response);
            return;
        }

        request.getRequestDispatcher("/").forward(request, response);
    }
}
