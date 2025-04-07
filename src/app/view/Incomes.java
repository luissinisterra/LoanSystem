package app.view;

import app.controller.IncomeController;
import app.model.Income;
import app.view.forms.EditIncomesForm;
import app.view.forms.NewIncomeForm;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Incomes extends JPanel {

    private IncomeController controller;

    public Incomes() {
        controller = new IncomeController();
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        JPanel panel = createMainContentPanel();

        // Agregar título y tabla
        panel.add(createTitleLabel(), "gapbottom 15, al center");
        panel.add(createTableComponent(), "grow, gapbottom 20");

        // Agregar el label del total
        panel.add(createTotalLabel(), "al right, span, wrap"); // Alinea el total a la derecha y salta a la siguiente fila

        // Agregar botones alineados horizontalmente
        panel.add(createAgregarButton(), "split 4, growx, gapright 10"); // Botón "Eliminar gasto"
        panel.add(createEliminarButton(), "growx, gapright 10"); // Botón "Editar gasto"
        panel.add(createEditarButton(), "growx, gapright 10"); // Botón "Ver detalles"
        panel.add(createDetallesButton(), "growx, wrap"); // Botón "Agregar gasto"

        add(panel);
        llenarTabla();
        addIncome();
        removeIncome();
        updateIncome();
        incomeDetails();
        setTotal();
    }

    // --- Componentes separados ---

    private JPanel createMainContentPanel() {
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 35 25 25", "fill,500:700"));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "arc:20;" +
                        "[light]background:darken(@background,3%);" +
                        "[dark]background:lighten(@background,3%)");
        return panel;
    }

    private JLabel createTitleLabel() {
        JLabel lbTitle = new JLabel("Ingresos/Pagos");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +12");
        return lbTitle;
    }

    private JScrollPane createTableComponent() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Tipo", "Valor", "Fecha"});
        JTable table = new JTable(model);
        this.incomesTable = table;
        table.setRowHeight(35);
        table.getColumnModel().getColumn(0).setPreferredWidth(300);
        table.putClientProperty(FlatClientProperties.STYLE,
                "showHorizontalLines:true;" +
                        "showVerticalLines:true;" +
                        "font:+1");

        // Configuración del header
        JTableHeader header = table.getTableHeader();
        header.putClientProperty(FlatClientProperties.STYLE,
                "font:bold +1;" +
                        "height:35");

        // ScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(750, 400));
        return scrollPane;
    }

    private JLabel createTotalLabel() {
        JLabel lblTotal = new JLabel();
        lblTotal.putClientProperty(FlatClientProperties.STYLE, "font:bold +3");
        lblTotalLabel = lblTotal;
        return lblTotal;
    }

    private JButton createAgregarButton() {
        JButton btn = createStylizedButton("Agregar ingresos");
        btnAdd = btn;
        return btn;
    }

    private JButton createEliminarButton() {
        JButton btn = createStylizedButton("Eliminar ingreso");
        btnDelete = btn;
        return btn;
    }

    private JButton createEditarButton() {
        JButton btn = createStylizedButton("Editar ingreso");
        btnEdit = btn; // Asignar el botón a la variable de instancia
        return btn;
    }

    private JButton createDetallesButton() {
        JButton btn = createStylizedButton("Ver detalles");
        btnDetails = btn;
        return btn;
    }

    private JButton createStylizedButton(String text) {
        JButton button = new JButton(text);
        button.putClientProperty(FlatClientProperties.STYLE,
                "[light]background:darken(@background,10%);" +
                        "[dark]background:lighten(@background,10%);" +
                        "focusWidth:0;" +
                        "innerFocusWidth:0;" +
                        "font:+2");
        button.setPreferredSize(new Dimension(100, 20));
        return button;
    }

    public void llenarTabla() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Tipo", "Valor", "Fecha"});
        for (int i = 0; i < controller.getIncomes().size(); i++) {
            model.addRow(new Object[]{
                    controller.getIncomes().get(i).getIncomeType(),
                    controller.getIncomes().get(i).getIncomeAmount(),
                    controller.getIncomes().get(i).getIncomeDate(),
            });
        }
        incomesTable.setModel(model);
    }

    private void addIncome(){
        // Agregar el evento manualmente
        btnAdd.addActionListener(e -> {
            NewIncomeForm newIncomeForm = new NewIncomeForm(this);
            JFrame frame = new JFrame("Agregar gasto");
            frame.setContentPane(newIncomeForm);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private void updateIncome(){
        // Agregar el evento manualmente
        btnEdit.addActionListener(e -> {
            String id = getSelectedIncomeID();
            Income i = controller.getIncomeByID(id);
            EditIncomesForm form = new EditIncomesForm(this, i);
            JFrame frame = new JFrame("Editar gasto");
            frame.setContentPane(form);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private void removeIncome(){
        // Agregar el evento manualmente
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = getSelectedIncomeID();
                controller.removeIncome(id);
                llenarTabla();
                setTotal();
            }
        });
    }

    private void incomeDetails(){
        btnDetails.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, controller.getIncomeByID(getSelectedIncomeID()));
        });
    }

    private String getSelectedIncomeID() {
        int filaVista = incomesTable.getSelectedRow();
        if (filaVista >= 0) {
            int filaModelo = incomesTable.convertRowIndexToModel(filaVista);
            List<Income> i = controller.getIncomes();
            if (filaModelo < i.size()) {
                return i.get(filaModelo).getIncomeID();
            }
        }
        return null;
    }

    public void setTotal () {
        List<Income> incomes = controller.getIncomes();
        double total = 0;
        for (Income income : incomes) {
            total += income.getIncomeAmount();
        }
        lblTotalLabel.setText("Total: "  + total);
    }

    private JTable incomesTable;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDetails;
    private JButton btnDelete;
    private JLabel lblTotalLabel;
}