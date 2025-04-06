package app.view.forms;

import app.controller.ClientController;
import app.model.Address;
import app.model.Client;
import app.view.ClientListView;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;

import javax.swing.*;
import java.awt.*;

public class EditClientForm extends JPanel {

    private JTextField txtId;
    private JTextField txtFirstName;
    private JTextField txtSecondName;
    private JTextField txtFirstSurname;
    private JTextField txtSecondSurname;
    private JTextField txtAge;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextField txtCountry;
    private JTextField txtDepartment;
    private JTextField txtCity;
    private JTextField txtStreet;
    private JTextField txtPostalCode;
    private JButton cmdUpdate;
    private JComboBox<String> cbStatus;
    private ClientController clientController;

    public EditClientForm(String id, String firstName, String secondName, String firstSurname, String secondSurname,
                          int age, String email, String phone, Address address, String status) {
        init(id, firstName, secondName, firstSurname, secondSurname, age, email, phone, address, status);
        this.clientController = new ClientController();
    }

    private void init(String id, String firstName, String secondName, String firstSurname, String secondSurname,
                      int age, String email, String phone, Address address, String status) {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));

        // Panel principal
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "[fill,360]"));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "arc:20;" +
                        "[light]background:darken(@background,3%);" +
                        "[dark]background:lighten(@background,3%)");

        // Campos de texto prellenados con los datos del cliente
        txtId = createTextField("ID del cliente");
        txtId.setText(id);
        txtId.setEditable(false); // El ID no debe ser editable

        txtFirstName = createTextField("Primer nombre");
        txtFirstName.setText(firstName);

        txtSecondName = createTextField("Segundo nombre (opcional)");
        txtSecondName.setText(secondName);

        txtFirstSurname = createTextField("Primer apellido");
        txtFirstSurname.setText(firstSurname);

        txtSecondSurname = createTextField("Segundo apellido (opcional)");
        txtSecondSurname.setText(secondSurname);

        txtAge = createTextField("Edad");
        txtAge.setText(String.valueOf(age));

        txtEmail = createTextField("Correo electrónico");
        txtEmail.setText(email);

        txtPhone = createTextField("Teléfono");
        txtPhone.setText(phone);

        txtCountry = createTextField("País");
        txtCountry.setText(address.getCountry());

        txtDepartment = createTextField("Departamento");
        txtDepartment.setText(address.getDeparment());

        txtCity = createTextField("Ciudad");
        txtCity.setText(address.getCity());

        txtStreet = createTextField("Calle");
        txtStreet.setText(address.getStreet());

        txtPostalCode = createTextField("Código postal");
        txtPostalCode.setText(address.getPostalCode());

        cbStatus = createComboBox();
        cbStatus.setSelectedItem(status);

        // Botón Actualizar
        cmdUpdate = new JButton("Actualizar");
        cmdUpdate.putClientProperty(FlatClientProperties.STYLE,
                "[light]background:darken(@background,10%);" +
                        "[dark]background:lighten(@background,10%);" +
                        "borderWidth:0;" +
                        "focusWidth:0;" +
                        "innerFocusWidth:0");

        cmdUpdate.addActionListener(e -> {
            if (validateFields()) {
                updateClient();
                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Cliente actualizado correctamente");

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
        JLabel lbTitle = new JLabel("Editar Cliente");
        JLabel description = new JLabel("Actualice los datos del cliente seleccionado.");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");
        description.putClientProperty(FlatClientProperties.STYLE,
                "[light]foreground:lighten(@foreground,30%);" +
                        "[dark]foreground:darken(@foreground,30%)");

        // Agregar componentes al panel
        panel.add(lbTitle);
        panel.add(description);
        panel.add(new JLabel("ID"), "gapy 10");
        panel.add(txtId);
        panel.add(new JLabel("Nombre completo"), "gapy 8");
        panel.add(txtFirstName, "split 2");
        panel.add(txtSecondName);
        panel.add(new JLabel("Apellidos"), "gapy 8");
        panel.add(txtFirstSurname, "split 2");
        panel.add(txtSecondSurname);
        panel.add(new JLabel("Edad"), "gapy 8");
        panel.add(txtAge);
        panel.add(new JLabel("Correo electrónico"), "gapy 8");
        panel.add(txtEmail);
        panel.add(new JLabel("Teléfono"), "gapy 8");
        panel.add(txtPhone);
        panel.add(new JLabel("País"), "gapy 8");
        panel.add(txtCountry);
        panel.add(new JLabel("Departamento"), "gapy 8");
        panel.add(txtDepartment);
        panel.add(new JLabel("Ciudad"), "gapy 8");
        panel.add(txtCity);
        panel.add(new JLabel("Calle"), "gapy 8");
        panel.add(txtStreet);
        panel.add(new JLabel("Código postal"), "gapy 8");
        panel.add(txtPostalCode);
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
        return !txtFirstName.getText().isEmpty() &&
                !txtFirstSurname.getText().isEmpty() &&
                !txtAge.getText().isEmpty() &&
                !txtEmail.getText().isEmpty() &&
                !txtPhone.getText().isEmpty() &&
                !txtCountry.getText().isEmpty() &&
                !txtDepartment.getText().isEmpty() &&
                !txtCity.getText().isEmpty() &&
                !txtStreet.getText().isEmpty() &&
                !txtPostalCode.getText().isEmpty();
    }

    private JComboBox<String> createComboBox() {
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Activo", "Inactivo"});
        comboBox.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:lighten(@background,5%);" +
                "foreground:@foreground;" +
                "arc:10");
        return comboBox;
    }

    private void updateClient() {
        // Crear un objeto Address con los datos ingresados
        Address address = new Address(
                txtCountry.getText(),
                txtDepartment.getText(),
                txtCity.getText(),
                txtStreet.getText(),
                txtPostalCode.getText()
        );

        // Crear un objeto Client con los datos actualizados
        Client client = new Client(
                txtId.getText(),
                txtFirstName.getText(),
                txtSecondName.getText(),
                txtFirstSurname.getText(),
                txtSecondSurname.getText(),
                Integer.parseInt(txtAge.getText()),
                txtEmail.getText(),
                txtPhone.getText(),
                address
        );

        String status = cbStatus.getSelectedItem().toString();
        client.setActive("Activo".equals(status));

        // Actualizar el cliente en la base de datos o en memoria
        this.clientController.updateClient(txtId.getText(), client);
    }
}