package com.packers.movers.commons.contracts;

import com.packers.movers.commons.utils.JsonUtils;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Type;
import java.util.List;

public class ListContract<Contract> extends ContractBase {
    private final List<Contract> data;

    public ListContract(List<Contract> data) {
        this.data = data;
    }

    public static <Contract> ListContract<Contract> fromJson(String json, Class<Contract> targetClass) {
        Type type = ParameterizedTypeImpl.make(ListContract.class, new Type[] { targetClass }, null);
        return JsonUtils.deserialize(json, type);
    }

    public List<Contract> getData() {
        return data;
    }
}