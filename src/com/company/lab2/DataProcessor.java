package com.company.lab2;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class DataProcessor {

    private String[] headers;
    private ArrayList<String[]> headersList = new ArrayList<>();
    private Object[][] currentData;
    private ArrayList<Object[][]> data = new ArrayList<>();
    private ArrayList<String[]> currentDataList = new ArrayList<>();
    public Date date;

    private int dataCounter = 0;

    private String[] procHeaders = new String[] {"Producent", "Model", "Liczba_rdzeni",
            "Seria", "Taktowanie", "Cache_L1", "Cache_L2", "Cache_L3",
            "Cena", "Opakowanie"};

    private String[] discHeaders = new String[] {"Producent", "Model", "Interfejs", "Pojemnosc", "Odczyt", "Zapis", "Rodzaj", "Rozmiar", "Cena", "Opakowanie" };

    private String[] graphHeaders = new String[] {"Producent", "Model", "Pamiec", "Rodzaj_pamieci", "Taktowanie_pamieci", "Taktowanie_procesora", "Rodzaje_zlacz", "Cena"};

    public DataProcessor(){
    }

    public void saveData(Scanner scanner){

        String RMD = "";
        scanner.next();
        RMD = scanner.next() + "." + scanner.next() + "." + scanner.next() + ".";

        try{
            this.date = new SimpleDateFormat("dd.MM.yyyy").parse(RMD);
        }catch (Exception e){
            e.printStackTrace();
        }

        DbUtility utility = new DbUtility();
        utility.saveDate(new Timestamp(date.getTime()));
        utility.closeConnection();

        String prevToken = "";

        while (scanner.hasNext()){

            String temp;

            if(prevToken.equals("")){
                 temp = scanner.next();
            }else {
                temp = prevToken;
                prevToken = "";
            }


            if(temp.equals("GRP") ){

                temp = scanner.next();

                if(temp.equals("PROC")){
                    headers = procHeaders;
                    headersList.add(headers);
                }

                if(temp.equals("DISC")){
                    headers = discHeaders;
                    headersList.add(headers);
                }

                if(temp.equals("GRAPH")){
                    headers = graphHeaders;
                    headersList.add(headers);
                }
            }

            String lineToken = scanner.next();

            while (lineToken.equals("PRD")){
                addToDataList(scanner);
                lineToken = scanner.next();
            }

            prevToken = lineToken;

            dataCounter += 1;
            currentData = prepareData(currentDataList);
            data.add(currentData);
            currentDataList.clear();
        }


    }

    private void addToDataList(Scanner scanner){
        String[] line = new String[headers.length];
        for(int i=0; i<headers.length; i++){
            line[i] = getTokenValue(scanner.next());
        }
        currentDataList.add(line);
    }

    private Object[][] prepareData(ArrayList<String[]> listOfData){
        Object[][] tempData = new Object[listOfData.size()][headers.length];
        int counter = 0;
        for(String[] line : listOfData ){
            for(int j=0; j<headers.length; j++){
                tempData[counter][j] = line[j];
            }
            counter++;
        }

        return tempData;
    }

    public String[] getHeaders(int counter){
        return headersList.get(counter);
    }

    public Object[][] getData(int counter){
        return data.get(counter);
    }

    private String getTokenValue(String token){
        if(token.equals(" ")){
            return "brak danych";
        }else {
            return token;
        }
    }

}
