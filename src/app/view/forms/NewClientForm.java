package app.view.forms;

import app.controller.ClientController;
import app.dto.UserResponseDTO;
import app.exception.ApiException;
import app.model.Client;
import app.model.User;
import app.view.ClientListView;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;

import javax.swing.*;
import java.awt.*;

public class NewClientForm extends JPanel {

    private JTextField txtId;
    private JTextField txtFirstName;
    private JTextField txtSecondName;
    private JTextField txtFirstSurname;
    private JTextField txtSecondSurname;
    private JTextField txtAge;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextField txtAddress;
    private JButton cmdSave;
    private ClientListView listView;
    private UserResponseDTO user;
    private ClientController clientController;

    public NewClientForm(UserResponseDTO user, ClientListView listView) {
        this.user = user;
        this.listView = listView;
        this.clientController = new ClientController();
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));

        // Panel principal
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "[fill,360]"));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:lighten(@background,3%)");

        // Campos de texto
        txtId = createTextField("ID del cliente");
        txtFirstName = createTextField("Primer nombre");
        txtSecondName = createTextField("Segundo nombre (opcional)");
        txtFirstSurname = createTextField("Primer apellido");
        txtSecondSurname = createTextField("Segundo apellido (opcional)");
        txtAge = createTextField("Edad");
        txtEmail = createTextField("Correo electrónico");
        txtPhone = createTextField("Teléfono");
        txtAddress = createTextField("Dirección");

        // Botón Guardar
        cmdSave = new JButton("Guardar");
        cmdSave.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        cmdSave.addActionListener(e -> {
            saveClient();

            //Metodo para refrescar la tabla del padre
            this.listView.refreshTable();

            // Obtener la ventana padre y cerrarla
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }

            Notifications.getInstance().show(Notifications.Type.SUCCESS, "Cliente guardado correctamente");
        });

        // Título y descripción
        JLabel lbTitle = new JLabel("Nuevo Cliente");
        JLabel description = new JLabel("Ingrese los datos del cliente a registrar.");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");
        description.putClientProperty(FlatClientProperties.STYLE, "" +
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
        panel.add(new JLabel("Dirección"), "gapy 8");
        panel.add(txtAddress);
        panel.add(cmdSave, "gapy 20");

        add(panel);
    }

    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField();
        field.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
        return field;
    }

    private void saveClient() {
        try{
            int id = Integer.parseInt(txtId.getText());
            String firstName = txtFirstName.getText();
            String secondName = txtSecondName.getText();
            String firstSurname = txtFirstSurname.getText();
            String secondSurname = txtSecondSurname.getText();
            int age = (txtAge.getText().length() > 0) ? Integer.parseInt(txtAge.getText()) : 0;
            String email = txtEmail.getText();
            String phone = txtPhone.getText();
            String address = txtAddress.getText();

            Client client = new Client(id, firstName, secondName, firstSurname, secondSurname, age, email, phone, address, this.user.getId());

            this.clientController.createClient(client, this.user);
        } catch (ApiException ex) {
            Notifications.getInstance().show(Notifications.Type.ERROR, ex.getMessage());
        }
    }
}