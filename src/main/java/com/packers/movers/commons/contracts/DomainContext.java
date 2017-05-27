package com.packers.movers.commons.contracts;


import com.packers.movers.commons.utils.JsonUtils;

import java.util.UUID;

public class DomainContext extends ContractBase {
    private final String name;
    private final UUID departmentId;
    private final UUID employeeId;
    private final UUID positionId;
    private final UUID roleId;
    private final UUID servicePractitionerId;
    private final UUID serviceProviderId;

    public DomainContext(
        String name,
        UUID departmentId,
        UUID employeeId,
        UUID positionId,
        UUID roleId,
        UUID servicePractitionerId,
        UUID serviceProviderId
    ) {
        this.name = name;
        this.departmentId = departmentId;
        this.employeeId = employeeId;
        this.positionId = positionId;
        this.roleId = roleId;
        this.servicePractitionerId = servicePractitionerId;
        this.serviceProviderId = serviceProviderId;
    }

    public static DomainContext fromJson(String json) {
        return JsonUtils.deserialize(json, DomainContext.class);
    }

    public String getName() {
        return name;
    }

    public UUID getDepartmentId() {
        return departmentId;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public UUID getPositionId() {
        return positionId;
    }

    public UUID getRoleId() {
        return roleId;
    }

    public UUID getServicePractitionerId() {
        return servicePractitionerId;
    }

    public UUID getServiceProviderId() {
        return serviceProviderId;
    }
}
