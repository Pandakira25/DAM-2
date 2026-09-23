package com.dam.view;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class TablaConBoton extends JFrame {

    public TablaConBoton() {
        String[] columnas = {"Nombre", "Edad", "Acción"};
        Object[][] datos = {
            {"Ana", 25, "Eliminar"},
            {"Luis", 30, "Eliminar"},
            {"Marta", 22, "Eliminar"}
        };

        JTable tabla = new JTable(new DefaultTableModel(datos, columnas));

        // Asignar renderer y editor a la columna del botón (columna 2)
        tabla.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
        tabla.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JCheckBox(), tabla));

        add(new JScrollPane(tabla));
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    // --- RENDERER: solo pinta el botón ---
    static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int col) {
            setText(value != null ? value.toString() : "");
            return this;
        }
    }

    // --- EDITOR: hace el botón interactivo ---
    static class ButtonEditor extends DefaultCellEditor {
        private JButton boton;
        private JTable tabla;
        private int filaActual;

        public ButtonEditor(JCheckBox checkBox, JTable tabla) {
            super(checkBox);
            this.tabla = tabla;
            boton = new JButton();
            boton.setOpaque(true);

            boton.addActionListener(e -> {
                fireEditingStopped(); // importante: cierra el editor antes de actuar
                int modelRow = tabla.convertRowIndexToModel(filaActual);
                String nombre = (String) tabla.getModel().getValueAt(modelRow, 0);
                JOptionPane.showMessageDialog(null, "Eliminando a: " + nombre);
                // Aquí llamarías a tu método real, ej: modelo.removeRow(modelRow);
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int col) {
            filaActual = row;
            boton.setText(value != null ? value.toString() : "");
            return boton;
        }

        @Override
        public Object getCellEditorValue() {
            return boton.getText();
        }
    }

    public static void main(String[] args) {
        new TablaConBoton();
    }
}
