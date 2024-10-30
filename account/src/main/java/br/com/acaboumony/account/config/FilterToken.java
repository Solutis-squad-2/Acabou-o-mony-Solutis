package br.com.acaboumony.account.config;

import br.com.acaboumony.account.repository.AccountRepository;
import br.com.acaboumony.account.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FilterToken extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private AccountRepository accountRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        System.out.println("Request Path: " + path);

        /*if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-resources") || path.startsWith("/webjars") ||
                path.startsWith("/account/login") || path.startsWith("/account/register")
                || path.startsWith("/account/confirmar-codigo")) {
            filterChain.doFilter(request, response);
            return;
        }*/
        if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-resources") || path.startsWith("/webjars") ||
                path.startsWith("/account/login") || path.startsWith("/account/register")
                || path.startsWith("/account/confirmar-codigo") || path.startsWith("/account/swagger-ui") || path.startsWith("/account/v3/api-docs") ||
                path.startsWith("/account/swagger-resources") || path.startsWith("/account/webjars") ||
                path.startsWith("/account/account/login") || path.startsWith("/account/account/register")
                || path.startsWith("/account/account/confirmar-codigo")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authorizationHeader);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.replace("Bearer ", "");
            System.out.println("Token: " + token);
            String subject = this.tokenService.getSubject(token);

            System.out.println("Subject: " + subject);
            if (subject != null && !subject.isEmpty()) {
                var accountOptional = accountRepository.findByEmail(subject);

                if (accountOptional.isPresent()) {
                    var account = accountOptional.get();
                    var authentication = new UsernamePasswordAuthenticationToken(account, null, account.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                    return;
                }
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }

        filterChain.doFilter(request, response);
    }

}
