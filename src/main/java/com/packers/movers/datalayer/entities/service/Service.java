package com.packers.movers.datalayer.entities.service;

import com.packers.movers.datalayer.entities.order.Order;

import javax.persistence.*;

@Entity
//@Table(name = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long service_id;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "service_type")
    private String serviceType;

    @Column(name = "package_quantity")
    private int packetsQuantity;

    @Column(name = "service_cost")
    private Float serviceCost;

    @Column(name = "status")
    private String status;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    public Service(
        String createdAt,
        String updatedAt,
        String createdBy,
        String updatedBy,
        Order order,
        String serviceType,
        int packetsQuantity,
        Float serviceCost,
        String status,
        String startDate,
        String endDate
    ) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.order = order;
        this.serviceType = serviceType;
        this.packetsQuantity = packetsQuantity;
        this.serviceCost = serviceCost;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getServiceId() {
        return service_id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Order getOrder() {
        return order;
    }

    public String getServiceType() {
        return serviceType;
    }

    public int getPacketsQuantity() {
        return packetsQuantity;
    }

    public Float getServiceCost() {
        return serviceCost;
    }

    public String getStatus() {
        return status;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
