package app.view.forms;

import app.controller.ClientController;
import app.controller.LoanController;
import app.exception.ApiException;
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
import java.util.List;

public class NewLoanForm extends JPanel {
    private JTextField txtClient;
    private JTextField txtAmount;
    private JTextField txtInterestRate;
    private JTextField txtTerm;
    private JTextField txtDate;
    private JButton cmdSave;
    private LoanListView listView;
    private LoanController loanController;
    private ClientController clientController;
    private User user;

    public NewLoanForm(User user, LoanListView listView) {
        this.user = user;
        this.listView = listView;
        this.loanController = new LoanController();
        this.clientController = new ClientController();
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));

        // Panel principal con bordes redondeados
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "[fill,360]"));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "arc:20;" +
                        "[light]background:darken(@background,3%);" +
                        "[dark]background:lighten(@background,3%)");

        // Título
        JLabel lbTitle = new JLabel("Nuevo Préstamo");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        // Descripción
        JLabel description = new JLabel("Ingrese los datos del nuevo préstamo.");
        description.putClientProperty(FlatClientProperties.STYLE,
                "[light]foreground:lighten(@foreground,30%);" +
                        "[dark]foreground:darken(@foreground,30%)");

        // Campos de texto
        txtClient = createTextField("Cliente");
        txtClient.setEditable(false); // Desactivar la edición del campo de texto
        txtAmount = createTextField("Monto");
        txtInterestRate = createTextField("Tasa de Interés (%)");
        txtTerm = createTextField("Plazo (meses)");
        txtDate = createTextField("Fecha (YYYY-MM-DD)");

        // Botón "Buscar Cliente"
        JButton btnSelectClient = new JButton("Buscar Cliente");
        btnSelectClient.putClientProperty(FlatClientProperties.STYLE,
                "[light]background:darken(@background,10%);" +
                        "[dark]background:lighten(@background,10%);" +
                        "borderWidth:0;" +
                        "focusWidth:0;" +
                        "innerFocusWidth:0");
        btnSelectClient.addActionListener(e -> {
            List<Client> clients = this.clientController.getAllClients(this.user);
            ClientSelectorDialog dialog = new ClientSelectorDialog((JFrame) SwingUtilities.getWindowAncestor(this), clients);
            dialog.setVisible(true);
            String selectedClientId = dialog.getSelectedClientId();
            if (selectedClientId != null) {
                txtClient.setText(selectedClientId); // Asignar el ID del cliente seleccionado
            }
        });

        // Configurar el tamaño máximo del botón
        btnSelectClient.setMaximumSize(new Dimension(120, btnSelectClient.getPreferredSize().height));

        // Botón Guardar
        cmdSave = new JButton("Guardar");
        cmdSave.putClientProperty(FlatClientProperties.STYLE,
                "[light]background:darken(@background,10%);" +
                        "[dark]background:lighten(@background,10%);" +
                        "borderWidth:0;" +
                        "focusWidth:0;" +
                        "innerFocusWidth:0");
        cmdSave.addActionListener(e -> {
            if (validateFields()) {
                saveLoan();
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, "Por favor complete todos los campos obligatorios");
            }
        });

        // Agregar componentes al panel
        panel.add(lbTitle, "growx, wrap");
        panel.add(description, "growx, wrap");
        panel.add(new JLabel("Cliente:"), "gapy 8");
        panel.add(txtClient, "split 2"); // Campo de texto para el cliente
        panel.add(btnSelectClient); // Botón "Buscar Cliente"
        panel.add(new JLabel("Monto:"), "gapy 8");
        panel.add(txtAmount, "growx, wrap");
        panel.add(new JLabel("Tasa de Interés:"), "gapy 8");
        panel.add(txtInterestRate, "growx, wrap");
        panel.add(new JLabel("Plazo (meses):"), "gapy 8");
        panel.add(txtTerm, "growx, wrap");
        panel.add(new JLabel("Fecha:"), "gapy 8");
        panel.add(txtDate, "growx, wrap");
        panel.add(cmdSave, "gapy 20");

        add(panel);
    }

    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField();
        field.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
        return field;
    }

    private JComboBox<String> createComboBox() {
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Activo", "Inactivo", "Pagado"});
        comboBox.putClientProperty(FlatClientProperties.STYLE,
                "background:lighten(@background,5%);" +
                        "foreground:@foreground;" +
                        "arc:10");
        return comboBox;
    }

    private boolean validateFields() {
       return !txtClient.getText().isEmpty() &&
                !txtAmount.getText().isEmpty() &&
                !txtInterestRate.getText().isEmpty() &&
                !txtTerm.getText().isEmpty() &&
                !txtDate.getText().isEmpty();
    }

    private void setupClientSelection() {
        JButton btnSelectClient = new JButton("Buscar Cliente");
        List<Client> clients = this.clientController.getAllClients(this.user);
        btnSelectClient.addActionListener(e -> {
            ClientSelectorDialog dialog = new ClientSelectorDialog((JFrame) SwingUtilities.getWindowAncestor(this), clients);
            dialog.setVisible(true);
            String selectedClientId = dialog.getSelectedClientId();
            if (selectedClientId != null) {
                txtClient.setText(selectedClientId);
            }
        });
        add(btnSelectClient);
    }

    private void saveLoan() {
        try {
            // Obtener los valores de los campos del formulario
            String clientId = txtClient.getText(); // ID del cliente asociado al préstamo

            double amount = Double.parseDouble(txtAmount.getText()); // Monto del préstamo
            double interestRate = Double.parseDouble(txtInterestRate.getText()); // Tasa de interés
            int term = Integer.parseInt(txtTerm.getText()); // Plazo en meses
            boolean active = true;
            LocalDate date = LocalDate.parse(txtDate.getText()); // Fecha del préstamo

            // Crear un objeto Loan con los datos ingresados
            Loan loan = new Loan(amount, interestRate, term, active, date, Integer.parseInt(clientId), this.user.getId());

            // Guardar el préstamo utilizando el controlador
            this.loanController.createLoan(loan, this.user);

            // Refrescar la tabla del padre
            this.listView.refreshTable();

            Notifications.getInstance().show(Notifications.Type.SUCCESS, "Préstamo guardado correctamente");

            // Cerrar la ventana después de guardar
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }

        } catch (Exception e) {
            Notifications.getInstance().show(Notifications.Type.ERROR, e.getMessage());
        }
    }
}