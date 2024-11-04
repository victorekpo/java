package com.example;

public class QueueItem {
    private final String id;
    private final String data;
    private ItemStatus status;

    public QueueItem(String data) {
        this.id = java.util.UUID.randomUUID().toString();
        this.data = data;
        this.status = ItemStatus.PENDING;
    }

    public String getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Data: " + data + " Status: " + status;
    }

    public enum ItemStatus {
        PENDING, PROCESSING, PROCESSED
    }
}