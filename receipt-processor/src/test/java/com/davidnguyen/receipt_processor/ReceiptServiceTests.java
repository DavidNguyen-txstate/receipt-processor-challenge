package com.davidnguyen.receipt_processor;

import com.davidnguyen.receipt_processor.model.Item;
import com.davidnguyen.receipt_processor.model.Receipt;
import com.davidnguyen.receipt_processor.storage.ReceiptStorage;
import com.davidnguyen.receipt_processor.service.ReceiptService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptServiceTest {

    private ReceiptService receiptService;
    private ReceiptStorage receiptStorage;

    @BeforeEach
    void setUp() {
        receiptStorage = new ReceiptStorage();
        receiptService = new ReceiptService(receiptStorage);
    }

    @Test
    void testProcessReceipt_withExample1() {
        Receipt receipt = new Receipt();
        receipt.setRetailer("Target");
        receipt.setPurchaseDate("2022-01-01");
        receipt.setPurchaseTime("13:01");
        receipt.setTotal("35.35");

        Item item1 = new Item();
        item1.setShortDescription("Mountain Dew 12PK");
        item1.setPrice("6.49");

        Item item2 = new Item();
        item2.setShortDescription("Emils Cheese Pizza");
        item2.setPrice("12.25");

        Item item3 = new Item();
        item3.setShortDescription("Knorr Creamy Chicken");
        item3.setPrice("1.26");

        Item item4 = new Item();
        item4.setShortDescription("Doritos Nacho Cheese");
        item4.setPrice("3.35");

        Item item5 = new Item();
        item5.setShortDescription("   Klarbrunn 12-PK 12 FL OZ  ");
        item5.setPrice("12.00");

        receipt.setItems(List.of(item1, item2, item3, item4, item5));

        String id = receiptService.processReceipt(receipt);
        assertNotNull(id);

        Integer points = receiptService.getPoints(id);

        // According to the README, this receipt should be worth 28 points
        assertEquals(28, points);
    }

    @Test
    void testProcessReceipt_withExample2() {
        Receipt receipt = new Receipt();
        receipt.setRetailer("M&M Corner Market");
        receipt.setPurchaseDate("2022-03-20");
        receipt.setPurchaseTime("14:33");
        receipt.setTotal("9.00");

        Item item1 = new Item();
        item1.setShortDescription("Gatorade");
        item1.setPrice("2.25");

        Item item2 = new Item();
        item2.setShortDescription("Gatorade");
        item2.setPrice("2.25");

        Item item3 = new Item();
        item3.setShortDescription("Gatorade");
        item3.setPrice("2.25");

        Item item4 = new Item();
        item4.setShortDescription("Gatorade");
        item4.setPrice("2.25");

        receipt.setItems(List.of(item1, item2, item3, item4));

        String id = receiptService.processReceipt(receipt);
        assertNotNull(id);

        Integer points = receiptService.getPoints(id);

        // According to the README, this receipt should be worth 109 points
        assertEquals(109, points);
    }
}
