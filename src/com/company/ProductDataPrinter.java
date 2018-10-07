package com.company;

import java.util.StringTokenizer;

public class ProductDataPrinter {

    private String category;
    private String[] processorTemplate = new String[]{"Producent: "
            ,"Model: "
            ,"Liczba rdzeni: "
            ,"Seria: "
            ,"Taktowanie: "
            ,"Cache L1: "
            ,"Cache L2: "
            ,"Cache L3: "
            ,"Cena: "
            ,"Opakowanie: "};
    private String[] diskTemplate = new String[]{"Producent: "
            ,"Model: "
            ,"Interfejs: "
            ,"Pojemność: "
            ,"Prędkość odczytu: "
            ,"Prędkość zapisu: "
            ,"Rodzaj pamięci: "
            ,"Rozmiar: "
            ,"Cena: "
            ,"Opakowanie: "};
    private String[] graphicsTemplate = new String[]{"Producent: "
            ,"Model: "
            ,"Pamięć: "
            ,"Rodzaj pamięci: "
            ,"Taktowanie pamięci:  "
            ,"Taktowanie procesora: "
            ,"Rodzaje złącz: "
            ,"Cena: "};

    public void setCategory(String category) {
        this.category = category;
    }

    public void printData(StringTokenizer tokenizer){
        switch (category){
            case "PROC" :
                printProcessorData(tokenizer);
                break;
            case "DISC":
                printDiscData(tokenizer);
                break;
            case "GRAPH":
                printGraphicsData(tokenizer);
                break;
        }
    }

    public void printCategory(){
        switch (category){
            case "PROC":
                System.out.println("Kategoria:\tProcesory");
                break;
            case "DISC":
                System.out.println("Kategoria:\tDyski");
                break;
            case "GRAPH":
                System.out.println("Kategoria:\tKarty graficzne");
                break;
        }
    }

    private void printProcessorData(StringTokenizer tokenizer){
        printDataLine(buildDataLine(processorTemplate, tokenizer));
    }

    private void printDiscData(StringTokenizer tokenizer){
        printDataLine(buildDataLine(diskTemplate, tokenizer));
    }

    private void printGraphicsData(StringTokenizer tokenizer){
        printDataLine(buildDataLine(graphicsTemplate, tokenizer));
    }

    private String[] buildDataLine(String[] template, StringTokenizer tokenizer){
        String[] line = template.clone();
        for(int i=0; i<line.length; i++){
            String token = getTokenValue(tokenizer.nextToken());
            String space = getSpace(line[i].length(), token.length());
            line[i] = line[i]+token+space;
        }
        return line;
    }

    private void printDataLine(String[] line){
        for(int i=0; i<line.length; i++){
            System.out.print(line[i]);
        }
        System.out.print("\n");
    }

    private String getTokenValue(String token){
        if(token.equals(" ")){
            return "<<brak danych>>";
        }else {
            return token;
        }
    }

    private String getSpace(int templateWordLength, int tokenLength){
        StringBuilder space = new StringBuilder();
        int x = templateWordLength+tokenLength;
        int y = 40 - x;
        double z = Math.ceil((double)y/8);
        for(int i=0; i<z; i++){
            space.append("\t");
        }
        return space.toString();
    }
}

