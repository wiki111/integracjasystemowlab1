package com.company;

import javafx.beans.binding.When;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) {

        String filename = "data.txt";
        URL path = Main.class.getResource(filename);
        File dataFile = new File(path.getFile());
        String line = null;
        try{
            FileReader fileReader = new FileReader(dataFile);
            BufferedReader reader = new BufferedReader(fileReader);
            ProductDataPrinter dataPrinter = new ProductDataPrinter();

            while((line = reader.readLine()) != null){
                StringTokenizer tokenizer = new StringTokenizer(line, "|");
                while (tokenizer.hasMoreTokens()){
                    switch (tokenizer.nextToken()){
                        case "RMD" :
                            System.out.println("Data:\t" + tokenizer.nextToken()
                                    + "-" + tokenizer.nextToken()
                                    + "-" +tokenizer.nextToken() + "\n");
                            break;
                        case "GRP" :
                            System.out.println("");
                            String category = tokenizer.nextToken();
                            dataPrinter.setCategory(category);
                            dataPrinter.printCategory();
                            System.out.println("");
                            break;
                        case "PRD" :
                            dataPrinter.printData(tokenizer);
                            break;
                    }
                }
            }

            reader.close();

            /*Scanner scanner = new Scanner(dataFile).useDelimiter("\\|");
            while (scanner.hasNext()){
                System.out.println(scanner.next());
            }*/

        }catch (Exception e){
            System.out.println("Error occured : " + e.getMessage());
        }

    }
}
