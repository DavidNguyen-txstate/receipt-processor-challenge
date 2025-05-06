package com.davidnguyen.receipt_processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.davidnguyen.receipt_processor.controller.ReceiptController;
import com.davidnguyen.receipt_processor.model.Item;
import com.davidnguyen.receipt_processor.model.Receipt;
import com.davidnguyen.receipt_processor.service.ReceiptService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReceiptController.class)
class ReceiptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReceiptService receiptService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void processReceipt_ReturnsId() throws Exception {
        Receipt receipt = new Receipt();
        receipt.setRetailer("Target");
        receipt.setPurchaseDate("2022-01-01");
        receipt.setPurchaseTime("13:01");
        receipt.setTotal("35.35");
        Item item = new Item();
        item.setShortDescription("Mountain Dew 12PK");
        item.setPrice("6.49");
        receipt.setItems(List.of(item));

        Mockito.when(receiptService.processReceipt(Mockito.any(Receipt.class))).thenReturn("test-id");

        mockMvc.perform(post("/receipts/process")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(receipt)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("test-id"));
    }

    @Test
    void getPoints_ReturnsPoints() throws Exception {
        Mockito.when(receiptService.getPoints("test-id")).thenReturn(42);

        mockMvc.perform(get("/receipts/test-id/points"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").value(42));
    }

    @Test
    void getPoints_NotFound() throws Exception {
        Mockito.when(receiptService.getPoints("missing-id")).thenReturn(null);

        mockMvc.perform(get("/receipts/missing-id/points"))
                .andExpect(status().isNotFound());
    }
}
