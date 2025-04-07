package app.view.forms;

import app.controller.GastoController;
import app.controller.IncomeController;
import app.model.Gasto;
import app.model.Income;
import app.view.Incomes;
import app.view.Overheads;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class EditIncomesForm extends JPanel {

    private JLabel lbTitle;
    private JComboBox<String> cbType;
    private JTextField txtDetail;
    private JTextField txtValue;
    private JButton btnAdd;
    private JButton btnCancel;
    private IncomeController controller;
    private Incomes over;
    private Income income;

    public EditIncomesForm(Incomes over, Income income) {
        init();
        controller = new IncomeController();
        editOverhead();
        closeForm();
        this.over = over;
        this.income = income;
        setText(this.income);
    }

    private void init() {
        configureLayout();
        createTitle();
        createFilterComponents();
        createActionButtons();
        assemblePanel();
    }

    // Configura el diseño principal
    private void configureLayout() {
        setLayout(new MigLayout("fill, insets 20", "[center]", "[center]"));
    }

    // Crea el título del panel
    private void createTitle() {
        lbTitle = new JLabel("Editar un ingresos");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +16");
    }

    // Crea los componentes de filtrado
    private void createFilterComponents() {
        cbType = new JComboBox<>(new String[]{"Ingreso", "Pago"});
        txtDetail = createFormField("Detalle");
        txtValue = createFormField("Monto del ingreso");
    }

    // Crea los botones de acción
    private void createActionButtons() {
        btnAdd = createActionButton("Editar");
        btnCancel = createActionButton("Cancelar");
    }

    // Ensambla el panel principal
    private void assemblePanel() {
        JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 35 45 30 45", "[fill, 600]"));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "background:$Menu.background;" + "arc:20");

        // Agregar título al panel
        panel.add(lbTitle, "growx, wrap, gapbottom 15");

        // Agregar componentes de filtrado
        panel.add(new JLabel("Tipo de ingreso:"), "gapy 8");
        panel.add(cbType, "growx, wrap");

        panel.add(new JLabel("Detalle:"), "gapy 8");
        panel.add(txtDetail, "growx, wrap");

        panel.add(new JLabel("Valor Ingreso/Pago:"), "gapy 8");
        panel.add(txtValue, "growx, wrap");

        // Agregar botones de acción
        panel.add(btnAdd, "split 2, gapy 20");
        panel.add(btnCancel);

        // Agregar el panel principal al contenedor
        add(panel, "grow");
    }

    // Método auxiliar para crear campos de formulario
    private JTextField createFormField(String placeholder) {
        JTextField field = new JTextField();
        field.putClientProperty(FlatClientProperties.STYLE,
                "background:lighten(@background,5%);"
                        + "foreground:@foreground;"
                        + "arc:10");
        field.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
        return field;
    }

    // Método auxiliar para crear botones de acción
    private JButton createActionButton(String text) {
        JButton button = new JButton(text);
        button.putClientProperty(FlatClientProperties.STYLE,
                "[light]background:darken(@background,10%);"
                        + "[dark]background:lighten(@background,10%);"
                        + "foreground:@foreground;"
                        + "focusWidth:0;"
                        + "innerFocusWidth:0;"
                        + "arc:10");
        return button;
    }

    private void editOverhead(){
        // Agregar el evento manualmente
        btnAdd.addActionListener(e -> {
            String type = cbType.getSelectedItem().toString();
            String detail = txtDetail.getText();
            String value = txtValue.getText();
            String id = this.income.getIncomeID();
            controller.updateIncome(id, type, detail, Double.parseDouble(value));
            over.llenarTabla();
            over.setTotal();
            JOptionPane.showMessageDialog(null, "¡Ingreso editado correctamente!",  "Ingreso editado", JOptionPane.INFORMATION_MESSAGE);
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
        });
    }

    private void closeForm(){
        // Agregar el evento manualmente
        btnCancel.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
        });
    }

    private void setText(Income income) {
        txtDetail.setText(income.getIncomeDescription());
        txtValue.setText(String.valueOf(income.getIncomeAmount()));
        cbType.setSelectedIndex(getIndex(income.getIncomeType()));
    }

    private int getIndex(String tipo){
        if (tipo.equals("Ingreso")){
            return 0;
        }
        if (tipo.equals("Pago")){
            return 1;
        }
        return -1;
    }

}