package com.mycompany.xy;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class RoomController {
    RoomView rv;
    int selectedGuestID = -1;
    public RoomController(RoomView view) {
        this.rv = view;
        loadGuestTable();
        addGuestSelection();
        allActions();
    }
    private void loadGuestTable() {
        List<Guest> list = GuestDAO.getAll();
        DefaultTableModel model = (DefaultTableModel) rv.jTable2.getModel();
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
    private void addGuestSelection() {
        rv.jTable2.getSelectionModel().addListSelectionListener(e -> {
            int row = rv.jTable2.getSelectedRow();
            if (row >= 0) {
                selectedGuestID = (int) rv.jTable2.getValueAt(row, 0);
            }
        });
    }
    private void allActions() {
        rv.roomBTN.addActionListener(e -> {
            if (selectedGuestID == -1) {
                JOptionPane.showMessageDialog(null, "Please select a guest first!");
                return;
            }
            Room r = new Room();
            r.setRoomnumber(rv.jTextField3.getText());                      
            r.setRoomType(rv.comboBox.getSelectedItem().toString());
            r.setPrice(Double.parseDouble(rv.jTextField2.getText()));     
            r.setStatus("Occupied");
            r.setGuestID(selectedGuestID);
            RoomDAO.save(r);
            System.out.println("Room Added:");
            System.out.println("Room Number: " + rv.jTextField3.getText());
            System.out.println("Room Type: " + rv.comboBox.getSelectedItem());
            System.out.println("=====================");
            JOptionPane.showMessageDialog(null, "Room assigned to guest!");
            loadGuestTable();
        });
        rv.nextBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rv.setVisible(false);
                ReservationView rv = new ReservationView();
                new ReservationController(rv);
                rv.setVisible(true);
            }
        });
    }
}
