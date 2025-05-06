package com.davidnguyen.receipt_processor.service;
import com.davidnguyen.receipt_processor.model.Item;
import com.davidnguyen.receipt_processor.model.Receipt;
import com.davidnguyen.receipt_processor.storage.ReceiptStorage;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.math.RoundingMode;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class ReceiptService {
    private final ReceiptStorage storage;

    public ReceiptService(ReceiptStorage storage) {
        this.storage = storage;
    }

    public String processReceipt(Receipt receipt) {
        int points = calculatePoints(receipt);
        String id = UUID.randomUUID().toString();
        storage.save(id, points);
        return id;
    }

    public Integer getPoints(String id) {
        return storage.getPoints(id);
    }

    private int calculatePoints(Receipt receipt) {
        int points = 0;

        // Rule 1: One point for every alphanumeric character in the retailer name.
        points += receipt.getRetailer().replaceAll("[^a-zA-Z0-9]", "").length();

        // Rule 2: 50 points if the total is a round dollar amount with no cents.
        BigDecimal total = new BigDecimal(receipt.getTotal());
        if (total.scale() == 0 || total.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0) {
            points += 50;
        }

        // Rule 3: 25 points if the total is a multiple of 0.25.
        if (total.remainder(new BigDecimal("0.25")).compareTo(BigDecimal.ZERO) == 0) {
            points += 25;
        }

        // Rule 4: 5 points for every two items on the receipt.
        points += (receipt.getItems().size() / 2) * 5;

        // Rule 5: If the trimmed length of the item description is a multiple of 3, multiply the price by 0.2 and round up to nearest integer. Add to points.
        for (Item item : receipt.getItems()) {
            String desc = item.getShortDescription().trim();
            if (desc.length() % 3 == 0) {
                BigDecimal price = new BigDecimal(item.getPrice());
                int bonus = price.multiply(new BigDecimal("0.2")).setScale(0, RoundingMode.UP).intValue();
                points += bonus;
            }
        }

        // Rule 6: 6 points if the day in the purchase date is odd.
        LocalDate date = LocalDate.parse(receipt.getPurchaseDate());
        if (date.getDayOfMonth() % 2 == 1) {
            points += 6;
        }

        // Rule 7: 10 points if time of purchase is after 2:00pm and before 4:00pm.
        LocalTime time = LocalTime.parse(receipt.getPurchaseTime());
        if (time.isAfter(LocalTime.of(14, 0)) && time.isBefore(LocalTime.of(16, 0))) {
            points += 10;
        }

        return points;
    }
}
