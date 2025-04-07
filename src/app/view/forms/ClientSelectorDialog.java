package app.view.forms;

import app.model.Client;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class ClientSelectorDialog extends JDialog {
    private JTable table;
    private DefaultTableModel model;
    private String selectedClientId;

    public ClientSelectorDialog(JFrame parent, List<Client> clients) {
        super(parent, "Seleccionar Cliente", true);
        init(clients);
    }

    private void init(List<Client> clients) {
        setLayout(new MigLayout("fill,insets 20", "[grow]", "[grow]"));
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Panel principal con bordes redondeados
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 20", "[grow]", "[grow]"));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "background:$Menu.background;" +
                        "arc:20");

        // Título
        JLabel titleLabel = new JLabel("Seleccionar Cliente");
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +14");
        panel.add(titleLabel, "growx, wrap");

        // Barra de búsqueda con botón
        JPanel searchBarPanel = new JPanel(new BorderLayout());
        searchBarPanel.putClientProperty(FlatClientProperties.STYLE,
                "background:lighten(@background,5%);" +
                        "arc:10");

        JTextField searchField = new JTextField();
        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Buscar cliente...");
        searchField.putClientProperty(FlatClientProperties.STYLE,
                "showClearButton:true;" +
                        "background:lighten(@background,3%);" +
                        "foreground:@foreground;" +
                        "arc:10");

        JButton searchButton = new JButton(new FlatSVGIcon("app/icon/svg/search-icon.svg").derive(20, 20));
        searchButton.putClientProperty(FlatClientProperties.STYLE,
                "[light]background:darken(@background,10%);" +
                        "[dark]background:lighten(@background,10%);" +
                        "borderWidth:0;" +
                        "focusWidth:0;" +
                        "innerFocusWidth:0;" +
                        "arc:10");
        searchButton.addActionListener(e -> filterTable(searchField.getText()));

        searchBarPanel.add(searchField, BorderLayout.CENTER);
        searchBarPanel.add(searchButton, BorderLayout.EAST);
        panel.add(searchBarPanel, "growx, wrap");

        // Tabla
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre Completo");
        model.addColumn("Correo");
        model.addColumn("Teléfono");
        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que la tabla no sea editable
            }
        };

        // Estilo de la tabla
        table.setRowHeight(35);
        table.getColumnModel().getColumn(0).setPreferredWidth(100); // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(200); // Nombre
        table.getColumnModel().getColumn(2).setPreferredWidth(200); // Correo
        table.getColumnModel().getColumn(3).setPreferredWidth(150); // Teléfono
        table.putClientProperty(FlatClientProperties.STYLE,
                "showHorizontalLines:true;" +
                        "showVerticalLines:true;" +
                        "font:+1");

        // Header con estilo minimalista
        JTableHeader header = table.getTableHeader();
        header.putClientProperty(FlatClientProperties.STYLE,
                "font:bold +1;" +
                        "height:35;" +
                        "background:darken(@background,5%);" +
                        "foreground:@foreground");

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.putClientProperty(FlatClientProperties.STYLE,
                "background:$Menu.background;" +
                        "arc:10");
        panel.add(scrollPane, "grow, push, wrap");

        // Botón Seleccionar
        JButton btnSelect = new JButton("Seleccionar");
        btnSelect.putClientProperty(FlatClientProperties.STYLE,
                "[light]background:darken(@background,10%);" +
                        "[dark]background:lighten(@background,10%);" +
                        "borderWidth:0;" +
                        "focusWidth:0;" +
                        "innerFocusWidth:0;" +
                        "arc:10");
        btnSelect.setFont(btnSelect.getFont().deriveFont(Font.BOLD, 14f));
        btnSelect.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                selectedClientId = (String) model.getValueAt(selectedRow, 0);
                dispose(); // Cierra la ventana
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, "Seleccione un cliente.");
            }
        });
        panel.add(btnSelect, "gapy 10, growx");

        // Cargar datos iniciales
        loadClients(clients);

        add(panel);
    }

    private void loadClients(List<Client> clients) {
        model.setRowCount(0); // Limpiar tabla
        for (Client client : clients) {
            model.addRow(new Object[]{
                    client.getId(),
                    client.getFirstName() + " " + client.getFirstSurname(),
                    client.getEmail(),
                    client.getPhone()
            });
        }
    }

    private void filterTable(String query) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        sorter.setRowFilter(query.isEmpty() ? null : RowFilter.regexFilter("(?i)" + query));
    }

    public String getSelectedClientId() {
        return selectedClientId;
    }
}