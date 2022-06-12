package eus.ecrop.api.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper=false)
@Data
public class LandDto implements ValidationGroup {

    @Null(groups = { Create.class })
    @NotNull(groups = { Update.class })
    private Long id;

    @NotEmpty(message = "The name is required", groups = { Create.class, Update.class })
    private String name;

    private Integer nitrogen;

    private Integer phosphorus;

    private Integer potassium;

    private Double temperature;

    private Double humidity;

    private Double pH;

    private Double rainfall;

    private String recommendation;

}
