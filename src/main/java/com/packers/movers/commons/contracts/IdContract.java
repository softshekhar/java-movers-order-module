package com.packers.movers.commons.contracts;


import com.packers.movers.commons.utils.JsonUtils;

import java.util.UUID;

public class IdContract extends ContractBase {

    private final String id;

    public IdContract(String id) {
        this.id = id;
    }

    public IdContract(UUID id) {
        this.id = id.toString();
    }

    public String getId() {
        return id;
    }

    public static IdContract fromJson(String json) {
        return JsonUtils.deserialize(json, IdContract.class);
    }
}
