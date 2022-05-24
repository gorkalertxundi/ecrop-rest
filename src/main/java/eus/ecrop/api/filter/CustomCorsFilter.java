package eus.ecrop.api.filter;

import java.io.IOException;
import java.util.List;

import javax.annotation.Priority;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/*
* @author Mikel Orobengoa
* @version 19/05/2022
*/

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
        CorsConfiguration corsConfiguration = configSource.getCorsConfiguration(request);
        if (corsConfiguration == null)
            return;

        List<String> allowedOrigins = corsConfiguration.getAllowedOrigins();
        if (allowedOrigins != null)
            allowedOrigins.forEach(origin -> response.setHeader("Access-Control-Allow-Origin", origin));

        List<String> exposedHeaders = corsConfiguration.getExposedHeaders();
        if (exposedHeaders != null)
            exposedHeaders.forEach(header -> response.setHeader("Access-Control-Expose-Headers", header));

        Boolean allowedCredentials = corsConfiguration.getAllowCredentials();
        if (allowedCredentials != null)
            response.setHeader("Access-Control-Allow-Credentials",
                    allowedCredentials.toString());

        if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
            List<String> allowedMethods = corsConfiguration.getAllowedMethods();
            if (allowedMethods != null)
                allowedMethods.forEach(method -> response.setHeader("Access-Control-Allow-Methods", method));
            List<String> allowedHeaders = corsConfiguration.getAllowedHeaders();
            if (allowedHeaders != null)
                allowedHeaders.forEach(header -> {
                    response.setHeader("Access-Control-Allow-Headers", header);
                    response.setStatus(200);
                });
        }
        filterChain.doFilter(request, response);
    }

}