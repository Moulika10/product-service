package com.moulika.platform.productservice.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
    private JwtTokenVerifier jwtTokenVerifier;

    JwtAuthenticationTokenFilter(JwtTokenVerifier jwtTokenVerifier) {
        this.jwtTokenVerifier = jwtTokenVerifier;
    }

    /**
     * Custom override of filtering method that makes sure the JWT Token is part of the request's header
     *
     * @param request  is the httpServletRequest
     * @param response is the httpServletResponse.
     * @throws IOException      when found.
     * @throws ServletException when found.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = request;
        HttpServletResponse httpServletResponse = response;
        ObjectMapper mapper = new ObjectMapper();

            if(httpServletRequest.getHeader("Authorization")!= null ){
            try {
                String tokenHeader = "Authorization";
                String header = httpServletRequest.getHeader(tokenHeader);
                logger.debug("jwtHeader: " + tokenHeader + ", header = " + header);

                if (header != null && header.startsWith("Bearer ")) {
                    String authToken = header.substring(7);
                    DecodedJWT decodedJWT = jwtTokenVerifier.decodeToken(authToken);
                    logger.debug("KeyId: " + decodedJWT.getKeyId());
                    DecodedJWT verifiedJWT = jwtTokenVerifier.verify(authToken);
                    logger.debug("KeyId: " + verifiedJWT.getKeyId());
                    List<String> scopes = verifiedJWT.getClaim("scope").asList(String.class);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        "product-service", null, scopes.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                printExceptionAsJsonResponse(httpServletResponse, mapper);
            }
        }
        else {
                printExceptionAsJsonResponse(httpServletResponse, mapper);
            }
        // Continue on with the filtering within the chain
        filterChain.doFilter(request, response);
    }

    private void printExceptionAsJsonResponse(HttpServletResponse httpServletResponse,
        ObjectMapper mapper) throws IOException {
        httpServletResponse.setStatus(401);
        httpServletResponse.setContentType("application/json");
        ServletOutputStream out = httpServletResponse.getOutputStream();
        Map<String, Object> map = new HashMap<>();
        map.put("message", "unable to allow this request");
        out.print(mapper.writeValueAsString(map));
        out.close();
    }
}
