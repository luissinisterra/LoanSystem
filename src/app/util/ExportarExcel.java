/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.util;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExportarExcel {

    public void exportarExcel(JTable t) throws IOException {
    JFileChooser chooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de Excel", "xls");
    chooser.setFileFilter(filter);
    chooser.setDialogTitle("Guardar archivo");
    chooser.setAcceptAllFileFilterUsed(false);

    if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
        String ruta = chooser.getSelectedFile().toString().concat(".xls");
        try {
            File archivoXLS = new File(ruta);
            if (archivoXLS.exists()) {
                archivoXLS.delete();
            }
            archivoXLS.createNewFile();
            Workbook libro = new HSSFWorkbook();
            FileOutputStream archivo = new FileOutputStream(archivoXLS);
            Sheet hoja = libro.createSheet("Mi hoja de trabajo 1");
            hoja.setDisplayGridlines(true); // Se muestran las cuadrículas

            // 1. Escribir los encabezados de la tabla en la primera fila
            Row filaEncabezado = hoja.createRow(0);
            for (int c = 0; c < t.getColumnCount(); c++) {
                Cell celda = filaEncabezado.createCell(c);
                celda.setCellValue(t.getColumnName(c));
            }

            // 2. Escribir los datos de la tabla
            for (int f = 0; f < t.getRowCount(); f++) {
                Row fila = hoja.createRow(f + 1); // Se inicia en la fila 1 porque la 0 es de encabezado
                for (int c = 0; c < t.getColumnCount(); c++) {
                    Cell celda = fila.createCell(c);
                    Object valor = t.getValueAt(f, c);

                    // Convertir valores según el tipo de dato
                    if (valor instanceof Number) {
                        celda.setCellValue(((Number) valor).doubleValue());
                    } else {
                        celda.setCellValue(valor.toString());
                    }
                }
            }

            // 3. Ajustar automáticamente el ancho de las columnas
            for (int c = 0; c < t.getColumnCount(); c++) {
                hoja.autoSizeColumn(c);
            }

            // Guardar el archivo
            libro.write(archivo);
            archivo.close();
            Desktop.getDesktop().open(archivoXLS);
        } catch (IOException | NumberFormatException e) {
            throw e;
        }
    }
}

}
