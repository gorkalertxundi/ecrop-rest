package eus.ecrop.api.dto;

import lombok.Data;

@Data
public class UserDto {
    
    private String name;

    private String imageUrl;

    private Boolean activeSubscription;

}
