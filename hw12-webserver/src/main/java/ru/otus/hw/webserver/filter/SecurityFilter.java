package ru.otus.hw.webserver.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class SecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("#CUSTOM FILTER");
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getUserPrincipal() != null) {
            log.info("Principal: {}", req.getUserPrincipal().getName());
        }
        chain.doFilter(request, response);
    }
}
