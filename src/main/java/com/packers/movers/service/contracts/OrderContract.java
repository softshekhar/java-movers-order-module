
package com.packers.movers.service.contracts;

import com.google.gson.annotations.SerializedName;
import com.packers.movers.commons.contracts.ContractBase;
import com.packers.movers.commons.contracts.validation.Optional;
import lombok.Data;

import java.util.List;

@Data
public class OrderContract extends ContractBase {

    private String name;
    private String phone;
    private String email;

    @SerializedName("special_notes")
    @Optional
    private String specialNotes;

    @SerializedName("from_address")
    private AddressContract fromAddress;
    @SerializedName("to_address")
    private AddressContract toAddress;
    private List<ServiceContract> services;
    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    @SerializedName("special_notes")
    public String getSpecialNotes() {
        return specialNotes;
    }

    @SerializedName("from_address")
    public AddressContract getFromAddress() {
        return fromAddress;
    }

    @SerializedName("to_address")
    public AddressContract getToAddress() {
        return toAddress;
    }

    public List<ServiceContract> getServices() {
        return services;
    }

}
