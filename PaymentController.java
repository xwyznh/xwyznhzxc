package com.mycompany.xy;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PaymentController {

    PaymentView pv;
    Reservation selectedReservation = null;
    Guest selectedGuest = null;

    public PaymentController(PaymentView pv) {
        this.pv = pv;

        loadGuestTable();        // NEW
        loadReservationTable();  // Default: show all

        // ---------------------------
        // GUEST TABLE SELECTION
        // ---------------------------
        pv.jTable1.getSelectionModel().addListSelectionListener(e -> {
            if (!pv.jTable1.getSelectionModel().isSelectionEmpty()) {

                int row = pv.jTable1.getSelectedRow();

                int guestID = Integer.parseInt(pv.jTable1.getValueAt(row, 0).toString());

                selectedGuest = new Guest();
                selectedGuest.setGuestID(guestID);

                // Load reservations for this guest ONLY
                loadReservationTableByGuest(guestID);
            }
        });

        // ---------------------------
        // RESERVATION TABLE SELECTION
        // ---------------------------
        pv.jTable2.getSelectionModel().addListSelectionListener(e -> {

            if (!pv.jTable2.getSelectionModel().isSelectionEmpty()) {

                int row = pv.jTable2.getSelectedRow();
                int reservationID = Integer.parseInt(pv.jTable2.getValueAt(row, 0).toString());

                selectedReservation = new Reservation();
                selectedReservation.setReservationID(reservationID);

                pv.jTextField2.setText("0"); // default amount
            }
        });

        // ---------------------------
        // PAY BUTTON
        // ---------------------------
       pv.payBTN.addActionListener(e -> {

    if (selectedGuest == null) {
        JOptionPane.showMessageDialog(null, "Please select a guest.");
        return;
    }

    if (selectedReservation == null) {
        JOptionPane.showMessageDialog(null, "Please select a reservation.");
        return;
    }

    if (pv.jTextField2.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Enter amount.");
        return;
    }

    double amount = Double.parseDouble(pv.jTextField2.getText());

    Payment payment = new Payment();
    payment.setReservationID(selectedReservation.getReservationID());
    payment.setAmount(amount);
    payment.setDate(new java.util.Date());
    payment.setStatus("PAID");

    PaymentDAO.addPayment(payment);

    JOptionPane.showMessageDialog(null, "Payment successful!");

    pv.dispose();
    Dashboard dv = new Dashboard();
    new DashboardController(dv);
    dv.setVisible(true);
});
    }
    // -----------------------------------------------------
    // LOAD ALL GUESTS (TOP TABLE)
    // -----------------------------------------------------
    private void loadGuestTable() {
        List<Guest> list = GuestDAO.getAll();
        DefaultTableModel model = (DefaultTableModel) pv.jTable1.getModel();
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

    // -----------------------------------------------------
    // LOAD ALL RESERVATIONS
    // -----------------------------------------------------
    private void loadReservationTable() {
        List<Reservation> list = ReservationDAO.getAll();
        DefaultTableModel model = (DefaultTableModel) pv.jTable2.getModel();
        model.setRowCount(0);

        for (Reservation r : list) {
            model.addRow(new Object[]{
                r.getReservationID(),
                r.getCheckIn(),
                r.getCheckOut()
            });
        }
    }

    // -----------------------------------------------------
    // LOAD RESERVATIONS BY GUEST (WHEN CLICKED GUEST)
    // -----------------------------------------------------
    private void loadReservationTableByGuest(int guestID) {
        List<Reservation> list = ReservationDAO.getByGuestID(guestID);
        DefaultTableModel model = (DefaultTableModel) pv.jTable2.getModel();
        model.setRowCount(0);

        for (Reservation r : list) {
            model.addRow(new Object[]{
                r.getReservationID(),
                r.getCheckIn(),
                r.getCheckOut()
            });
        }
    }
}
    