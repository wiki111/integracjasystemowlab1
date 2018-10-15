package com.company.lab2;

import java.util.ArrayList;
import java.util.Scanner;

public class DataProcessor {

    private String[] headers;
    private Object[][] data;
    private ArrayList<String[]> dataList;

    public DataProcessor(){
        headers = new String[] {"Producent", "Model", "Liczba rdzeni",
                "Seria", "Taktowanie", "Cache L1", "Cache L2", "Cache L3",
                "Cena", "Opakowanie"};
    }

    public void saveData(Scanner scanner){
        dataList = new ArrayList<>();
        while (scanner.hasNext()){
            String[] line = new String[10];
            for(int i=0; i<headers.length; i++){
                line[i] = getTokenValue(scanner.next());
            }
            dataList.add(line);
        }

        data = new Object[dataList.size()][10];
        int counter = 0;
        for(String[] line : dataList ){
            for(int j=0; j<10; j++){
                data[counter][j] = line[j];
            }
            counter++;
        }
    }

    public String[] getHeaders(){
        return headers;
    }

    public Object[][] getData(){
        return data;
    }

    private String getTokenValue(String token){
        if(token.equals(" ")){
            return "<<brak danych>>";
        }else {
            return token;
        }
    }

}
