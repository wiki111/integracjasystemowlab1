package com.company.lab2;

import com.company.lab2.DataWriter;
import com.company.lab2.SelectionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainLayout extends JFrame {
    private JPanel rootPanel;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JButton addNewButton;
    private JButton SaveButton;
    private DefaultTableModel model;
    private boolean editMode;
    private int activeRow;
    private DataWriter dataWriter;

    public MainLayout(){
        editMode = false;
        dataWriter = new DataWriter();
        ListSelectionModel lsm = table1.getSelectionModel();
        lsm.addListSelectionListener(new SelectionListener(this));
        table1.setSelectionModel(lsm);
        add(rootPanel);
        setTitle("Skuliowski Lab 2");
        setSize(600, 500);

        addNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!editMode){
                    model.addRow(new String[]{textField1.getText(), textField2.getText(), textField3.getText(),
                            textField4.getText(), textField5.getText(), textField6.getText(), textField7.getText(),
                            textField8.getText(),   textField9.getText(), textField10.getText()});
                }else {
                    model.setValueAt(textField1.getText(), activeRow, 0);
                    model.setValueAt(textField2.getText(), activeRow, 1);
                    model.setValueAt(textField3.getText(), activeRow, 2);
                    model.setValueAt(textField4.getText(), activeRow, 3);
                    model.setValueAt(textField5.getText(), activeRow, 4);
                    model.setValueAt(textField6.getText(), activeRow, 5);
                    model.setValueAt(textField7.getText(), activeRow, 6);
                    model.setValueAt(textField8.getText(), activeRow, 7);
                    model.setValueAt(textField9.getText(), activeRow, 8);
                    model.setValueAt(textField10.getText(), activeRow, 9);
                    addNewButton.setText("Add new");
                    editMode = false;
                    activeRow = -1;
                }

                table1.setModel(model);
            }
        });

        SaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String data = "";
                for(int row=0; row<model.getRowCount(); row++){
                    for(int col=0; col<10; col++){
                        data+=getValue(model.getValueAt(row,col).toString());
                        if(col != 9){
                            data+="|";
                        }
                    }
                    data+="\n";
                }
                dataWriter.saveToFile(data);
            }
        });
    }

    public void setUpTable(String[] headers, Object[][] data){

         this.model = new DefaultTableModel(data, headers);

        /*for(String header : headers){
            TableColumn column = new TableColumn();
            column.setHeaderValue(header);
            table1.addColumn(header);
        }*/

        table1.setModel(model);
        table1.setAutoCreateColumnsFromModel(true);
    }

    public void fillForm(int rowId){
        editMode = true;
        activeRow = rowId;
        this.addNewButton.setText("Save edit");
        textField1.setText(model.getValueAt(rowId, 0).toString());
        textField2.setText(model.getValueAt(rowId, 1).toString());
        textField3.setText(model.getValueAt(rowId, 2).toString());
        textField4.setText(model.getValueAt(rowId, 3).toString());
        textField5.setText(model.getValueAt(rowId, 4).toString());
        textField6.setText(model.getValueAt(rowId, 5).toString());
        textField7.setText(model.getValueAt(rowId, 6).toString());
        textField8.setText(model.getValueAt(rowId, 7).toString());
        textField9.setText(model.getValueAt(rowId, 8).toString());
        textField10.setText(model.getValueAt(rowId, 9).toString());
    }

    private String getValue(String val){
        if(val!="" && !val.isEmpty()){
            return val;
        }else {
            return " ";
        }
    }
}
