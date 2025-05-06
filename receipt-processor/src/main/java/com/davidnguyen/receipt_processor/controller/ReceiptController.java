package com.davidnguyen.receipt_processor.controller;
import com.davidnguyen.receipt_processor.model.Item;
import com.davidnguyen.receipt_processor.model.Receipt;
import com.davidnguyen.receipt_processor.service.ReceiptService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {
    private final ReceiptService receiptService;

    @Autowired
    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/process")
    public Map<String, String> processReceipt(@RequestBody Receipt receipt) {
        if (receipt.getRetailer() == null || receipt.getPurchaseDate() == null || receipt.getPurchaseTime() == null || receipt.getItems() == null || receipt.getTotal() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid receipt data");
        }
        if (receipt.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Receipt must contain at least one item");
        }
        for (Item item : receipt.getItems()) {
            if (item.getShortDescription() == null || item.getShortDescription().trim().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item shortDescription is required");
            }
            if (item.getPrice() == null || item.getPrice().trim().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item price is required");
            }
        }
        String id = receiptService.processReceipt(receipt);
        return Map.of("id", id);
    }

    @GetMapping("/{id}/points")
    public Map<String, Integer> getPoints(@PathVariable String id) {
        Integer points = receiptService.getPoints(id);
        if (points == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Receipt not found");
        }
        return Map.of("points", points);
    }
}
