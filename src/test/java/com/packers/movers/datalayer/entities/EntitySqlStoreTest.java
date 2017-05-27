package com.packers.movers.datalayer.entities;

import com.packers.movers.datalayer.entities.customer.Customer;
import com.packers.movers.test.UnitTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.UUID;


public class EntitySqlStoreTest extends UnitTest{

    @BeforeClass
    public void setup() {
    }

    @Test
    public void testPersistCustomer() throws Exception {
        Instant now = Instant.now();
        String employeeName = "testSalesPerson";
        String customerName = "JohnTravolta";
        String phone = "004723456789";
        String email = "jt@abc.com";
        String customerId = UUID.randomUUID().toString();

        Customer customer = new Customer(now.toString(), now.toString(), employeeName, employeeName, customerName, phone, email);


    }
}