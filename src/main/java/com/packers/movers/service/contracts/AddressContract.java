
package com.packers.movers.service.contracts;

import com.google.gson.annotations.SerializedName;
import com.packers.movers.commons.contracts.ContractBase;
import lombok.Data;

@Data
public class AddressContract extends ContractBase {

    @SerializedName("street_name")
    private String streetName;
    @SerializedName("postcode")
    private String postcode;
    @SerializedName("city")
    private String city;
    @SerializedName("country")
    private String country;

    @SerializedName("street_name")
    public String getStreetName() {
        return streetName;
    }

    @SerializedName("postcode")
    public String getPostcode() {
        return postcode;
    }

    @SerializedName("city")
    public String getCity() {
        return city;
    }

    @SerializedName("country")
    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return streetName + ", " + postcode + ", " + city + ", " + country;
    }

}
