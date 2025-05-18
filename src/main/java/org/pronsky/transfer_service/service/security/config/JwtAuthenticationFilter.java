package org.pronsky.transfer_service.service.security.config;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.pronsky.transfer_service.service.exception.SecurityException;
import org.pronsky.transfer_service.service.security.JwtSecurityService;
import org.pronsky.transfer_service.service.security.UserSecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
    private final JwtSecurityService jwtService;
    private final UserSecurityService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        var authHeader = request.getHeader(HEADER_NAME);
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        var jwt = authHeader.substring(BEARER_PREFIX.length());

        try {
            var username = jwtService.extractUserEmail(jwt);

            if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService
                        .userDetailsService()
                        .loadUserByUsername(username);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    SecurityContext context = SecurityContextHolder.createEmptyContext();

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    context.setAuthentication(authToken);
                    SecurityContextHolder.setContext(context);
                }
            }
        } catch (SecurityException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Access Denied: " + e.getMessage());
            return;
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("JWT token has expired: " + e.getMessage());
            return;
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Invalid JWT token: " + e.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }

}
