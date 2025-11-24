package com.mycompany.xy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GuestController {

    GuestView gv;
    Guest selectedGuest = null;

    public GuestController(GuestView temp) {
        gv = temp;

        // load table on startup
        loadGuestTable();

        // table row selection listener
        gv.jTable2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = gv.jTable2.getSelectedRow();
                if (row >= 0) {
                    int id = Integer.parseInt(gv.jTable2.getValueAt(row, 0).toString());
                    selectedGuest = GuestDAO.getById(id);

                    gv.jTextField1.setText(selectedGuest.getFullname());
                    
                    gv.jTextField6.setText(selectedGuest.getAddress());
                    gv.jTextField7.setText(selectedGuest.getPhoneNumber());
                }
            }
        });

        gv.allListeners(new AllActions());
    }

    public void loadGuestTable() {
        List<Guest> list = GuestDAO.getAll();
        DefaultTableModel model = (DefaultTableModel) gv.jTable2.getModel();
        model.setRowCount(0);

        for (Guest g : list) {
            Object[] row = {
                g.getGuestID(),
                g.getFullname(),
                
                g.getAddress(),
                g.getPhoneNumber()
            };
            model.addRow(row);
        }
    }

    class AllActions implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            // ADD BUTTON -------------------------------------------------------
            if (e.getSource() == gv.addBTN) {

                Guest g = new Guest();
                g.setFullname(gv.jTextField1.getText());
                g.setAddress(gv.jTextField6.getText());
                g.setPhoneNumber(gv.jTextField7.getText());

                GuestDAO.save(g);
                System.out.println("Guest Added:");
                System.out.println("Name: " + gv.jTextField1.getText());
                System.out.println("Address: " + gv.jTextField6.getText());
                System.out.println("Phone: " + gv.jTextField7.getText());

                loadGuestTable();
                clearFields();
            }

            // EDIT BUTTON (JUST LOADS DATA) ----------------------------------
            if (e.getSource() == gv.editBTN) {

                int row = gv.jTable2.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(null, "Please select a guest first!");
                    return;
                }

                int id = Integer.parseInt(gv.jTable2.getValueAt(row, 0).toString());
                selectedGuest = GuestDAO.getById(id);

                gv.jTextField1.setText(selectedGuest.getFullname());
               
                gv.jTextField6.setText(selectedGuest.getAddress());
                gv.jTextField7.setText(selectedGuest.getPhoneNumber());
            }

            // UPDATE BUTTON ----------------------------------------------------
            if (e.getSource() == gv.updateBTN) {

                if (selectedGuest == null) {
                    JOptionPane.showMessageDialog(null, "No guest selected!");
                    return;
                }

                selectedGuest.setFullname(gv.jTextField1.getText());
                
                selectedGuest.setAddress(gv.jTextField6.getText());
                selectedGuest.setPhoneNumber(gv.jTextField7.getText());

                GuestDAO.update(selectedGuest);
                loadGuestTable();
                clearFields();

                selectedGuest = null;
            }

            // DELETE BUTTON ---------------------------------------------------
            if (e.getSource() == gv.deleteBTN) {

                if (selectedGuest == null) {
                    JOptionPane.showMessageDialog(null, "Select a guest to delete!");
                    return;
                }

                GuestDAO.delete(selectedGuest.getGuestID());
                loadGuestTable();
                clearFields();

                selectedGuest = null;
            }
            if(e.getSource() == gv.nextBTN) {
                gv.setVisible(false);
                RoomView rv = new RoomView();
                new RoomController(rv);
                rv.setVisible(true);
            }
        }
    }

    private void clearFields() {
        gv.jTextField1.setText("");
        
        gv.jTextField6.setText("");
        gv.jTextField7.setText("");
    }
}
