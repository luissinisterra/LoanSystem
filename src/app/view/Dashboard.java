package app.view;

import app.dto.UserResponseDTO;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JPanel {

    private final UserResponseDTO user;

    public Dashboard(UserResponseDTO user) {
        this.user = user;
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill, wrap", "[center]", "[top]20[top]"));

        JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 60", "[center]"));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "[light]background:darken(@background,3%);" +
                        "[dark]background:lighten(@background,3%);" +
                        "arc:20");

        // Ícono
        JLabel iconLabel = new JLabel(new FlatSVGIcon("app/icon/svg/welcome-icon.svg", 100, 100));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Título de bienvenida estilizado
        JLabel title = new JLabel("<html><div style='text-align:center;'>"
                + "<h1 style='font-size:26px; font-weight:bold; margin: 10px 0;'>¡Bienvenido(a), "
                + user.getUsername() + "!</h1></div></html>");
        title.putClientProperty(FlatClientProperties.STYLE, "font:+2");

        // Descripción larga estilizada con CSS inline
        JLabel description = new JLabel("<html><div style='text-align:justify; max-width:700px; padding: 10px 30px;'>"
                + "<p style='font-size:15px; font-family:sans-serif; line-height:1.6; color:#555;'>"
                + "Esta aplicación está diseñada especialmente para ayudarte a gestionar "
                + "de forma sencilla y eficiente los <b>préstamos personales</b>.<br><br>"
                + "Con ella podrás registrar nuevos clientes, crear y administrar préstamos, "
                + "seguir su estado y mantener todo organizado en un solo lugar.<br><br>"
                + "Cada préstamo puede estar <b>activo</b> o <b>pagado</b>, y puedes revisar el historial "
                + "completo de operaciones en cualquier momento.<br><br>"
                + "La interfaz es <b>intuitiva, rápida y segura</b>, ideal para usuarios que desean "
                + "tener control total de sus operaciones financieras sin complicaciones."
                + "</p></div></html>");

        description.putClientProperty(FlatClientProperties.STYLE,
                "font:+1");

        // Añadir componentes al panel central
        panel.add(iconLabel, "align center");
        panel.add(title, "align center, gapy 10");
        panel.add(description, "align center, growx");

        add(panel, "grow");
    }
}
