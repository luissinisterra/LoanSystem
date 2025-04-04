package app.view;

import app.controller.ClientController;
import app.model.Client;
import app.view.forms.EditClientForm;
import app.view.forms.NewClientForm;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;

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
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Menu.background;" +
                "arc:20");

        // Título
        JLabel lbTitle = new JLabel("Gestión de Clientes");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +18");

        // Búsqueda rápida
        JTextField searchField = new JTextField();
        searchField.putClientProperty(FlatClientProperties.STYLE, ""
                + "showClearButton:true;" +
                "background:lighten(@background,5%);" +
                "foreground:@foreground;" +
                "arc:10");

        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Buscar clientes...");

        //Icono para la baraa de busqueda
        FlatSVGIcon searchIcon = new FlatSVGIcon("app/view/menu/icon/search-icon.svg").derive(20, 20);
        FlatSVGIcon.ColorFilter f = new FlatSVGIcon.ColorFilter();
        searchIcon.setColorFilter(f);

        JLabel iconLabel = new JLabel(searchIcon);
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(iconLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:lighten(@background,5%);" +
                "arc:10");

        // Tarjetas de resumen
        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        statsPanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Menu.background;" +
                "arc:10");

        //Icono para la seccion de clientes totales
        FlatSVGIcon totalClientsIcon = new FlatSVGIcon("app/view/menu/icon/people-icon.svg").derive(40, 40);
        JLabel totalClientsIconLabel = new JLabel(totalClientsIcon);
        JPanel totalClients = createStatCard("Total de Clientes", totalClientsIcon ," 120");

        //Icono para la seccion de clientes activos
        FlatSVGIcon activesClientsIcon = new FlatSVGIcon("app/view/menu/icon/quality-icon.svg").derive(40, 40);
        JLabel activesClientsIconLabel = new JLabel(totalClientsIcon);
        JPanel activeClients = createStatCard("Clientes Activos", activesClientsIcon," 90");

        statsPanel.add(totalClients);
        statsPanel.add(activeClients);

        // Modelo de la tabla
        List<Client> clients = this.clientController.getAllClients();
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Correo");
        model.addColumn("Teléfono");
        model.addColumn("Dirección");

        for (Client client : clients) {
            model.addRow(new Object[]{client.getId(), client.getFirstName()+ " " + client.getFirstSurname(), client.getEmail(), client.getEmail(), client.getAddress().getCity() + " " + client.getAddress().getStreet() + " " + client.getAddress().getPostalCode()});
        }

        // Crear la tabla con el modelo
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

        // Botones de acción
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Menu.background;" +
                "arc:10");

        JButton newButton = createActionButton("Nuevo Cliente", "app/view/menu/icon/create-icon.svg");
        JButton editButton = createActionButton("Editar Cliente", "app/view/menu/icon/update-icon.svg");
        JButton deleteButton = createActionButton("Eliminar Cliente", "app/view/menu/icon/delete-icon.svg");

        // Acción para el botón Nuevo
        newButton.addActionListener(e -> {
            JFrame frame = new JFrame("Nuevo Cliente");
            frame.setContentPane(new NewClientForm());
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

        // Acción para el botón Editar
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String name = (String) model.getValueAt(selectedRow, 0);
                String email = (String) model.getValueAt(selectedRow, 1);
                String phone = (String) model.getValueAt(selectedRow, 2);

                String firstName = name.split(" ")[0];
                String secondName = name.split(" ").length > 1 ? name.split(" ")[1] : "";
                String firstSurname = ""; // Ajustar según datos
                String secondSurname = ""; // Ajustar según datos
                int age = 0; // Ajustar según datos

                JFrame frame = new JFrame("Editar Cliente");
                frame.setContentPane(new EditClientForm(
                        "",
                        firstName,
                        secondName,
                        firstSurname,
                        secondSurname,
                        age,
                        email,
                        phone,
                        ""
                ));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Acción para el botón Eliminar
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este cliente?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    model.removeRow(selectedRow);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });

        buttonPanel.add(newButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Agregar componentes al panel principal
        panel.add(lbTitle, "span, growx, wrap");
        panel.add(searchPanel, "growx, wrap");
        panel.add(statsPanel, "growx, wrap");
        panel.add(scrollPane, "grow, push, wrap");
        panel.add(buttonPanel, "growx");

        add(panel, "grow");
    }

    /*private JPanel createStatCard(String title, String value) {
        JPanel card = new JPanel(new MigLayout("wrap, fillx, insets 10", "[grow]"));
        card.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:lighten(@background,5%);" +
                "arc:10");

        JLabel titleLabel = new JLabel(title);
        titleLabel.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +14");

        JLabel valueLabel = new JLabel(value);
        valueLabel.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +18");

        card.add(titleLabel, "growx");
        card.add(valueLabel, "growx");

        return card;
    }*/

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

    /*private JButton createActionButton(String text, String iconPath) {
        JButton button = new JButton(text, new ImageIcon(iconPath));
        button.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "foreground:@foreground;" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0;" +
                "arc:10");
        button.setPreferredSize(new Dimension(180, 40));
        button.setFont(button.getFont().deriveFont(Font.BOLD, 14f));
        return button;
    }*/

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