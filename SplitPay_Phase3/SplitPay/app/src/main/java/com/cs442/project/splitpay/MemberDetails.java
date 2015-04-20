package com.cs442.project.splitpay;

public class MemberDetails {
    private String membername;
    private double amountpaid;
    private double amountowed;
    private String paidfor;
    public MemberDetails(String membername,Double amountowed,Double amountpaid,String paidfor){
        this.membername=membername;
        this.amountowed=amountowed;
        this.amountpaid=amountpaid;
        this.paidfor=paidfor;
    }

    public String getMembername() {return membername;}
    public double getAmountpaid() {return amountpaid;}
    public double getAmountowed() {return amountowed;}
    public String getPaidfor() {return paidfor;}
}

