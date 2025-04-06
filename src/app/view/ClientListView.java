package app.view;

import app.controller.ClientController;
import app.model.Address;
import app.model.Client;
import app.view.forms.EditClientForm;
import app.view.forms.NewClientForm;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;

import javax.crypto.spec.PSource;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class ClientListView extends JPanel {

    private ClientController clientController = new ClientController();
    private DefaultTableModel model;
    private JTable table;

    public ClientListView() {
        init();
        this.clientController = new ClientController();
    }

    private void init() {
        setLayout(new MigLayout("fill, insets 20", "[grow]", "[grow]"));

        // Panel principal con bordes redondeados
        JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 20", "[grow]"));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "background:$Menu.background;" +
                        "arc:20");

        // Título
        JLabel lbTitle = new JLabel("Gestión de Clientes");
        lbTitle.putClientProperty(FlatClientProperties.STYLE,
                "font:bold +18");

        // Componentes principales
        JPanel searchPanel = createSearchBar();
        JPanel statsPanel = createStatsPanel();
        JScrollPane scrollPane = createTablePanel();
        JPanel buttonPanel = createButtonPanel();

        // Agregar componentes al panel principal
        panel.add(lbTitle, "span, growx, wrap");
        panel.add(searchPanel, "growx, wrap");
        panel.add(statsPanel, "growx, wrap");
        panel.add(scrollPane, "grow, push, wrap");
        panel.add(buttonPanel, "growx");

        add(panel, "grow");
    }

    // Método para crear la barra de búsqueda
    private JPanel createSearchBar() {
        JTextField searchField = new JTextField();
        searchField.putClientProperty(FlatClientProperties.STYLE,
                "showClearButton:true;" +
                        "background:lighten(@background,5%);" +
                        "foreground:@foreground;" +
                        "arc:10");
        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Buscar clientes...");

        // Botón de búsqueda
        JButton searchButton = createActionButton("", "app/icon/svg/search-icon.svg");
        searchButton.setPreferredSize(new Dimension(100, 35));

        searchButton.putClientProperty(FlatClientProperties.STYLE,
                "[light]background:darken(@background,10%);" +
                        "[dark]background:lighten(@background,10%);" +
                        "foreground:@foreground;" +
                        "borderWidth:0;" +
                        "focusWidth:0;" +
                        "innerFocusWidth:0;" +
                        "arc:10");

        searchButton.setFont(searchButton.getFont().deriveFont(Font.BOLD, 14f));
        searchButton.addActionListener(e -> {
            String query = searchField.getText(); // Obtener el texto del campo de búsqueda
            filterTable(query); // Filtrar la tabla con el texto actual
        });

        // Panel principal para la barra de búsqueda
        JPanel searchPanel = new JPanel(new BorderLayout());
        //searchPanel.add(iconLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST); // Agregar el botón al lado derecho
        searchPanel.putClientProperty(FlatClientProperties.STYLE,
                "background:lighten(@background,5%);" +
                        "arc:10");

        return searchPanel;
    }

    private void filterTable(String query) {
        JOptionPane.showMessageDialog(null, query);
    }

    // Método para crear las tarjetas de resumen
    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        statsPanel.putClientProperty(FlatClientProperties.STYLE,
                "background:$Menu.background;" +
                        "arc:10");

        // Icono para la sección de clientes totales
        FlatSVGIcon totalClientsIcon = new FlatSVGIcon("app/icon/svg/people-icon.svg").derive(40, 40);
        JPanel totalClients = createStatCard("Total de Clientes", totalClientsIcon, " 120");

        // Icono para la sección de clientes activos
        FlatSVGIcon activesClientsIcon = new FlatSVGIcon("app/icon/svg/quality-icon.svg").derive(40, 40);
        JPanel activeClients = createStatCard("Clientes Activos", activesClientsIcon, " 90");

        statsPanel.add(totalClients);
        statsPanel.add(activeClients);

        return statsPanel;
    }

    // Método para crear una tarjeta de estadísticas
    private JPanel createStatCard(String title, Icon icon, String value) {
        JPanel card = new JPanel(new MigLayout("wrap, fillx, insets 10", "[grow]"));
        card.putClientProperty(FlatClientProperties.STYLE,
                "background:lighten(@background,5%);" +
                        "arc:10");

        // Título
        JLabel titleLabel = new JLabel(title);
        titleLabel.putClientProperty(FlatClientProperties.STYLE,
                "font:bold +14");

        // Valor con ícono
        JPanel valuePanel = new JPanel(new BorderLayout());
        valuePanel.setOpaque(false); // Hacer el panel transparente
        JLabel iconLabel = new JLabel(icon);
        JLabel valueLabel = new JLabel(value);
        valueLabel.putClientProperty(FlatClientProperties.STYLE,
                "font:bold +18");

        valuePanel.add(iconLabel, BorderLayout.WEST);
        valuePanel.add(valueLabel, BorderLayout.CENTER);

        card.add(titleLabel, "growx");
        card.add(valuePanel, "growx");

        return card;
    }

    // Método para crear la tabla
    public JScrollPane createTablePanel() {
        List<Client> clients = this.clientController.getAllClients();
        model = new DefaultTableModel();
        model.addColumn("Documento");
        model.addColumn("Nombre");
        model.addColumn("Correo");
        model.addColumn("Teléfono");
        model.addColumn("Dirección");
        model.addColumn("Activo");


        if (clients != null && !clients.isEmpty()) {
            for (Client client : clients) {
                model.addRow(new Object[]{
                        client.getId(),
                        client.getFirstName() + " " + client.getFirstSurname(),
                        client.getEmail(),
                        client.getPhone(),
                        client.getAddress().getCity() + " " + client.getAddress().getStreet() + " " + client.getAddress().getPostalCode(),
                        client.isActive() ? "Activo" : "Inactivo"
                });
            }
        }

        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que la tabla no sea editable
            }
        };

        // Estilo de la tabla
        table.setRowHeight(35); // Altura de las filas
        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);
        table.getColumnModel().getColumn(4).setPreferredWidth(200);
        table.getColumnModel().getColumn(5).setPreferredWidth(200);
        table.putClientProperty(FlatClientProperties.STYLE,
                "showHorizontalLines:true;" +
                        "showVerticalLines:true;" +
                        "font:+1");

        // Header con estilo minimalista
        JTableHeader header = table.getTableHeader();
        header.putClientProperty(FlatClientProperties.STYLE,
                "font:bold +1;" +
                        "height:35");

        // ScrollPane para la tabla
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(750, 400));

        return scrollPane;
    }

    // Método para crear el panel de botones
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.putClientProperty(FlatClientProperties.STYLE,
                "background:$Menu.background;" +
                        "arc:10");

        JButton newButton = createActionButton("Nuevo Cliente", "app/icon/svg/create-icon.svg");
        JButton editButton = createActionButton("Editar Cliente", "app/icon/svg/update-icon.svg");
        JButton deleteButton = createActionButton("Eliminar Cliente", "app/icon/svg/delete-icon.svg");

        // Asignar acciones a los botones
        setupNewButtonAction(newButton);
        setupEditButtonAction(editButton);
        setupDeleteButtonAction(deleteButton);

        buttonPanel.add(newButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        return buttonPanel;
    }

    // Método para configurar la acción del botón "Nuevo"
    private void setupNewButtonAction(JButton button) {
        button.addActionListener(e -> {
            JFrame frame = new JFrame("Nuevo Cliente");
            frame.setContentPane(new NewClientForm());
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    // Método para configurar la acción del botón "Editar"
    private void setupEditButtonAction(JButton button) {
        button.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String id = (String) table.getValueAt(selectedRow, 0);

                Client client = this.clientController.getClientById(id);
                String firstName = client.getFirstName();
                String secondName = client.getFirstSurname();
                String firstSurname = client.getFirstSurname();
                String secondSurname = client.getSecondSurname();
                int age = client.getAge();
                String email = client.getEmail();
                String phone = client.getPhone();

                String country = client.getAddress().getCountry();
                String deparment = client.getAddress().getDeparment();
                String city = client.getAddress().getCity();
                String street = client.getAddress().getStreet();
                String postalCode = client.getAddress().getPostalCode();

                Address address = new Address(country, deparment, city, street, postalCode);

                String status = client.isActive() ? "Activo" : "Inactivo";

                JFrame frame = new JFrame("Editar Cliente");
                frame.setContentPane(new EditClientForm(
                        id,
                        firstName,
                        secondName,
                        firstSurname,
                        secondSurname,
                        age,
                        email,
                        phone,
                        address,
                        status
                ));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    // Método para configurar la acción del botón "Eliminar"
    private void setupDeleteButtonAction(JButton button) {
        button.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String id = (String) table.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este cliente?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    this.clientController.deleteClient(id);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, "El cliente ha sido eliminado con éxito.");
                }
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, "Seleccione un cliente para eliminar. Advertencia");
            }
        });
    }

    // Método para crear un botón con ícono
    private JButton createActionButton(String text, String iconPath) {
        JButton button = new JButton(text, new FlatSVGIcon(iconPath).derive(20, 20));
        button.putClientProperty(FlatClientProperties.STYLE,
                "[light]background:darken(@background,10%);" +
                        "[dark]background:lighten(@background,10%);" +
                        "foreground:@foreground;" +
                        "borderWidth:0;" +
                        "focusWidth:0;" +
                        "innerFocusWidth:0;" +
                        "arc:10");
        button.setPreferredSize(new Dimension(180, 40));
        button.setFont(button.getFont().deriveFont(Font.BOLD, 14f));
        return button;
    }
}