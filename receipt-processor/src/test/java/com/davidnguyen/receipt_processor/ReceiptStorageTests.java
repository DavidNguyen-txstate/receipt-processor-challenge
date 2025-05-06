package com.davidnguyen.receipt_processor;

import org.junit.jupiter.api.Test;

import com.davidnguyen.receipt_processor.storage.ReceiptStorage;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptStorageTest {

    @Test
    void testSaveAndGetPoints() {
        ReceiptStorage storage = new ReceiptStorage();
        String id = "abc123";
        int points = 42;

        storage.save(id, points);
        Integer retrieved = storage.getPoints(id);

        assertNotNull(retrieved);
        assertEquals(points, retrieved);
    }

    @Test
    void testGetPointsForUnknownId() {
        ReceiptStorage storage = new ReceiptStorage();
        assertNull(storage.getPoints("nonexistent"));
    }
}
