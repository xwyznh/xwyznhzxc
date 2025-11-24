package com.mycompany.xy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    public static void insert(Reservation r) {
    try {
        Connection con = DBConnection.getConnection();

        String sql = "INSERT INTO reservation (guestID, roomID, room, checkin, checkout, status) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, r.getGuestID());                               // guestID
        ps.setInt(2, r.getRoomID());                                // roomID
        ps.setString(3, r.getRoom());                               // room (room type or number)
        ps.setDate(4, new java.sql.Date(r.getCheckIn().getTime())); // checkin
        ps.setDate(5, new java.sql.Date(r.getCheckOut().getTime())); // checkout
        ps.setString(6, "Reserved");                                // status

        ps.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    public static List<Reservation> getByGuestID(int guestID) {
    List<Reservation> list = new ArrayList<>();

    try {
        Connection con = DBConnection.getConnection();

        String sql = "SELECT * FROM reservation WHERE guestID = ? ORDER BY reservationID DESC";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, guestID);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Reservation r = new Reservation();

            r.setReservationID(rs.getInt("reservationID"));
            r.setGuestID(rs.getInt("guestID"));
            r.setRoomID(rs.getInt("roomID"));
            r.setRoom(rs.getString("room"));
            r.setCheckIn(rs.getDate("checkin"));
            r.setCheckOut(rs.getDate("checkout"));
            r.setStatus(rs.getString("status"));

            list.add(r);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}


    public static List<Reservation> getAll() {
    List<Reservation> list = new ArrayList<>();

    try {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT * FROM reservation ORDER BY reservationid DESC";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Reservation r = new Reservation();
            r.setReservationID(rs.getInt("reservationid"));
            r.setGuestID(rs.getInt("guestid"));
            r.setRoomID(rs.getInt("roomid"));
            r.setRoom(rs.getString("room"));
            r.setCheckIn(rs.getDate("checkin"));
            r.setCheckOut(rs.getDate("checkout"));
            r.setStatus(rs.getString("status"));

            list.add(r);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}


    public static void delete(int id) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM reservation WHERE reservationID = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
