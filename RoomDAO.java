package com.mycompany.xy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class RoomDAO {

   public static void save(Room r) {
    try (Connection conn = DBConnection.getConnection()) {
        String sql = "INSERT INTO room (RoomNumber, RoomType, Price, Status, GuestID) "
                   + "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, r.getRoomnumber());
        stmt.setString(2, r.getRoomType());
        stmt.setDouble(3, r.getPrice());
        stmt.setString(4, "Occupied");   // auto-occupied
        stmt.setInt(5, r.getGuestID());  // the selected guest

        stmt.executeUpdate();
        stmt.close();

        JOptionPane.showMessageDialog(null, "Room Assigned to Guest!");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Error saving room: " + ex.getMessage());
    }
}


    public static List<Room> getAll() {
        List<Room> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM room ORDER BY RoomID DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Room r = new Room();
                r.setRoomID(rs.getInt("RoomID"));
                r.setRoomType(rs.getString("RoomType"));
                r.setPrice(rs.getDouble("Price"));
                r.setStatus(rs.getString("Status"));
                list.add(r);
            }

            rs.close();
            stmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error fetching rooms: " + ex.getMessage());
        }

        return list;
    }

    public static Room getByID(int id) {
        Room room = null;

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM room WHERE RoomID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                room = new Room();
                room.setRoomID(rs.getInt("RoomID"));
                room.setRoomType(rs.getString("RoomType"));
                room.setPrice(rs.getDouble("Price"));
                room.setStatus(rs.getString("Status"));
            }

            rs.close();
            stmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error finding room: " + ex.getMessage());
        }

        return room;
    }

    public static Room getRoomByName(String name) {
    Room r = null;

    try {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
            "SELECT * FROM room WHERE roomtype = ?"
        );
        ps.setString(1, name);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            r = new Room();
            r.setRoomID(rs.getInt("roomid"));
            r.setRoomnumber(rs.getString("roomnumber"));
            r.setRoomType(rs.getString("roomtype"));
            r.setPrice(rs.getDouble("price"));
            r.setStatus(rs.getString("status"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return r;
}
}