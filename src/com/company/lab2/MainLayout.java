package com.company.lab2;

import com.company.lab2.DataWriter;
import com.company.lab2.SelectionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class MainLayout extends JFrame {
    private JPanel rootPanel;
    private JTable table1;
    private JButton SaveButton;
    private JButton saveToDbBtn;
    private JTable table2;
    private JTable table3;
    private JButton readFromDatabaseButton;
    private JButton saveAsButton;
    private DefaultTableModel model;
    private DefaultTableModel model2;
    private DefaultTableModel model3;
    private boolean editMode;
    private int activeRow;
    private DataWriter dataWriter;
    public Date date = null;

    public MainLayout(){
        editMode = false;
        dataWriter = new DataWriter();
        ListSelectionModel lsm = table1.getSelectionModel();
        lsm.addListSelectionListener(new SelectionListener(this));
        table1.setSelectionModel(lsm);
        add(rootPanel);
        setTitle("Skuliowski Lab 2");
        setSize(600, 500);


        SaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                /*for(int row=0; row<model.getRowCount(); row++){
                    for(int col=0; col<10; col++){
                        data+=getValue(model.getValueAt(row,col).toString());
                        if(col != 9){
                            data+="|";
                        }
                    }
                    data+="\n";
                }*/
                String data = getDataToSave();
                dataWriter.saveToFile(data);
            }
        });

        saveToDbBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DbUtility utility = new DbUtility();
                try{
                    utility.prepareDb();
                }catch (Exception ex){
                    ex.printStackTrace();
                }

                ArrayList<String[]> rows = getDatabaseRowsFromModel(model);
                saveToDb(utility, rows, "PROC");
                rows = getDatabaseRowsFromModel(model2);
                saveToDb(utility, rows, "DISC");
                rows = getDatabaseRowsFromModel(model3);
                saveToDb(utility, rows, "GRAPH");
                utility.closeConnection();
            }
        });

        readFromDatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DbUtility utility = new DbUtility();
                ResultSet processors =  utility.getData("Processors");
                ResultSet discs = utility.getData("Discs");
                ResultSet graphics = utility.getData("Graphics");
                String[] procHead = getHeaders(processors);
                String[] discHead = getHeaders(discs);
                String[] graphHead = getHeaders(graphics);
                Object[][] procData = getData(processors);
                Object[][] discData = getData(discs);
                Object[][] graphData = getData(graphics);

                setUpTable(procHead, procData);
                setUpTable2(discHead, discData);
                setUpTable3(graphHead, graphData);
             }
        });

        saveAsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    dataWriter.saveToFile(getDataToSave(), file);
                }
            }
        });
    }

    private String[] getHeaders(ResultSet results){
        try{
            ResultSetMetaData meta = results.getMetaData();
            int count = meta.getColumnCount();
            String[] headers = new String [count];
            for(int i = 1; i <= count; i++){
                headers[i-1] = meta.getColumnLabel(i);
            }
            return headers;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private Object[][] getData(ResultSet set){
        try{
            int rowCounter = 0;
            ResultSetMetaData meta = set.getMetaData();
            int columnCount = meta.getColumnCount();
            set.last();
            int rowCount = set.getRow();
            set.beforeFirst();
            Object[][] data = new Object[rowCount][columnCount+1];
            while (set.next()){
                for(int i = 1; i <= columnCount; i++){
                    data[rowCounter][i-1] = set.getObject(i);
                }
                rowCounter++;
            }
            return data;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    private void saveToDb(DbUtility utility, ArrayList<String[]> rows, String group){
        for(String[] row : rows){
            try{
                utility.saveProductToDatabase(group, row);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private String getDataFromModel(DefaultTableModel model){
        String temp = "";
        for(int row=0; row<model.getRowCount(); row++){
            temp += "PRD|";
            for(int col=0; col<model.getColumnCount(); col++){
                temp+=getValue(model.getValueAt(row,col).toString());
                if(col != model.getColumnCount()-1){
                    temp+="|";
                }
            }
            temp+="\n";
        }
        return temp;
    }

    private ArrayList<String[]> getDatabaseRowsFromModel(DefaultTableModel model){
        ArrayList<String[]> rows = new ArrayList<>();

        for(int row=0; row<model.getRowCount(); row++){
            String[] data = new String[model.getColumnCount()];
            for(int col=0; col<model.getColumnCount(); col++){
                data[col] = getValue(model.getValueAt(row,col).toString());
            }
            rows.add(data);
        }

        return rows;
    }

    private String getDataToSave(){
        String data = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String month = String.valueOf(Integer.valueOf(calendar.get(Calendar.MONTH))+ 1);
        data += "RMD|"
                + calendar.get(Calendar.DAY_OF_MONTH) +"|"
                + ((Integer.valueOf(month) < 10) ? (0 + month) : (month) ) + "|"
                + calendar.get(Calendar.YEAR)+"\n";
        data += "GRP|PROC\n";
        data += getDataFromModel(model);
        data += "GRP|DISC\n";
        data += getDataFromModel(model2);
        data += "GRP|GRAPH\n";
        data += getDataFromModel(model3) + "\n";
        return data;
    }

    public void setUpTable(String[] headers, Object[][] data){
        this.model = getTableModel(headers, data);
        table1.setModel(model);
        table1.setAutoCreateColumnsFromModel(true);
    }

    public void setUpTable2(String[] headers, Object[][] data){
        this.model2 = getTableModel(headers, data);
        table2.setModel(model2);
        table2.setAutoCreateColumnsFromModel(true);
    }

    public void setUpTable3(String[] headers, Object[][] data){
        this.model3 = getTableModel(headers, data);
        table3.setModel(model3);
        table3.setAutoCreateColumnsFromModel(true);
    }

    private DefaultTableModel getTableModel(String[] headers, Object[][] data){
        DefaultTableModel localModel = new DefaultTableModel(data, headers);
        return localModel;
    }

    private String getValue(String val){
        if(val!="" && !val.isEmpty()){
            return val;
        }else {
            return " ";
        }
    }
}
