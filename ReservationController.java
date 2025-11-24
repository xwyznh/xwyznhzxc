package com.mycompany.xy;
import java.awt.event.*;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
public class ReservationController {

    ReservationView rv;
    int selectedGuestID = -1;

    public ReservationController(ReservationView view) {
        this.rv = view;

        loadGuestTable();
        addRowSelection();

        rv.reserveBTN.addActionListener(new ReserveAction());

     
    }
    private void loadGuestTable() {
        List<Guest> list = GuestDAO.getAll();
        DefaultTableModel model = (DefaultTableModel) rv.jTable1.getModel();
        model.setRowCount(0);

        for (Guest g : list) {
            model.addRow(new Object[]{
                g.getGuestID(),
                g.getFullname(),
                g.getAddress(),
                g.getPhoneNumber()
            });
        }
    }
    private void addRowSelection() {
        rv.jTable1.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            int row = rv.jTable1.getSelectedRow();
            if (row >= 0) {
                selectedGuestID = Integer.parseInt(rv.jTable1.getValueAt(row, 0).toString());
            }
        });
    }
    class ReserveAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            reserve();
            }
    }
   private void reserve() {
    try {
        if (selectedGuestID == -1) {
            JOptionPane.showMessageDialog(null, "Please select a guest first.");
            return;
        }

        String roomType = rv.roomBox.getSelectedItem().toString();
        Room room = RoomDAO.getRoomByName(roomType);

        if (room == null) {
            JOptionPane.showMessageDialog(null, "Room not found!");
            return;
        }
        int roomID = room.getRoomID();
        java.util.Date checkIn = rv.jDateChooser1.getDate();
        java.util.Date checkOut = rv.jDateChooser2.getDate();

        if (checkIn == null || checkOut == null) {
            JOptionPane.showMessageDialog(null, "Please choose both dates.");
            return;
        }
        Reservation r = new Reservation();
        r.setGuestID(selectedGuestID);
        r.setRoomID(roomID);
        r.setRoom(roomType);
        r.setCheckIn(checkIn);
        r.setCheckOut(checkOut);
        ReservationDAO.insert(r);
        JOptionPane.showMessageDialog(null, "Reservation Successful!");
        PaymentView pv = new PaymentView();
        PaymentController pc = new PaymentController(pv);
        for (int i = 0; i < pv.jTable1.getRowCount(); i++) {
            int id = Integer.parseInt(pv.jTable1.getValueAt(i, 0).toString());
            if (id == selectedGuestID) {
                pv.jTable1.setRowSelectionInterval(i, i);
                break;
            }
        }
        pv.setVisible(true);
        rv.dispose();
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        rv.dispose();
    }
}
}
