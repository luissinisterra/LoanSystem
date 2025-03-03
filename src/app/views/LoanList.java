package app.views;

import app.views.NewLoanForm;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class LoanList extends JPanel {

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
            app.views.NewLoanForm newLoanForm = new NewLoanForm();
            JFrame frame = new JFrame("Nuevo Préstamo");
            frame.setContentPane(newLoanForm);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

        // Agregar componentes a la barra de herramientas
        toolBar.add(searchField);
        toolBar.add(Box.createHorizontalStrut(10)); // Espaciador
        toolBar.add(newButton);
        toolBar.add(editButton);
        toolBar.add(deleteButton);

        // Tabla de préstamos
        DefaultTableModel model = new DefaultTableModel();
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

        JTable table = new JTable(model);
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