package com.example.javafx_1.classes;

import java.time.LocalDate;
import java.util.Date;

public class Income {
    private int id;
    private String user;
    private String user_id;
    private String amount;
    private String date;
    private String description;
    public Income() {}
    public Income(int id, String user, String amount, String description, String date) {
        this.id = id;
        this.user = user;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }


    // Getter and setter methods for the properties
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {return user;}
    public void setUser(String cmbUser) {this.user = cmbUser;}
    public String getAmount() {return amount;}
    public void setAmount(String txtAmount) {this.amount = txtAmount;}
    public String getDescription() {return description;}
    public void setDescription(String txtDescription) {this.description = txtDescription;}
    public String getDate() {return date;}
    public void setDate(String txtDate) {this.date = txtDate;}

    public String getUser_id() {return user_id;}
    public void setUser_id(String user_id) {this.user_id = user_id;}
}
