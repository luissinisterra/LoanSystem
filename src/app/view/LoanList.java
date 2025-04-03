package app.view;

import app.view.forms.EditLoanForm;
import app.view.forms.NewLoanForm;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class LoanList extends JPanel {

    private DefaultTableModel model;
    private JTable table;

    public LoanList() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill, insets 20", "[grow]", "[grow]"));

        // Panel principal con bordes redondeados
        JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 20", "[grow]"));
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Menu.background;" +
                "arc:20");

        // Título
        JLabel lbTitle = new JLabel("Gestión de Préstamos");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +18");

        // Búsqueda rápida
        JTextField searchField = new JTextField();
        searchField.putClientProperty(FlatClientProperties.STYLE, ""
                + "showClearButton:true;" +
                "background:lighten(@background,5%);" +
                "foreground:@foreground;" +
                "arc:10");
        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Buscar préstamos...");
        JLabel searchIcon = new JLabel("🔍");
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(searchIcon, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:lighten(@background,5%);" +
                "arc:10");

        // Tarjetas de resumen
        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        statsPanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Menu.background;" +
                "arc:10");

        JPanel totalLoans = createStatCard("Total de Préstamos", "📊 50");
        JPanel activeLoans = createStatCard("Préstamos Activos", "📊 30");

        statsPanel.add(totalLoans);
        statsPanel.add(activeLoans);

        // Modelo de la tabla
        model = new DefaultTableModel();

        // Columnas de la tabla
        model.addColumn("ID");
        model.addColumn("Cliente");
        model.addColumn("Monto");
        model.addColumn("Estado");
        model.addColumn("Fecha");

        // Datos de ejemplo
        model.addRow(new Object[]{"1", "Juan Pérez", "$5,000", "Activo", "2023-10-01"});
        model.addRow(new Object[]{"2", "María López", "$3,000", "Pagado", "2022-09-15"});
        model.addRow(new Object[]{"3", "Carlos Sánchez", "$8,000", "Vencido", "2023-08-20"});

        // Crear la tabla con el modelo
        table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    comp.setBackground(row % 2 == 0 ? new Color(245, 245, 245, 150) : new Color(230, 230, 230, 150));
                } else {
                    comp.setBackground(new Color(0, 120, 215)); // Color de fila seleccionada
                }
                return comp;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que la tabla no sea editable
            }
        };
        table.setRowHeight(40);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
        table.setGridColor(new Color(230, 230, 230));

        // Estilo del encabezado de la tabla
        JTableHeader header = table.getTableHeader();
        header.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Table.headerBackground;" +
                "foreground:$Table.headerForeground;" +
                "border:0,0,0,0");

        // ScrollPane para la tabla (transparente)
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:0,0,0,0;" +
                "background:null"); // Fondo transparente
        table.setOpaque(false); // Hacer la tabla transparente
        ((JComponent) table.getDefaultRenderer(Object.class)).setOpaque(false); // Hacer celdas transparentes

        // Botones de acción
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Menu.background;" +
                "arc:10");

        JButton newButton = createActionButton("Nuevo Préstamo", "path/to/new_icon.png");
        JButton editButton = createActionButton("Editar Préstamo", "path/to/edit_icon.png");
        JButton deleteButton = createActionButton("Eliminar Préstamo", "path/to/delete_icon.png");

        // Acción para el botón Nuevo
        newButton.addActionListener(e -> {
            JFrame frame = new JFrame("Nuevo Préstamo");
            frame.setContentPane(new NewLoanForm());
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

        // Acción para el botón Editar
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                // Obtener los datos del préstamo seleccionado
                String client = (String) model.getValueAt(selectedRow, 0);
                String amount = (String) model.getValueAt(selectedRow, 1);
                String interestRate = (String) model.getValueAt(selectedRow, 2);
                String term = (String) model.getValueAt(selectedRow, 3);
                String status = (String) model.getValueAt(selectedRow, 4);
                String date = (String) model.getValueAt(selectedRow, 5);

                // Abrir el formulario de edición
                EditLoanForm editLoanForm = new EditLoanForm(client, amount, interestRate, term, status, date);
                JFrame frame = new JFrame("Editar Préstamo");
                frame.setContentPane(editLoanForm);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un préstamo para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });


        // Acción para el botón Eliminar
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este préstamo?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    model.removeRow(selectedRow);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un préstamo para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
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

    private JPanel createStatCard(String title, String value) {
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

        return card; // Devolvemos el JPanel directamente
    }

    private JButton createActionButton(String text, String iconPath) {
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
    }

    /*public static void main(String[] args) {
        JFrame frame = new JFrame("Gestión de Préstamos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new LoanList());
        frame.setVisible(true);
    }*/
}