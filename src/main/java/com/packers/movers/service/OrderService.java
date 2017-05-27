package com.packers.movers.service;

import com.packers.movers.commons.constants.ServiceType;
import com.packers.movers.commons.constants.StatusType;
import com.packers.movers.commons.utils.Sequence;
import com.packers.movers.datalayer.entities.JPAConfig;
import com.packers.movers.datalayer.entities.customer.Customer;
import com.packers.movers.datalayer.entities.order.Order;
import com.packers.movers.datalayer.entities.order.OrderRepository;
import com.packers.movers.datalayer.entities.service.Service;
import com.packers.movers.service.contracts.OrderContract;
import com.packers.movers.service.contracts.ServiceContract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@EnableJpaRepositories(basePackages = "com.packers.movers.datalayer.entities")
@Import(JPAConfig.class)
@Transactional
public class OrderService {
    private static final Logger LOG = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SingleConnectionDataSource dataSource;

    public Long placeOrder(OrderContract orderContract) {

        Instant current = Instant.now();
        String personName = "John"; // TODO - Explore how to populate it during the runtime.
        String customerName = orderContract.getName();
        String phone = orderContract.getPhone();
        String email = orderContract.getEmail();

        Customer customer = new Customer(current.toString(), current.toString(), personName, personName, customerName, phone, email);

        UUID orderId = UUID.randomUUID();
        String fromAddress = orderContract.getFromAddress().toString();
        String toAddress = orderContract.getToAddress().toString();
        String status = StatusType.Created.name();

        List<ServiceType> serviceTypes = Sequence.of(ServiceType.values()).toList();

        Float totalOrderCost = Float.valueOf("0");

        Set<Service> services = new HashSet<>();

        Order order = new Order(current.toString(), current.toString(), personName, personName, customer, fromAddress, toAddress, status, totalOrderCost);

        for (ServiceType type : serviceTypes) {
            ServiceContract serviceContract = Sequence.of(orderContract.getServices()).first(element -> ServiceType.parse(element.getType()).equals(type));
            if (serviceContract != null) {

                Float serviceCost = serviceContract != null ? Float.valueOf(serviceContract.getCostPerPacket() * serviceContract.getPacketsQuantity()) : Float.valueOf("0");
                totalOrderCost = totalOrderCost + serviceCost;
                String serviceId = UUID.randomUUID().toString();
                String serviceType = type.name();
                Integer packetsQuantity = serviceContract.getPacketsQuantity();
                String serviceStatus = StatusType.Created.name();
                Instant startDate = current.plusSeconds(259200);

                Service service = new Service(
                    current.toString(),
                    current.toString(),
                    personName,
                    personName,
                    order,
                    serviceType,
                    packetsQuantity,
                    serviceCost,
                    serviceStatus,
                    startDate.toString(),
                    null
                );

                services.add(service);
            }

        }

        Order savedOrder = orderRepository.saveAndFlush(order);
        return savedOrder.getOrderId();
    }


}
