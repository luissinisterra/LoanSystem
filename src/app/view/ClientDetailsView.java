package app.view;

import app.controller.ClientController;
import app.model.Client;
import app.model.Loan;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class ClientDetailsView extends JPanel {
    private final ClientController clientController = new ClientController();
    private final String clientId;

    public ClientDetailsView(String clientId) {
        this.clientId = clientId;
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill, insets 20", "[grow][grow]", "[grow]"));

        // Obtener cliente
        Client client = clientController.getClientById(clientId);

        // Paneles
        JPanel leftPanel = createInfoPanel(client);
        JPanel rightPanel = createLoansPanel(client);

        add(leftPanel, "grow, push");
        add(rightPanel, "grow, push");
    }

    private JPanel createInfoPanel(Client client) {
        JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 20", "[grow]", "[]10[]10[]10[]10[]"));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "background:lighten(@background,5%);" +
                        "arc:16;" +
                        "font:medium +2");
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Información del Cliente");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +4");

        JLabel name = new JLabel("Nombre: " + client.getFirstName() + " " + client.getFirstSurname());
        JLabel age = new JLabel("Edad: " + client.getAge());
        JLabel address = new JLabel("Dirección: " + client.getAddress().getCity() + ", " + client.getAddress().getStreet());
        JLabel phone = new JLabel("Teléfono: " + client.getPhone());
        JLabel status = new JLabel("Estado: " + (client.isActive() ? "Activo" : "Inactivo"));

        for (JLabel label : new JLabel[]{name, age, address, phone, status}) {
            label.putClientProperty(FlatClientProperties.STYLE, "font:regular +2");
        }

        panel.add(title, "wrap");
        panel.add(name);
        panel.add(age);
        panel.add(address);
        panel.add(phone);
        panel.add(status);

        return panel;
    }

    private JPanel createLoansPanel(Client client) {
        JPanel panel = new JPanel(new MigLayout("wrap, fill, insets 20", "[grow]", "[grow]"));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "background:lighten(@background,5%);" +
                        "arc:16");

        JLabel title = new JLabel("Historial de Préstamos");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +4");

        // Crear modelo tabla
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Monto");
        model.addColumn("Interés");
        model.addColumn("Plazo");
        model.addColumn("Fecha");
        model.addColumn("Estado");

        if (client.getLoans() != null && !client.getLoans().isEmpty()) {
            for (Loan loan : client.getLoans()) {
                model.addRow(new Object[]{
                        "$" + loan.getAmount(),
                        loan.getInterestRate() + "%",
                        loan.getTerm() + " meses",
                        loan.getDate(),
                        loan.isActive() ? "Activo" : "Pagado"
                });
            }
        }

        JTable table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Estilo de tabla igual que ClientListView
        table.setRowHeight(35);
        table.putClientProperty(FlatClientProperties.STYLE,
                "showHorizontalLines:true;" +
                        "showVerticalLines:true;" +
                        "font:+1");

        JTableHeader header = table.getTableHeader();
        header.putClientProperty(FlatClientProperties.STYLE,
                "font:bold +1;" +
                        "height:35");

        // Ajustar tamaño de columnas si deseas:
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(700, 350));

        panel.add(title, "wrap");
        panel.add(scrollPane, "grow, push");

        return panel;
    }

}
