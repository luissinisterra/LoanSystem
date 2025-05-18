package app.view.forms;

import app.controller.LoanController;
import app.model.Client;
import app.model.Loan;
import app.model.User;
import app.view.LoanListView;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EditLoanForm extends JPanel {
    private JTextField txtId;
    private JTextField txtClientId;
    private JTextField txtAmount;
    private JTextField txtInterestRate;
    private JTextField txtTerm;
    private JTextField txtDate;
    private JComboBox<String> cbStatus;
    private LoanController loanController;
    private LoanListView listView;
    private User user;

    public EditLoanForm(int id, double amount, double interestRate, double term, String status, String date, int clientId, User user, LoanListView listView) {
        this.user = user;
        this.listView = listView;
        this.loanController = new LoanController();
        init(id, amount, interestRate, term, status, date, clientId);
    }

    private void init(int id, double amount, double interestRate, double term, String status, String date, int clientId) {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));

        // Panel principal
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "[fill,360]"));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "arc:20;" +
                        "[light]background:darken(@background,3%);" +
                        "[dark]background:lighten(@background,3%)");

        // Campos de texto prellenados con los datos del préstamo
        txtId = createTextField("ID del préstamo");
        txtId.setText(String.valueOf(id));
        txtId.setEditable(false); // El ID no debe ser editable

        txtClientId = createTextField("Documento del cliente");
        txtClientId.setText(String.valueOf(clientId));
        txtClientId.setEditable(false);

        txtAmount = createTextField("Monto");
        txtAmount.setText(String.valueOf(amount));

        txtInterestRate = createTextField("Tasa de Interés (%)");
        txtInterestRate.setText(String.valueOf(interestRate));

        txtTerm = createTextField("Plazo (meses)");
        txtTerm.setText(String.valueOf(term));

        txtDate = createTextField("Fecha (YYYY-MM-DD)");
        txtDate.setText(date);

        cbStatus = createComboBox();
        cbStatus.setSelectedItem(status);

        // Botón Actualizar
        JButton cmdUpdate = new JButton("Actualizar");
        cmdUpdate.putClientProperty(FlatClientProperties.STYLE,
                "[light]background:darken(@background,10%);" +
                        "[dark]background:lighten(@background,10%);" +
                        "borderWidth:0;" +
                        "focusWidth:0;" +
                        "innerFocusWidth:0");
        cmdUpdate.addActionListener(e -> {
            if (validateFields()) {
                updateLoan();
                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Préstamo actualizado correctamente");

                // Método para refrescar la tabla del padre
                this.listView.refreshTable();

                // Obtener la ventana padre y cerrarla
                Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null) {
                    window.dispose();
                }
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, "Por favor complete todos los campos obligatorios");
            }
        });

        // Título y descripción
        JLabel lbTitle = new JLabel("Editar Préstamo");
        JLabel description = new JLabel("Actualice los datos del préstamo seleccionado.");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");
        description.putClientProperty(FlatClientProperties.STYLE,
                "[light]foreground:lighten(@foreground,30%);" +
                        "[dark]foreground:darken(@foreground,30%)");

        // Agregar componentes al panel
        panel.add(lbTitle);
        panel.add(description);
        panel.add(new JLabel("ID"), "gapy 10");
        panel.add(txtId);
        //panel.add(new JLabel("Nombre del cliente"), "gapy 8");
        //panel.add(txtClientName);
        panel.add(new JLabel("Monto"), "gapy 8");
        panel.add(txtAmount);
        panel.add(new JLabel("Tasa de interes"), "gapy 8");
        panel.add(txtInterestRate);
        panel.add(new JLabel("Plazo"), "gapy 8");
        panel.add(txtTerm);
        panel.add(new JLabel("Fecha"), "gapy 8");
        panel.add(txtDate);
        panel.add(new JLabel("Estado:"), "gapy 8");
        panel.add(cbStatus, "growx, wrap");
        panel.add(cmdUpdate, "gapy 20");

        add(panel);
    }

    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField();
        field.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
        return field;
    }

    private boolean validateFields() {
        return  !txtAmount.getText().isEmpty() &&
                !txtInterestRate.getText().isEmpty() &&
                !txtTerm.getText().isEmpty() &&
                !txtDate.getText().isEmpty();
    }

    private JComboBox<String> createComboBox() {
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Activo", "Pagado"});
        comboBox.putClientProperty(FlatClientProperties.STYLE,
                "background:lighten(@background,5%);" +
                        "foreground:@foreground;" +
                        "arc:10");
        return comboBox;
    }

    private void updateLoan() {
        try {
            /*// Convertir el texto de la fecha a LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Formato esperado
            LocalDate date = LocalDate.parse(txtDate.getText(), formatter);

            String clientId = txtClientId.getText(); // ID del cliente asociado al préstamo

            double amount = Double.parseDouble(txtAmount.getText()); // Monto del préstamo
            double interestRate = Double.parseDouble(txtInterestRate.getText()); // Tasa de interés
            int term = Integer.parseInt(txtTerm.getText()); // Plazo en meses

            boolean active = true;

            Loan loan = new Loan(amount, interestRate, term, active, date, Integer.parseInt(clientId), this.user.getId());

            loan.setId(Integer.parseInt(txtId.getText()));
            String status = cbStatus.getSelectedItem().toString();
            loan.setActive("Activo".equals(status));

            // Actualizar el préstamo en la base de datos o en memoria
            this.loanController.updateLoan(Integer.parseInt(txtId.getText()), loan);*/
        } catch (Exception e) {
            // Manejar errores de formato de fecha o campos inválidos
            Notifications.getInstance().show(Notifications.Type.ERROR, "Error: Verifique el formato de la fecha (YYYY-MM-DD).");
        }
    }
}