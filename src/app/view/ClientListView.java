package app.view;

import app.view.forms.EditClientForm;
import app.view.forms.NewClientForm;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ClientListView extends JPanel {

    private DefaultTableModel model;
    private JTable table;

    public ClientListView() {
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
        JLabel lbTitle = new JLabel("Clientes");
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
        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Buscar clientes...");

        // Botones de acciones con iconos
        JButton newButton = createToolBarButton("Nuevo", "path/to/new_icon.png");
        JButton editButton = createToolBarButton("Editar", "path/to/edit_icon.png");
        JButton deleteButton = createToolBarButton("Eliminar", "path/to/delete_icon.png");

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
                // Obtener los datos del cliente seleccionado
                String id = (String) model.getValueAt(selectedRow, 0);
                String name = (String) model.getValueAt(selectedRow, 1);
                String email = (String) model.getValueAt(selectedRow, 2);
                String phone = (String) model.getValueAt(selectedRow, 3);
                String address = (String) model.getValueAt(selectedRow, 4);

                // Dividir el nombre en primer nombre y segundo nombre (si existe)
                String firstName = name.split(" ")[0];
                String secondName = name.split(" ").length > 1 ? name.split(" ")[1] : "";

                // Valores predeterminados para apellidos y edad
                String firstSurname = ""; // Puedes ajustar esto según tus datos
                String secondSurname = ""; // Puedes ajustar esto según tus datos
                int age = 0; // Puedes ajustar esto según tus datos

                // Abrir el formulario de edición
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
                        address
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
                    model.removeRow(selectedRow); // Elimina la fila seleccionada
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
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
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Correo");
        model.addColumn("Teléfono");
        model.addColumn("Dirección");

        // Datos de ejemplo
        model.addRow(new Object[]{"1", "Juan Pérez", "juan@example.com", "123456789", "Calle 123"});
        model.addRow(new Object[]{"2", "María López", "maria@example.com", "987654321", "Avenida 456"});
        model.addRow(new Object[]{"3", "Carlos Gómez", "carlos@example.com", "555555555", "Plaza 789"});

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

    /*public static void main(String[] args) {
        JFrame frame = new JFrame("Vista de Clientes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new ClientListView());
        frame.setVisible(true);
    }*/
}