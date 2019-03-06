//package com.moulika.platform.productservice.security;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.mockito.Mockito.when;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import java.io.IOException;
//import javax.servlet.ServletException;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.mock.web.MockFilterChain;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.mock.web.MockHttpServletResponse;
//
//@RunWith(MockitoJUnitRunner.Silent.class)
//public class JwtAuthenticationTokenFilterTest {
//
//    private static final String TOKEN = "Authorization";
//    private static final String authToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImM1ZmI3Mjk5LWI4MjktNGYxOC1hMTdlLWVhMmY5MDAwMWVjZSJ9.eyJhdWQiOlsicHJvZHVjdC1zZXJ2aWNlIl0sInNjb3BlIjpbIm9wZW5pZCJdLCJpc3MiOiJhdXRoLWxvY2FsIiwiZXhwIjoxNTM2ODE3NDI2LCJqdGkiOiIwMjIyYjE1Yy1hOTMyLTQzMGUtOGYwNy1iOGQ5NmFiNDE0ODUiLCJjbGllbnRfaWQiOiJwcm9kdWN0LXNlcnZpY2UifQ.NG3wP6ZEQ30fFnovAitJf6alK-m-VGxI-exPqvPYlvIi5SfHXLPw-JUTnatW3LzrhJZDoeVPgy3m_WOFcCjLXnwK-R36OFgd9nCKABH_7qwmmMjFFSjG--Z5myjEhwJgwLOOyC4-4oYU8Z64msdHn9G2t1X8T2BfcfhdZYYOwZfXixpM4LyXE1WiLdKzlx4gKiYltPkTgj7Ea1rGB2STnMT8vnUsmf8OSYkx76M7zhBu658zumdlfVCqQlbsUTUPXawkDK6lZ49C4lfkyz1xfvYHmdTvWCuTo6npuC8J_C5hGQDwWPtg2MdmXYn3H5Yf-8_gRNGYuj4TexrYRp3MZw";
//    private static final String token = "Bearer " + authToken;
//    private String testUri;
//
//    @Mock
//    private JwtTokenVerifier jwtTokenVerifier;
//
//    @InjectMocks
//    private JwtAuthenticationTokenFilter tokenAuthenticationFilter;
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        testUri = "http://localhost:8114/v1/productSegment";
//    }
//
//    @Test
//    public void doFilterWhenUnAuthorized() throws ServletException, IOException {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        request.addHeader(TOKEN, token);
//        request.setRequestURI(testUri);
//        MockHttpServletResponse response = new MockHttpServletResponse();
//        MockFilterChain filterChain = new MockFilterChain();
//        DecodedJWT jwt = JWT.decode(authToken);
//        when(jwtTokenVerifier.decodeToken(authToken)).thenReturn(jwt);
//        tokenAuthenticationFilter.doFilter(request, response, filterChain);
//        assertEquals(response.getStatus(), 401);
//    }
//
//    @Test
//    public void doFilterWhenNoAuthorizationHeader() throws IOException, ServletException {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        MockHttpServletResponse response = new MockHttpServletResponse();
//        MockFilterChain filterChain = new MockFilterChain();
//        tokenAuthenticationFilter.doFilter(request, response, filterChain);
//        assertEquals(response.getStatus(), 401);
//        assertFalse(response.containsHeader("Authorization"));
//    }
//}
