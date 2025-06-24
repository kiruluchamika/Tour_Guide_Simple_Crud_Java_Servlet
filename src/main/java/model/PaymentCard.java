package model;

import java.sql.Timestamp;

public class PaymentCard {
    private int cardId;
    private int userId;
    private String cardholderName;
    private String cardNumberLast4;
    private String cardType;
    private int expiryMonth;
    private int expiryYear;
    private Timestamp addedOn;
    private boolean isDefault;

    // Getters
    public int getCardId() {
        return cardId;
    }

    public int getUserId() {
        return userId;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public String getCardNumberLast4() {
        return cardNumberLast4;
    }

    public String getCardType() {
        return cardType;
    }

    public int getExpiryMonth() {
        return expiryMonth;
    }

    public int getExpiryYear() {
        return expiryYear;
    }

    public Timestamp getAddedOn() {
        return addedOn;
    }

    // Renamed Getter
    public boolean getIsDefault() {
        return isDefault;
    }

    // Setters
    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public void setCardNumberLast4(String cardNumberLast4) {
        this.cardNumberLast4 = cardNumberLast4;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public void setExpiryMonth(int expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public void setExpiryYear(int expiryYear) {
        this.expiryYear = expiryYear;
    }

    public void setAddedOn(Timestamp addedOn) {
        this.addedOn = addedOn;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }
}
