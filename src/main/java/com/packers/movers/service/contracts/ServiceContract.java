
package com.packers.movers.service.contracts;

import com.google.gson.annotations.SerializedName;
import com.packers.movers.commons.contracts.ContractBase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ServiceContract extends ContractBase {

    private String type;
    @SerializedName("packets_quantity")
    private int packetsQuantity;
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("cost_per_packet")
    private int costPerPacket;

    public String getType() {
        return type;
    }

    @SerializedName("packets_quantity")
    public int getPacketsQuantity() {
        return packetsQuantity;
    }

    @SerializedName("start_date")
    public String getStartDate() {
        return startDate;
    }

    @SerializedName("cost_per_packet")
    public int getCostPerPacket() {
        return costPerPacket;
    }
}
