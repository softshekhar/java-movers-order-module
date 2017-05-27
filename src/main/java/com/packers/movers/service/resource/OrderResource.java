package com.packers.movers.service.resource;

import com.packers.movers.commons.contracts.IdContract;
import com.packers.movers.service.OrderService;
import com.packers.movers.service.contracts.OrderContract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path(OrderResource.BASE_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {
    private static final Logger LOG = LoggerFactory.getLogger(OrderResource.class);
    public final static String BASE_PATH = "/createOrder";

    private OrderService orderService;

    @Autowired
    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response placeOrder(OrderContract orderContract) {
        LOG.trace(orderContract.toJson());

        Long id = orderService.placeOrder(orderContract);

        IdContract idContract = new IdContract(id.toString());

        return Response.ok(idContract.toJson()).build();
    }
}
