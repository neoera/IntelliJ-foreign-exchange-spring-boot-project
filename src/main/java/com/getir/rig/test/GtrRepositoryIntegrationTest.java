package com.getir.rig.test;

import com.getir.rig.repository.OrderRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GtrRepositoryIntegrationTest {

    @Autowired
    private final OrderRepository orderRepository;

    public GtrRepositoryIntegrationTest(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // test cases can be written in here
}