package app.views;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LoanList extends JPanel {

    public LoanList() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill, insets 20", "[center]", "[center]"));

        // Panel principal con bordes redondeados
        JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 35 45 30 45", "[fill,360]"));
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Menu.background;" +
                "arc:20");

        // Título
        JLabel lbTitle = new JLabel("Lista de Préstamos");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +10");

        // Descripción
        JLabel description = new JLabel("Administra los préstamos de tus clientes");
        description.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");

        // Campo de búsqueda
        JTextField searchField = new JTextField();
        searchField.putClientProperty(FlatClientProperties.STYLE, ""
                + "showClearButton:true;" +
                "background:lighten(@background,5%);" +
                "foreground:@foreground;" +
                "arc:10");
        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Buscar préstamos...");

        // Botón de búsqueda
        JButton searchButton = new JButton("Buscar");
        searchButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "foreground:@foreground;" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0;" +
                "arc:10");

        // Filtro por día, semana y mes
        JComboBox<String> filterComboBox = new JComboBox<>(new String[]{"Todos", "Hoy", "Esta semana", "Este mes"});
        filterComboBox.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:lighten(@background,5%);" +
                "foreground:@foreground;" +
                "arc:10");

        // Panel de búsqueda y filtro
        JPanel searchPanel = new JPanel(new MigLayout("fillx, insets 0", "[grow][]", "[]"));
        searchPanel.add(searchField, "growx");
        searchPanel.add(searchButton, "wmin 100, hmin 35");
        searchPanel.add(filterComboBox, "wmin 120, hmin 35");

        // Crear la tabla de préstamos
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Cliente");
        model.addColumn("Monto");
        model.addColumn("Tasa de Interés");
        model.addColumn("Plazo (meses)");
        model.addColumn("Estado");
        model.addColumn("Fecha");

        // Datos de ejemplo
        model.addRow(new Object[]{"1", "Juan Pérez", "$10,000", "5%", "12", "Activo", "2023-10-01"});
        model.addRow(new Object[]{"2", "María López", "$15,000", "7%", "24", "Activo", "2023-10-02"});
        model.addRow(new Object[]{"3", "Carlos Sánchez", "$20,000", "6%", "36", "Inactivo", "2023-10-03"});

        JTable table = new JTable(model);
        table.setRowHeight(40); // Altura de las filas
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
        table.setGridColor(new Color(230, 230, 230));

        // ScrollPane para la tabla
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:0,0,0,0;" +
                "background:$Table.background");

        // Botones del CRUD
        JButton addButton = createCrudButton("Agregar");
        JButton editButton = createCrudButton("Editar");
        JButton deleteButton = createCrudButton("Eliminar");

        // Panel de botones CRUD
        JPanel crudPanel = new JPanel(new MigLayout("insets 0", "[][][]", "[]"));
        crudPanel.setOpaque(false);
        crudPanel.add(addButton, "wmin 100, hmin 35");
        crudPanel.add(editButton, "wmin 100, hmin 35");
        crudPanel.add(deleteButton, "wmin 100, hmin 35");

        // Agregar componentes al panel
        panel.add(lbTitle, "growx, wrap");
        panel.add(description, "growx, wrap");
        panel.add(searchPanel, "growx, wrap");
        panel.add(scrollPane, "grow, push, wrap");
        panel.add(crudPanel, "right, wrap");

        add(panel, "grow");
    }

    private JButton createCrudButton(String text) {
        JButton button = new JButton(text);
        button.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "foreground:@foreground;" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0;" +
                "arc:10");
        return button;
    }
}