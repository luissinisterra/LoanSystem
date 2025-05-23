package app.view;

import app.controller.UserController;
import app.model.User;
import app.view.forms.EditUserForm;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;

import javax.swing.*;
import java.awt.*;

public class UserProfileView extends JPanel {

    private User user;
    private UserController userController;

    private JLabel lbNames, lbSurnames, lbEmail, lbUsername, lbGender;
    private JButton btnEdit, btnDelete;

    public UserProfileView(User user) {
        this.user = user;
        this.userController = new UserController();
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill, insets 20", "[center]", "[center]"));

        JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 35 45 30 45", "[fill,360]"));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "background:$Menu.background;" +
                        "arc:20");

        JLabel lbTitle = new JLabel("Perfil del Usuario");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +18");

        JLabel description = new JLabel("Revise y gestione su información personal.");
        description.putClientProperty(FlatClientProperties.STYLE, "font:italic;");

        lbNames = createInfoLabel("Nombres:", user.getNames());
        lbSurnames = createInfoLabel("Apellidos:", user.getSurnames());
        lbEmail = createInfoLabel("Correo electrónico:", user.getEmail());
        lbUsername = createInfoLabel("Nombre de usuario:", user.getUsername());
        lbGender = createInfoLabel("Género:", user.getGender());

        btnEdit = createActionButton("Editar Perfil", "app/icon/svg/update-icon.svg");
        btnDelete = createActionButton("Eliminar Cuenta", "app/icon/svg/delete-icon.svg");

        setupEditButtonAction(btnEdit);
        setupDeleteButtonAction(btnDelete);

        panel.add(lbTitle, "span, center");
        panel.add(description, "span, center, gapy 10");
        panel.add(lbNames, "gapy 12");
        panel.add(lbSurnames);
        panel.add(lbEmail);
        panel.add(lbUsername);
        panel.add(lbGender);
        panel.add(btnEdit, "gapy 20, split 2");
        panel.add(btnDelete);

        add(panel);
    }

    private JLabel createInfoLabel(String label, String value) {
        JLabel lbl = new JLabel("<html><b>" + label + "</b><br>" + value + "</html>");
        lbl.putClientProperty(FlatClientProperties.STYLE, "font:+1");
        return lbl;
    }

    private JButton createActionButton(String text, String iconPath) {
        JButton btn = new JButton(text, new FlatSVGIcon(iconPath).derive(20, 20));
        btn.setPreferredSize(new Dimension(180, 40));
        btn.setFont(btn.getFont().deriveFont(Font.BOLD, 14f));
        btn.putClientProperty(FlatClientProperties.STYLE,
                "[light]background:darken(@background,10%);" +
                        "[dark]background:lighten(@background,10%);" +
                        "foreground:@foreground;" +
                        "arc:10;" +
                        "borderWidth:0");
        return btn;
    }

    // Método para configurar el botón de editar
    private void setupEditButtonAction(JButton button) {
        button.addActionListener(e -> {
                    JFrame frame = new JFrame("Editar Perfil");
                    frame.setContentPane(new EditUserForm(user, this));
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
        );
    }

    // Método para configurar el botón de eliminar
    private void setupDeleteButtonAction(JButton button) {
        button.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,"¿Está seguro de eliminar su cuenta? Esta acción no se puede deshacer.", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                this.userController.deleteUser(user.getId());
                Notifications.getInstance().show(Notifications.Type.WARNING, "Funcionalidad de eliminación pendiente.");
            }
        });
    }

    public void refreshData(User updatedUser) {
        this.user = updatedUser;
        lbNames.setText("<html><b>Nombres:</b><br>" + user.getNames() + "</html>");
        lbSurnames.setText("<html><b>Apellidos:</b><br>" + user.getSurnames() + "</html>");
        lbEmail.setText("<html><b>Correo electrónico:</b><br>" + user.getEmail() + "</html>");
        lbUsername.setText("<html><b>Nombre de usuario:</b><br>" + user.getUsername() + "</html>");
        lbGender.setText("<html><b>Género:</b><br>" + user.getGender() + "</html>");
    }
}
