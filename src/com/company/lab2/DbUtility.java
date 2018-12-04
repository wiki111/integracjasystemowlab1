package com.company.lab2;

import java.sql.*;
import java.util.Date;

public class DbUtility {

    Connection connection;

    public DbUtility() {
        connection = DbConnector.getConnection();
    }

    public void prepareDb() throws Exception{
        clearTable("Processors");
        clearTable("Graphics");
        clearTable("Discs");
    }

    public void saveProductToDatabase(String productGroup, String[] productData) throws Exception{

        String query = "select idnew_table from Grupa where Shortname='" + productGroup + "'";
        Statement st = connection.createStatement();
        ResultSet results = st.executeQuery(query);

        String groupId = "";

        while(results.next()){
            groupId = String.valueOf(results.getInt("idnew_table"));
        }

        if(productGroup.equals("PROC")){
            saveProcessor(productData, groupId);
        }else if(productGroup.equals("GRAPH")){
            saveGraph(productData, groupId);
        }else if(productGroup.equals("DISC")){
            saveDisc(productData, groupId);
        }

    }

    private void saveProcessor(String[] data, String groupId){
        try{
            String query = "insert into Processors (Producent, Model, Liczba_rdzeni, " +
                    "Seria, Taktowanie, Cache_L1, Cache_L2, Cache_L3, Cena, Opakowanie, IdGrupa) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, data[0]);
            statement.setString(2, data[1]);
            statement.setString(3, data[2]);
            statement.setString(4, data[3]);
            statement.setString(5, data[4]);
            statement.setString(6, data[5]);
            statement.setString(7, data[6]);
            statement.setString(8, data[7]);
            statement.setFloat(9, Float.valueOf(data[8]));
            statement.setString(10, data[9]);
            statement.setInt(11, Integer.parseInt(groupId));
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void saveDisc(String[] data, String groupId){
        try{
            String query = "insert into Discs (Producent, Model, Interfejs, " +
                    "Pojemnosc, Odczyt, Zapis, Rodzaj, Rozmiar, Cena, Opakowanie, IdGrupa) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, data[0]);
            statement.setString(2, data[1]);
            statement.setString(3, data[2]);
            statement.setString(4, data[3]);
            statement.setString(5, data[4]);
            statement.setString(6, data[5]);
            statement.setString(7, data[6]);
            statement.setString(8, data[7]);
            statement.setFloat(9, Float.valueOf(data[8]));
            statement.setString(10, data[9]);
            statement.setInt(11, Integer.parseInt(groupId));
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void saveGraph(String[] data, String groupId){
        try{
            String query = "insert into Graphics (Producent, Model, Pamiec, " +
                    "Rodzaj_pamieci, Taktowanie_pamieci, Taktowanie_procesora, " +
                    "Rodzaje_zlacz, Cena, IdGrupa) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, data[0]);
            statement.setString(2, data[1]);
            statement.setString(3, data[2]);
            statement.setString(4, data[3]);
            statement.setString(5, data[4]);
            statement.setString(6, data[5]);
            statement.setString(7, data[6]);
            statement.setFloat(8, Float.valueOf(data[7]));
            statement.setInt(9, Integer.parseInt(groupId));
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void clearTable(String table) throws Exception{
        String sql = "DELETE from " + table;
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);

    }

    public ResultSet getData(String group){
        try{
            String sql = "";
            if(group.equals("Processors")){
                sql = "select Producent, Model, Liczba_rdzeni, Seria, Taktowanie, Cache_L1, Cache_L2, Cache_L3, Cena, Opakowanie from " + group;
            }else if(group.equals("Discs")){
                sql = "select Producent, Model, Interfejs, Pojemnosc, Odczyt, Zapis, Rodzaj, Rozmiar, Cena, Opakowanie from " + group;
            }else if(group.equals("Graphics")){
                sql = "select Producent, Model, Pamiec, Rodzaj_pamieci, Taktowanie_pamieci, Taktowanie_procesora, Rodzaje_zlacz, Cena from " + group;
            }

            Statement statement = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            return statement.executeQuery(sql);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public void closeConnection(){
        try {
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void saveDate(Date date){
        try{
            String sql = "update Cennik set Date = '" + date + "'";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
