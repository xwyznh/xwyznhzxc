/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.xy;

import java.util.Date;

/**
 *
 * @author gamin
 */
public class Payment {
    private int reservationID;
    private int paymentID;
    private double amount;
    private  Date Date;
    private String Status;

    public int getReservationID() {
        return reservationID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

   
    

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

   

    

   

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        this.Date = date;
    }
    
}