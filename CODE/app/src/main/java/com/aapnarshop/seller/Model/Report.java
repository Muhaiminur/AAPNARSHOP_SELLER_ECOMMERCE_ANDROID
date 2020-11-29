package com.aapnarshop.seller.Model;

public class Report {

    private int id;
    private String title;
    private int totalNumber;
    private double totalPrice;


    public Report(String title, int totalNumber, double totalPrice) {
        this.title = title;
        this.totalNumber = totalNumber;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
