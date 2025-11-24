
package com.mycompany.xy;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class GuestDAO {

    public static void save(Guest g) {
    try (Connection conn = DBConnection.getConnection()) {
        String sql = "INSERT INTO guest (fullname, Address, PhoneNumber) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, g.getFullname());
        stmt.setString(2, g.getAddress());
        stmt.setString(3, g.getPhoneNumber());

        stmt.executeUpdate();
        stmt.close();
        JOptionPane.showMessageDialog(null, "Guest saved!");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Error saving guest: " + ex.getMessage());
    }
}


    public static int saveAndReturnId(Guest g) {
    int generatedId = -1;
    try (Connection conn = DBConnection.getConnection()) {
        String sql = "INSERT INTO guest (fullname, Address, PhoneNumber) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, g.getFullname());
        stmt.setString(2, g.getAddress());
        stmt.setString(3, g.getPhoneNumber());

        stmt.executeUpdate();
        ResultSet keys = stmt.getGeneratedKeys();
        if (keys.next()) generatedId = keys.getInt(1);

        keys.close();
        stmt.close();
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Error saving guest: " + ex.getMessage());
    }
    return generatedId;
}


    public static void update(Guest g) {
    try (Connection conn = DBConnection.getConnection()) {
        String sql = "UPDATE guest SET fullname=?, Address=?, PhoneNumber=? WHERE GuestID=?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, g.getFullname());
        stmt.setString(2, g.getAddress());
        stmt.setString(3, g.getPhoneNumber());
        stmt.setInt(4, g.getGuestID());

        stmt.executeUpdate();
        stmt.close();
        JOptionPane.showMessageDialog(null, "Guest updated!");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Error updating guest: " + ex.getMessage());
    }
}

    public static void delete(int guestID) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM guest WHERE GuestID=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, guestID);
            stmt.executeUpdate();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Guest deleted!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error deleting guest: " + ex.getMessage());
        }
    }

    public static Guest getById(int guestID) {
        Guest g = null;
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM guest WHERE GuestID=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, guestID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                g = new Guest();
                g.setGuestID(rs.getInt("GuestID"));
                g.setFullname(rs.getString("FullName"));
                g.setAddress(rs.getString("Address"));
                g.setPhoneNumber(rs.getString("PhoneNumber"));
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error fetching guest: " + ex.getMessage());
        }
        return g;
    }

    public static List<Guest> getAll() {
        List<Guest> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM guest ORDER BY GuestID DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Guest g = new Guest();
                g.setGuestID(rs.getInt("GuestID"));
                g.setFullname(rs.getString("FullName"));
                
                g.setAddress(rs.getString("Address"));
                g.setPhoneNumber(rs.getString("PhoneNumber"));
                list.add(g);
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error fetching guests: " + ex.getMessage());
        }
        return list;
    }

    public static int countAll() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT COUNT(*) FROM guest";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int c = rs.getInt(1);
                rs.close();
                stmt.close();
                return c;
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) { }
        return 0;
    }
}
