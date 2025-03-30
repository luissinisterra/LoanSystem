package app.view;

import app.view.forms.EditLoanForm;
import app.view.forms.NewLoanForm;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class LoanList extends JPanel {

    private DefaultTableModel model;
    private JTable table;

    public LoanList() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill, insets 20", "[center]", "[center]"));

        // Panel principal con bordes redondeados
        JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 20", "[fill, 800]"));
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Menu.background;" +
                "arc:20");

        // Título
        JLabel lbTitle = new JLabel("Préstamos");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +18");

        // Barra de herramientas (búsqueda y acciones)
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setOpaque(false);

        // Campo de búsqueda
        JTextField searchField = new JTextField();
        searchField.putClientProperty(FlatClientProperties.STYLE, ""
                + "showClearButton:true;" +
                "background:lighten(@background,5%);" +
                "foreground:@foreground;" +
                "arc:10");
        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Buscar préstamos...");

        // Botones de acciones con iconos
        JButton newButton = createToolBarButton("Nuevo", "path/to/new_icon.png");
        JButton editButton = createToolBarButton("Editar", "path/to/edit_icon.png");
        JButton deleteButton = createToolBarButton("Eliminar", "path/to/delete_icon.png");

        // Acción para el botón Nuevo
        newButton.addActionListener(e -> {
            NewLoanForm newLoanForm = new NewLoanForm();
            JFrame frame = new JFrame("Nuevo Préstamo");
            frame.setContentPane(newLoanForm);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

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
                    model.removeRow(selectedRow); // Elimina la fila seleccionada
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un préstamo para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Agregar componentes a la barra de herramientas
        toolBar.add(searchField);
        toolBar.add(Box.createHorizontalStrut(10)); // Espaciador
        toolBar.add(newButton);
        toolBar.add(editButton);
        toolBar.add(deleteButton);

        // Modelo de la tabla
        model = new DefaultTableModel();

        // Columnas de la tabla
        model.addColumn("Cliente");
        model.addColumn("Monto");
        model.addColumn("Tasa de Interés");
        model.addColumn("Plazo (meses)");
        model.addColumn("Estado");
        model.addColumn("Fecha");

        // Datos de ejemplo
        model.addRow(new Object[]{"Juan Pérez", "$10,000", "5%", "12", "Activo", "2023-10-01"});
        model.addRow(new Object[]{"María López", "$15,000", "7%", "24", "Activo", "2023-09-15"});
        model.addRow(new Object[]{"Carlos Sánchez", "$20,000", "6%", "36", "Inactivo", "2023-08-20"});

        // Crear la tabla con el modelo
        table = new JTable(model);
        table.setRowHeight(40); // Altura de las filas
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
        table.setGridColor(new Color(230, 230, 230));

        // Estilo del encabezado de la tabla
        JTableHeader header = table.getTableHeader();
        header.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Table.headerBackground;" +
                "foreground:$Table.headerForeground;" +
                "border:0,0,0,0");

        // ScrollPane para la tabla
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:0,0,0,0;" +
                "background:$Table.background");

        // Agregar componentes al panel principal
        panel.add(lbTitle, "growx, wrap");
        panel.add(toolBar, "growx, wrap");
        panel.add(scrollPane, "grow, push, wrap");

        add(panel, "grow");
    }

    private JButton createToolBarButton(String text, String iconPath) {
        JButton button = new JButton(text, new ImageIcon(iconPath));
        button.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "foreground:@foreground;" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0;" +
                "arc:10");
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        return button;
    }
}