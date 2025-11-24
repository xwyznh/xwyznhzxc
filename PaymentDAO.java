package com.mycompany.xy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class PaymentDAO {

    public static void addPayment(Payment p) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO payment (ReservationID, Amount, paymentdate, Status) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, p.getReservationID());
            stmt.setDouble(2, p.getAmount());
            stmt.setDate(3, new java.sql.Date(p.getDate().getTime()));
            stmt.setString(4, p.getStatus());
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Payment error: " + ex.getMessage());
        }
    }

    public static List<Payment> getAll() {
        List<Payment> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM payment";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Payment p = new Payment();
                p.setPaymentID(rs.getInt("PaymentID"));
                p.setReservationID(rs.getInt("ReservationID"));
                p.setAmount(rs.getDouble("Amount"));
                p.setDate(rs.getDate("Date"));  
                p.setStatus(rs.getString("Status"));
                list.add(p);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
    public static List<Reservation> getAlls() {
    List<Reservation> list = new ArrayList<>();
    try (Connection conn = DBConnection.getConnection()) {
        String sql = "SELECT ReservationID, CheckIn, CheckOut FROM reservation";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Reservation r = new Reservation();
            r.setReservationID(rs.getInt("ReservationID"));
            r.setCheckIn(rs.getDate("CheckIn"));
            r.setCheckOut(rs.getDate("CheckOut"));
            list.add(r);
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    return list;
}

}
