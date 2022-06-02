package eus.ecrop.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* @author Mikel Orobengoa
* @version 26/05/2022
*/

/**
 * This class is a DTO (Data Transfer Object) that is used to transfer data between the client and the
 * server
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LandDto {

    private Long id;

    // @NotEmpty(message = "The name is required")
    private String name;

    private Integer nitrogen;

    private Integer phosphorus;

    private Integer potassium;

    private Double temperature;

    private Double humidity;

    private Double pH;

    private Double rainfall;

}
