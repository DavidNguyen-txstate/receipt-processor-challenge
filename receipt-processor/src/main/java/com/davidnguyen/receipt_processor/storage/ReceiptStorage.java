package com.davidnguyen.receipt_processor.storage;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class ReceiptStorage {
    private final Map<String, Integer> receiptPoints = new ConcurrentHashMap<>();

    public void save(String id, int points) {
        receiptPoints.put(id, points);
    }

    public Integer getPoints(String id) {
        return receiptPoints.get(id);
    }
}
