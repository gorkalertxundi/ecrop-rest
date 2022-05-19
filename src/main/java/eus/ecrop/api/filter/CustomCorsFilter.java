package eus.ecrop.api.filter;

import java.io.IOException;

import javax.annotation.Priority;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Component
@Priority(Ordered.HIGHEST_PRECEDENCE)
public class CustomCorsFilter extends CorsFilter {

    private CorsConfigurationSource configSource;

    public CustomCorsFilter(CorsConfigurationSource configSource) {
        super(configSource);
        this.configSource = configSource;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        configSource.getCorsConfiguration(request).getAllowedOrigins().forEach(origin -> {
            response.setHeader("Access-Control-Allow-Origin", origin);
        });

        configSource.getCorsConfiguration(request).getExposedHeaders().forEach(header -> {
            response.setHeader("Access-Control-Expose-Headers", header);
        });

        response.setHeader("Access-Control-Allow-Credentials",
                configSource.getCorsConfiguration(request).getAllowCredentials().toString());

        if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
            configSource.getCorsConfiguration(request).getAllowedMethods().forEach(method -> {
                response.setHeader("Access-Control-Allow-Methods", method);
            });

            configSource.getCorsConfiguration(request).getAllowedHeaders().forEach(header -> {
                response.setHeader("Access-Control-Allow-Headers", header);
                response.setStatus(200);
            });
        }
        filterChain.doFilter(request, response);
    }

}