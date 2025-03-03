import java.time.LocalDateTime;

public class Transaction {
    private String transactionId;
    private String bookingId;
    private String customerId;
    private String riderId;
    private LocalDateTime transactionDate;
    private LocalDateTime pickupTime;
    private LocalDateTime arrivalTime;
    private double amountPaid;
    private String paymentStatus;

    public Transaction(String transactionId, String bookingId, String customerId, String riderId, LocalDateTime transactionDate, LocalDateTime pickupTime, LocalDateTime arrivalTime, double amountPaid, String paymentStatus) {
        this.transactionId = transactionId;
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.riderId = riderId;
        this.transactionDate = transactionDate;
        this.pickupTime = pickupTime;
        this.arrivalTime = arrivalTime;
        this.amountPaid = amountPaid;
        this.paymentStatus = paymentStatus;
    }

    // Getters and setters for each field
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getRiderId() {
        return riderId;
    }

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public LocalDateTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(LocalDateTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}