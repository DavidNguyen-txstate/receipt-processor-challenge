package com.davidnguyen.receipt_processor;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.davidnguyen.receipt_processor.controller.ReceiptController;
import com.davidnguyen.receipt_processor.service.ReceiptService;
import com.davidnguyen.receipt_processor.storage.ReceiptStorage;

@SpringBootTest
class ReceiptProcessorApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ReceiptController receiptController;

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private ReceiptStorage receiptStorage;

    @Test
    void contextLoads() {
        // Basic context load check
        assertThat(context).isNotNull();
    }

    @Test
    void receiptControllerIsLoaded() {
        assertThat(receiptController).isNotNull();
    }

    @Test
    void receiptServiceIsLoaded() {
        assertThat(receiptService).isNotNull();
    }

    @Test
    void receiptStorageIsLoaded() {
        assertThat(receiptStorage).isNotNull();
    }
}
