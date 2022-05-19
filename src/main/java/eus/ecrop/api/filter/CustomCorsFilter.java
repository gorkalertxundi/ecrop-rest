package eus.ecrop.api.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Component
@Primary
public class CustomCorsFilter extends CorsFilter {

    private CorsConfigurationSource configSource;

    public CustomCorsFilter(CorsConfigurationSource configSource) {
        super(configSource);
        this.configSource = configSource;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // configSource.getCorsConfiguration(request).getAllowedOrigins().forEach(origin -> {
        //     response.setHeader("Access-Control-Allow-Origin", origin);
        // });

        // configSource.getCorsConfiguration(request).getAllowedMethods().forEach(method -> {
        //     response.setHeader("Access-Control-Allow-Methods", method);
        // });

        // configSource.getCorsConfiguration(request).getAllowedHeaders().forEach(header -> {
        //     response.setHeader("Access-Control-Allow-Headers", header);
        // });

        // configSource.getCorsConfiguration(request).getExposedHeaders().forEach(header -> {
        //     response.setHeader("Access-Control-Expose-Headers", header);
        // });

        final String origin = request.getHeader("Origin");

        if (origin != null && origin.equals("http://localhost")) {
            response.addHeader("Access-Control-Allow-Origin", "http://localhost");
            response.addHeader("Access-Control-Allow-Credentials", "true");

            if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
                response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                response.addHeader("Access-Control-Allow-Headers", "X-Requested-With,Origin,Content-Type,Accept,Authorization");
                response.setStatus(200);
            }
        }
        filterChain.doFilter(request, response);
        
    }
}