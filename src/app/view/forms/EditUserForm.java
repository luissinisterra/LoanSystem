package app.view.forms;

import app.controller.UserController;
import app.dto.UserResponseDTO;
import app.model.User;
import app.view.UserProfileView;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EditUserForm extends JPanel {

    private JTextField txtId;
    private JTextField txtNames;
    private JTextField txtSurnames;
    private JTextField txtEmail;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cbGender;

    private UserResponseDTO user;
    private UserProfileView profileView;
    private UserController userController;

    public EditUserForm(UserResponseDTO user, UserProfileView profileView) {
        this.user = user;
        this.profileView = profileView;
        this.userController = new UserController();
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));

        // Panel principal
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "[fill,360]"));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "arc:20;" +
                        "[light]background:darken(@background,3%);" +
                        "[dark]background:lighten(@background,3%)");

        JLabel lbTitle = new JLabel("Editar Perfil");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        JLabel description = new JLabel("Actualice sus datos personales.");
        description.putClientProperty(FlatClientProperties.STYLE,
                "[light]foreground:lighten(@foreground,30%);" +
                        "[dark]foreground:darken(@foreground,30%)");

        txtId = createTextField("ID");
        txtId.setText(String.valueOf(user.getId()));

        txtNames = createTextField("Nombres");
        txtNames.setText(user.getNames());

        txtSurnames = createTextField("Apellidos");
        txtSurnames.setText(user.getSurnames());

        txtEmail = createTextField("Correo electrónico");
        txtEmail.setText(user.getEmail());

        txtUsername = createTextField("Nombre de usuario");
        txtUsername.setText(user.getUsername());

        txtPassword = new JPasswordField();
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nueva Contraseña (Opcional)");
        txtPassword.setText("");

        String[] genders = {"Masculino", "Femenino"};
        cbGender = new JComboBox<>(genders);
        cbGender.setSelectedItem(user.getGender());
        cbGender.putClientProperty(FlatClientProperties.STYLE,
                "background:lighten(@background,5%);" +
                        "foreground:@foreground;" +
                        "arc:10");

        // Botón Guardar
        JButton btnSave = new JButton("Guardar Cambios");
        btnSave.putClientProperty(FlatClientProperties.STYLE,
                "[light]background:darken(@background,10%);" +
                        "[dark]background:lighten(@background,10%);" +
                        "borderWidth:0;" +
                        "focusWidth:0;" +
                        "innerFocusWidth:0");

        btnSave.addActionListener(this::saveAction);

        // Agregar componentes al panel
        panel.add(lbTitle);
        panel.add(description);
        panel.add(new JLabel("Nombres"), "gapy 10");
        panel.add(txtNames);
        panel.add(new JLabel("Apellidos"), "gapy 8");
        panel.add(txtSurnames);
        panel.add(new JLabel("Correo electrónico"), "gapy 8");
        panel.add(txtEmail);
        panel.add(new JLabel("Nombre de usuario"), "gapy 8");
        panel.add(txtUsername);
        panel.add(new JLabel("Contraseña"), "gapy 8");
        panel.add(txtPassword);
        panel.add(new JLabel("Género"), "gapy 8");
        panel.add(cbGender, "growx, wrap");
        panel.add(btnSave, "gapy 20");

        add(panel);
    }

    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField();
        field.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
        return field;
    }

    private void saveAction(ActionEvent e) {
        if (validateFields()) {
            updateUser();
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, "Por favor complete los campos obligatorios.");
        }
    }

    private void updateUser() {
        try{
            User user = new User(
                    txtNames.getText(),
                    txtSurnames.getText(),
                    txtEmail.getText(),
                    String.valueOf(txtPassword.getPassword().length == 0 ? "" : new String(txtPassword.getPassword())),
                    txtUsername.getText(),
                    (String) cbGender.getSelectedItem()
            );

            UserResponseDTO userUpdated = this.userController.updateUser(Integer.parseInt(txtId.getText()), user, this.user);

            this.profileView.refreshData(userUpdated);

            Notifications.getInstance().show(Notifications.Type.SUCCESS, "Perfil actualizado correctamente.");

            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }

        } catch (Exception ex) {
            Notifications.getInstance().show(Notifications.Type.ERROR, ex.getMessage());
        }
    }

    private boolean validateFields() {
        return !txtNames.getText().trim().isEmpty() &&
                !txtSurnames.getText().trim().isEmpty() &&
                !txtEmail.getText().trim().isEmpty() &&
                !txtUsername.getText().trim().isEmpty();
    }
}