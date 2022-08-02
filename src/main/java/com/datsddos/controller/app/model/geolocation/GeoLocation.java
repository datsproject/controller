package com.datsddos.controller.app.model.geolocation;

import lombok.*;

@EqualsAndHashCode
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GeoLocation {

    private String countryCode;
    private String countryName;
    private String postalCode;
    private String city;
    private String region;
    private int areaCode;
    private int dmaCode;
    private int metroCode;
    private float latitude;
    private float longitude;
}