package com.example.demo.service.jms;

public class InternalQueueMessage {
    private String id;
    private String message;
    private String threadName;
    private Status status;

    public InternalQueueMessage(String id, String message, String threadName, Status status) {
        this.id = id;
        this.message = message;
        this.threadName = threadName;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getThreadName() {
        return threadName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        PENDING,
        PROCESSING,
        FAILURE,
        SUCCESS
    }

    @Override
    public String toString() {
        return "InternalQueueMessage{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", threadName='" + threadName + '\'' +
                ", status=" + status +
                '}';
    }
}