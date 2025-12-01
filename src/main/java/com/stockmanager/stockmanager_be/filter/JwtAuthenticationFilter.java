package com.stockmanager.stockmanager_be.filter;

import com.stockmanager.stockmanager_be.service.CustomUserDetailsService;
import com.stockmanager.stockmanager_be.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JWTUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Get the authentication header
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        // Extract JWT and username
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7); // Token starts after "Bearer "
            try {
                username = jwtUtil.extractUsername(jwtToken);
            } catch (IllegalArgumentException e) {
                logger.error("Invalid JWT token: "+ e.getMessage());
            }
        }

        // Validate token and security context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // loading user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // check token validity
            if (jwtUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                // set the details
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // establishing the user in security context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }

        // continue the filter chain
        filterChain.doFilter(request, response);
    }
}
