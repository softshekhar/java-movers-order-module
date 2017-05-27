package com.packers.movers.commons.contracts;

import com.packers.movers.commons.utils.JsonUtils;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Type;

public class DataContract<Contract> extends ContractBase {
    private final Contract data;

    public DataContract(Contract data) {
        this.data = data;
    }

    public static <Contract> DataContract<Contract> fromJson(String json, Class<Contract> targetClass) {
        Type type = ParameterizedTypeImpl.make(DataContract.class, new Type[] { targetClass }, null);
        return JsonUtils.deserialize(json, type);
    }

    public Contract getData() {
        return data;
    }
}
