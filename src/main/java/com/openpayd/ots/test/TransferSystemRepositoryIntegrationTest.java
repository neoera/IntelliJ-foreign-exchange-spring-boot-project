package com.openpayd.ots.test;

import com.openpayd.ots.repository.ClientRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TransferSystemRepositoryIntegrationTest {

    @Autowired
    private final ClientRepository clientRepository;

    public TransferSystemRepositoryIntegrationTest(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    // test cases can be written in here
}