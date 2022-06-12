package eus.ecrop.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* @author Mikel Orobengoa
* @version 06/06/2022
*/

/**
 * This class is a DTO (Data Transfer Object) that is used to transfer
 * subscription data between the client and the
 * server
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubscriptionDto {

    private String cardHolderName;

    private String cardNumber;

    private Integer cardExpiryMonth;

    private Integer cardExpiryYear;

    private String cardCvv;

}
