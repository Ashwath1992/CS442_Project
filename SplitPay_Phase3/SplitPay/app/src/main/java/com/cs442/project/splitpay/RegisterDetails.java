package com.cs442.project.splitpay;

public class RegisterDetails {
    private String fullname;
    private String username;
    private String password;
    private String email;
    private String phoneno;
    private String nameoncard;
    private String cardnumber;
    private String cvv;
    private String date;
    private String Card;
    private String card_type;
    public RegisterDetails(){}
    public RegisterDetails(String fullname, String username, String password, String email, String phoneno, String nameoncard, String cardnumber, String cvv, String date, String Card, String card_type)
    {
        this.fullname=fullname;
        this.username=username;
        this.email=email;
        this.phoneno=phoneno;
        this.password=password;
        this.nameoncard=nameoncard;
        this.cardnumber=cardnumber;
        this.cvv=cvv;
        this.date=date;
        this.Card=Card;
        this.card_type=card_type;
    }
    public String getFullname(){return fullname;}
    public String getUsername(){return username;}
    public String getEmail() {return email;}
    public String getPhoneno() {return phoneno;}
    public String getPassword() {return password;}
    public String getNameoncard() {return nameoncard;}
    public String getCardnumber() {return cardnumber;}
    public String getCvv() {return cvv;}
    public String getDate() {return date;}
    public String getCard() {return Card;}
    public String getCard_type(){return card_type;}
}
