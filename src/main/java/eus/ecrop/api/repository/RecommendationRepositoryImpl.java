package eus.ecrop.api.repository;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import eus.ecrop.api.domain.Land;
import lombok.Getter;
import lombok.Setter;

/*
* @author Mikel Orobengoa
* @version 09/06/2022
*/

/*
* An implementation for the RecommendationRepository interface.
*/
@Repository
public class RecommendationRepositoryImpl implements RecommendationRepository {

    @Value("${server.api.ia.recommendation}")
    private String recommendationUrl;

    @Autowired
    private ObjectMapper objectMapper;

    public String getRecommendation(Land land) {
        if (land.getNitrogen() == null || land.getPhosphorus() == null || land.getPotassium() == null
                || land.getTemperature() == null || land.getHumidity() == null || land.getPH() == null
                || land.getRainfall() == null) {
            return null;
        }

        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(recommendationUrl);
        builder.queryParam("n", land.getNitrogen());
        builder.queryParam("p", land.getPhosphorus());
        builder.queryParam("k", land.getPotassium());
        builder.queryParam("temperature", land.getTemperature());
        builder.queryParam("humidity", land.getHumidity());
        builder.queryParam("ph", land.getPH());
        builder.queryParam("rainfall", land.getRainfall());
        builder.queryParam("api-key", "urko moxolo"); // TODO: sacar de user ?

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        try {
            System.out.println("Sending request to: " + builder.toUriString());
            ResponseEntity<?> result = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
                    String.class);
            System.out.println(result.getBody());
            RecommendationResponse res = objectMapper.readValue(result.getBody().toString(),
                    RecommendationResponse.class);
            System.out.println(res.getPrediction());
            return res.getPrediction();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Getter
    @Setter
    private static class RecommendationResponse {
        private String ok;
        private String prediction;
    }

}