package app.views.forms;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class EditLoanForm extends JPanel {

    private JTextField txtClient;
    private JTextField txtAmount;
    private JTextField txtInterestRate;
    private JTextField txtTerm;
    private JComboBox<String> cbStatus;
    private JTextField txtDate;

    public EditLoanForm(String client, String amount, String interestRate, String term, String status, String date) {
        init(client, amount, interestRate, term, status, date);
    }

    private void init(String client, String amount, String interestRate, String term, String status, String date) {
        setLayout(new MigLayout("fill, insets 20", "[center]", "[center]"));

        // Panel principal con bordes redondeados
        JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 35 45 30 45", "[fill, 360]"));
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Menu.background;" +
                "arc:20");

        // Título
        JLabel lbTitle = new JLabel("Editar Préstamo");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +16");

        // Campos del formulario
        txtClient = createFormField("Cliente", client);
        txtAmount = createFormField("Monto", amount);
        txtInterestRate = createFormField("Tasa de Interés", interestRate);
        txtTerm = createFormField("Plazo (meses)", term);
        cbStatus = new JComboBox<>(new String[]{"Activo", "Inactivo", "Pagado"});
        cbStatus.setSelectedItem(status);
        txtDate = createFormField("Fecha", date);

        // Botones de acción
        JButton btnSave = createActionButton("Guardar");
        JButton btnCancel = createActionButton("Cancelar");

        // Acción para el botón Guardar
        btnSave.addActionListener(e -> {
            // Aquí puedes implementar la lógica para guardar los cambios
            String updatedClient = txtClient.getText();
            String updatedAmount = txtAmount.getText();
            String updatedInterestRate = txtInterestRate.getText();
            String updatedTerm = txtTerm.getText();
            String updatedStatus = (String) cbStatus.getSelectedItem();
            String updatedDate = txtDate.getText();

            System.out.println("Préstamo actualizado:");
            System.out.println("Cliente: " + updatedClient);
            System.out.println("Monto: " + updatedAmount);
            System.out.println("Tasa de Interés: " + updatedInterestRate);
            System.out.println("Plazo: " + updatedTerm);
            System.out.println("Estado: " + updatedStatus);
            System.out.println("Fecha: " + updatedDate);

            // Cerrar el formulario después de guardar
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.dispose();
        });

        // Acción para el botón Cancelar
        btnCancel.addActionListener(e -> {
            // Cerrar el formulario sin guardar cambios
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.dispose();
        });

        // Agregar componentes al panel
        panel.add(lbTitle, "growx, wrap");
        panel.add(new JLabel("Cliente:"), "gapy 8");
        panel.add(txtClient, "growx, wrap");
        panel.add(new JLabel("Monto:"), "gapy 8");
        panel.add(txtAmount, "growx, wrap");
        panel.add(new JLabel("Tasa de Interés:"), "gapy 8");
        panel.add(txtInterestRate, "growx, wrap");
        panel.add(new JLabel("Plazo (meses):"), "gapy 8");
        panel.add(txtTerm, "growx, wrap");
        panel.add(new JLabel("Estado:"), "gapy 8");
        panel.add(cbStatus, "growx, wrap");
        panel.add(new JLabel("Fecha:"), "gapy 8");
        panel.add(txtDate, "growx, wrap");
        panel.add(btnSave, "split 2, gapy 20");
        panel.add(btnCancel);

        add(panel, "grow");
    }

    private JTextField createFormField(String placeholder, String initialValue) {
        JTextField field = new JTextField(initialValue);
        field.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:lighten(@background,5%);" +
                "foreground:@foreground;" +
                "arc:10");
        field.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
        return field;
    }

    private JButton createActionButton(String text) {
        JButton button = new JButton(text);
        button.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "foreground:@foreground;" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0;" +
                "arc:10");
        return button;
    }
}