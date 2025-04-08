package app.view;

import app.controller.IncomeController;
import app.exception.ApiException;
import app.model.Income;
import app.view.forms.EditIncomesForm;
import app.view.forms.NewIncomeForm;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class Incomes extends JPanel {

    private final IncomeController controller = new IncomeController();


    public Incomes() {
        init();
    }

    // ==============================
    // Inicialización
    // ==============================

    private void init() {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        JPanel panel = createMainPanel();

        panel.add(createTitleLabel(), "gapbottom 15, al center");
        panel.add(createTableComponent(), "grow, gapbottom 20");
        panel.add(createTotalLabel(), "al right, span, wrap");
        panel.add(new JSeparator(), "gapy 5 5");
        panel.add(createAddButton(), "split 4, growx, gapright 10");
        panel.add(createDeleteButton(), "growx, gapright 10");
        panel.add(createEditButton(), "growx, gapright 10");
        panel.add(createDetailsButton(), "growx, wrap");

        add(panel);
        llenarTabla();
        setTotal();
        configurarAcciones();
    }

    private void configurarAcciones() {
        btnAdd.addActionListener(e -> agregarIngreso());
        btnDelete.addActionListener(e -> eliminarIngreso());
        btnEdit.addActionListener(e -> editarIngreso());
        btnDetails.addActionListener(e -> verDetallesIngreso());
    }

    // ==============================
    // Construcción visual
    // ==============================

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 35 25 25", "fill,500:700"));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "arc:20;" +
                        "[light]background:darken(@background,3%);" +
                        "[dark]background:lighten(@background,3%)");
        return panel;
    }

    private JLabel createTitleLabel() {
        JLabel lb = new JLabel("Ingresos/Pagos");
        lb.putClientProperty(FlatClientProperties.STYLE, "font:bold +12");
        return lb;
    }

    private JScrollPane createTableComponent() {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Tipo", "Valor", "Fecha"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        this.incomesTable = table;
        table.setRowHeight(35);
        table.getColumnModel().getColumn(0).setPreferredWidth(300);
        table.putClientProperty(FlatClientProperties.STYLE, "showHorizontalLines:true;"
                + "showVerticalLines:true;"
                + "selectionBackground:@background;"
                + "selectionForeground:@foreground;"
                + "font:+2");

        // Header optimizado para el nuevo ancho
        JTableHeader header = table.getTableHeader();
        header.putClientProperty(FlatClientProperties.STYLE, "font:bold +2;"
                + "height:40");

        // ScrollPane con ancho completo
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.putClientProperty(FlatClientProperties.STYLE, "border:7,7,7,7;"
                + "background:@background;");

        scrollPane.setPreferredSize(new Dimension(750, 400));
        return scrollPane;
    }

    private JLabel createTotalLabel() {
        JLabel lb = new JLabel();
        lb.putClientProperty(FlatClientProperties.STYLE, "font:bold +3");
        lblTotalLabel = lb;
        return lb;
    }

    private JButton createAddButton() {
        JButton btn = createStyledButton(" Agregar ingresos", "app/icon/svg/finances.svg");
        btnAdd = btn;
        return btn;
    }

    private JButton createDeleteButton() {
        JButton btn = createStyledButton(" Eliminar ingreso", "app/icon/svg/remove.svg");
        btnDelete = btn;
        return btn;
    }

    private JButton createEditButton() {
        JButton btn = createStyledButton(" Editar ingreso", "app/icon/svg/edit.svg");
        btnEdit = btn;
        return btn;
    }

    private JButton createDetailsButton() {
        JButton btn = createStyledButton(" Ver detalles", "app/icon/svg/info.svg");
        btnDetails = btn;
        return btn;
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton btn = new JButton(text, new FlatSVGIcon(iconPath).derive(20, 20));
        btn.putClientProperty(FlatClientProperties.STYLE,
                "[light]background:darken(@background,10%);" +
                        "[dark]background:lighten(@background,10%);" +
                        "focusWidth:0;" +
                        "innerFocusWidth:0;" +
                        "font:+2");
        btn.setPreferredSize(new Dimension(90, 35));
        return btn;
    }

    // ==============================
    // Datos / Tabla
    // ==============================

    public void llenarTabla() {
        try {
            DefaultTableModel model = new DefaultTableModel(new Object[]{"Tipo", "Valor", "Fecha"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Ninguna celda editable
                }
            };
            for (Income income : controller.getIncomes()) {
                model.addRow(new Object[]{
                        income.getIncomeType(),
                        income.getIncomeAmount(),
                        income.getIncomeDate()
                });
            }
            incomesTable.setModel(model);
        } catch (ApiException ex) {
            Notifications.getInstance().show(Notifications.Type.ERROR, ex.getMessage());
        }
    }

    public void setTotal() {
        try {
            List<Income> incomes = controller.getIncomes();
            double total = 0;
            for (Income income : incomes) {
                total += income.getIncomeAmount();
            }
            lblTotalLabel.setText("Total: " + total);
        } catch (ApiException ex) {
        }
    }

    // ==============================
    // Funcionalidad de botones
    // ==============================

    private void agregarIngreso() {
        NewIncomeForm form = new NewIncomeForm(this);
        JFrame frame = new JFrame("Agregar gasto");
        frame.setContentPane(form);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
    }

    private void eliminarIngreso() {
        String id = getSelectedIncomeID();
        if (id == null) {
            Notifications.getInstance().show(Notifications.Type.ERROR, "Seleccione un ingreso para eliminar");
        } else {
            controller.removeIncome(id);
            llenarTabla();
            setTotal();
            Notifications.getInstance().show(Notifications.Type.INFO, "Ingreso eliminado correctamente");
        }
    }

    private void editarIngreso() {
        String id = getSelectedIncomeID();
        if (id == null) {
            Notifications.getInstance().show(Notifications.Type.ERROR, "Seleccione un ingreso para editar");
        } else {
            Income i = controller.getIncomeByID(id);
            EditIncomesForm form = new EditIncomesForm(this, i);
            JFrame frame = new JFrame("Editar gasto");
            frame.setContentPane(form);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setAlwaysOnTop(true);
        }
    }

    private void verDetallesIngreso() {
        String id = getSelectedIncomeID();
        if (id == null) {
            Notifications.getInstance().show(Notifications.Type.ERROR, "Seleccione un ingreso para ver sus detalles");
        } else {
            JOptionPane.showMessageDialog(null, controller.getIncomeByID(id));
        }
    }

    // ==============================
    // Helpers
    // ==============================

    private String getSelectedIncomeID() {
        int filaVista = incomesTable.getSelectedRow();
        if (filaVista >= 0) {
            int filaModelo = incomesTable.convertRowIndexToModel(filaVista);
            List<Income> incomes = controller.getIncomes();
            if (filaModelo < incomes.size()) {
                return incomes.get(filaModelo).getIncomeID();
            }
        }
        return null;
    }

    // ==============================
    // Atributos
    // ==============================

    private JTable incomesTable;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDetails;
    private JButton btnDelete;
    private JLabel lblTotalLabel;
}
