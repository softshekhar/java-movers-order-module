package com.packers.movers.datalayer.entities.order;

import com.packers.movers.datalayer.entities.customer.Customer;
import com.packers.movers.datalayer.entities.service.Service;

import javax.persistence.*;
import java.util.Set;

@Entity
//@Table(name = "order_placed")
public class Order {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long order_id;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    private Customer customer;

    private Set<Service> services;

    @Column(name = "from_address")
    private String fromAddress;

    @Column(name = "to_address")
    private String toAddress;

    @Column(name = "special_note")
    private String specialNote;

    @Column(name = "status")
    private String status;

    @Column(name = "total_cost")
    private Float totalCost;

    public Order (
        String createdAt,
        String updatedAt,
        String createdBy,
        String updatedBy,
        Customer customer,
        String fromAddress,
        String toAddress,
        String status,
        Float totalCost
    ) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.customer = customer;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.status = status;
        this.totalCost = totalCost;
    }

    public Order (
        String createdAt,
        String updatedAt,
        String createdBy,
        String updatedBy,
        Customer customer,
        String fromAddress,
        String toAddress,
        String specialNote,
        String status,
        Float totalCost
    ) {
        this(
            createdAt,
            updatedAt,
            createdBy,
            updatedBy,
            customer,
            fromAddress,
            toAddress,
            status,
            totalCost
        );

        this.specialNote = specialNote;
    }

    public Long getOrderId() {
        return order_id;
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

    @OneToOne(mappedBy = "customer_id", cascade = CascadeType.ALL)
    public Customer getCustomer() {
        return customer;
    }

    @OneToMany(mappedBy = "service_id", cascade = CascadeType.ALL)
    public Set<Service> getServices() {
        return services;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public String getSpecialNote() {
        return specialNote;
    }

    public void setSpecialNote(String specialNote) {
        this.specialNote = specialNote;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getTotalCost() {
        return totalCost;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setServices(Set<Service> services) {
        this.services = services;
    }

    public void setTotalCost(Float totalCost) {
        this.totalCost = totalCost;
    }
}
