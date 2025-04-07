package app.view;

import app.controller.ClientController;
import app.model.Client;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AllClientsView extends JPanel {
    private final ClientController clientController = new ClientController();

    public AllClientsView() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill, insets 20", "[grow]", "[grow]"));

        // Panel principal con bordes redondeados
        JPanel mainPanel = new JPanel(new MigLayout("wrap, fillx, insets 20", "[grow]", "[]20[grow]"));
        mainPanel.putClientProperty(FlatClientProperties.STYLE,
                "background:$Menu.background;" +
                        "arc:20");

        // Panel del título
        JPanel titlePanel = new JPanel(new MigLayout("wrap, insets 0", "[]", "[]2[]"));
        titlePanel.setOpaque(false);
        titlePanel.putClientProperty(FlatClientProperties.STYLE,
                "background:$Menu.background");

        JLabel lbTitle = new JLabel("Clientes");
        lbTitle.putClientProperty(FlatClientProperties.STYLE,
                "font:bold +18;" +
                        "foreground:@foreground");

        JLabel lbSubtitle = new JLabel("Consulta rápida del historial y estado de todos los clientes registrados.");
        lbSubtitle.putClientProperty(FlatClientProperties.STYLE,
                "font:italic +9;" +
                        "foreground:lighten(@foreground,50%)");

        titlePanel.add(lbTitle, "gapbottom 2, wrap, align left");
        titlePanel.add(lbSubtitle, "align left");

        // Panel para las cards
        JPanel cardsPanel = new JPanel(new MigLayout("wrap 4, gap 15 15", "[grow][grow][grow][grow]", ""));
        cardsPanel.setOpaque(false);
        cardsPanel.putClientProperty(FlatClientProperties.STYLE,
                "background:lighten(@background,3%)");

        // Obtener clientes
        List<Client> clients = clientController.getAllClients();
        if (clients != null && !clients.isEmpty()) {
            for (Client client : clients) {
                cardsPanel.add(createClientCard(client), "grow");
            }
        } else {
            JLabel noClientsLabel = new JLabel("No hay clientes registrados.");
            noClientsLabel.putClientProperty(FlatClientProperties.STYLE,
                    "font:+14;" +
                            "foreground:@disabledText");
            cardsPanel.add(noClientsLabel, "span, align center");
        }

        // Agregar componentes al mainPanel
        mainPanel.add(titlePanel, "growx");
        mainPanel.add(cardsPanel, "grow, push");

        add(mainPanel, "grow");
    }

    private JPanel createClientCard(Client client) {
        JPanel card = new JPanel(new MigLayout("wrap, fillx, insets 16", "[grow]", "[]10[]20[]"));
        card.setPreferredSize(new Dimension(220, 140)); // Más grueso
        card.putClientProperty(FlatClientProperties.STYLE,
                "background:lighten(@background,6%);" +
                        "arc:18");

        // Sombra sutil
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Nombre completo
        JLabel nameLabel = new JLabel(client.getFirstName() + " " + client.getFirstSurname());
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nameLabel.putClientProperty(FlatClientProperties.STYLE,
                "foreground:darken(@foreground,5%)");

        // Email (opcional si lo tiene)
        JLabel emailLabel = new JLabel(client.getEmail() != null ? client.getEmail() : "Correo no disponible");
        emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        emailLabel.putClientProperty(FlatClientProperties.STYLE,
                "foreground:lighten(@foreground,40%)");

        // Botón "Ver detalles"
        JButton detailsButton = new JButton("Ver detalles");
        detailsButton.putClientProperty(FlatClientProperties.STYLE,
                "background:lighten(@background,8%);" +
                        "foreground:@foreground;" +
                        "borderWidth:0;" +
                        "arc:12;" +
                        "focusWidth:0;" +
                        "font:medium +0");
        detailsButton.setPreferredSize(new Dimension(120, 32));

        detailsButton.addActionListener(e -> {
            JFrame frame = new JFrame("Detalles del Cliente");
            frame.setContentPane(new ClientDetailsView(client.getId()));
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

        // Agregar al panel
        card.add(nameLabel, "growx, align center");
        card.add(emailLabel, "growx, align center");
        card.add(detailsButton, "align center");

        return card;
    }

}
