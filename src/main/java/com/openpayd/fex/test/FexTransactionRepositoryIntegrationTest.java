package com.openpayd.fex.test;

import com.openpayd.fex.repository.FexTransactionRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FexTransactionRepositoryIntegrationTest {

    @Autowired
    private final FexTransactionRepository fexTransactionRepository;

    public FexTransactionRepositoryIntegrationTest(FexTransactionRepository fexTransactionRepository) {
        this.fexTransactionRepository = fexTransactionRepository;
    }

    // test cases can be written in here
}