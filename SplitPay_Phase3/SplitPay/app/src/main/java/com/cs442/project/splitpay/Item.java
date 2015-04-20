package com.cs442.project.splitpay;

public class Item {
    private Boolean ChkBox;
    private String memberName;
    private Double totalAmountOwed;
    private Double perItemAmountOwed;
    private Double totalAmountSpent;
    private Double perItemAmountSpent;
    private String paymentStatus;
    private String itemName;

    public Item(Boolean ChkBox, String memberName,  Double perItemAmountOwed, Double perItemAmountSpent,Double totalAmountOwed, Double totalAmountSpent) {
        this.ChkBox = ChkBox;
        this.memberName = memberName;
        this.perItemAmountOwed = perItemAmountOwed;
        this.perItemAmountSpent = perItemAmountSpent;
        this.totalAmountOwed = totalAmountOwed;
        this.totalAmountSpent = totalAmountSpent;
    }

    public Item() {
    }

    public Boolean getChkBox() {
        return ChkBox;
    }

    public void setChkBox(Boolean chkBox) {
        ChkBox = chkBox;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Double getTotalAmountOwed() {
        return totalAmountOwed;
    }

    public void setTotalAmountOwed(Double totalAmountOwed) {
        this.totalAmountOwed = totalAmountOwed;
    }

    public Double getTotalAmountSpent() {
        return totalAmountSpent;
    }

    public void setTotalAmountSpent(Double totalAmountSpent) {
        this.totalAmountSpent = totalAmountSpent;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getPerItemAmountSpent() {
        return perItemAmountSpent;
    }

    public void setPerItemAmountSpent(Double perItemAmountSpent) {
        this.perItemAmountSpent = perItemAmountSpent;
    }

    public Double getPerItemAmountOwed() {
        return perItemAmountOwed;
    }

    public void setPerItemAmountOwed(Double perItemAmountOwed) {
        this.perItemAmountOwed = perItemAmountOwed;
    }
}