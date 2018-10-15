package com.company;

import com.company.lab2.DataProcessor;
import com.company.lab2.FileUtility;
import com.company.lab2.MainLayout;

import javax.swing.*;
import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        /*String filename = "data.txt";
        URL path = Main.class.getResource(filename);
        File dataFile = new File(path.getFile());
        String line = null;
        try{
            FileReader fileReader = new FileReader(dataFile);
            BufferedReader reader = new BufferedReader(fileReader);
            ProductDataPrinter dataPrinter = new ProductDataPrinter();

            *//*while((line = reader.readLine()) != null){
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
            }*//*


            Scanner scanner = new Scanner(dataFile).useDelimiter("\\||\n");

            while((reader.readLine()) != null){
                while (scanner.hasNext()){
                    switch (scanner.next()){
                        case "RMD" :
                            System.out.println("Data:\t" + scanner.next()
                                    + "-" + scanner.next()
                                    + "-" + scanner.next() + "\n");
                            break;
                        case "GRP" :
                            System.out.println("");
                            String category = scanner.next();
                            dataPrinter.setCategory(category);
                            dataPrinter.printCategory();
                            System.out.println("");
                            break;
                        case "PRD" :
                            dataPrinter.printData(scanner);
                            break;
                    }
                }
            }

            scanner.close();

            System.out.println("\n Company appears "
                    + dataPrinter.findCompanyOccurences(
                            new Scanner(dataFile), "INTEL")
                    + " times");

            reader.close();



        }catch (Exception e){
            System.out.println("Error occured : " + e.getMessage());
        }*/

        File dataFile = null;
        try{
             dataFile = FileUtility.getFile();
        }catch (Exception e){

        }

        MainLayout mainLayout = new MainLayout();

        try{
            Scanner scanner = new Scanner(dataFile).useDelimiter("\\||\n");
            DataProcessor dataProcessor = new DataProcessor();
            dataProcessor.saveData(scanner);
            scanner.close();
            mainLayout.setUpTable(dataProcessor.getHeaders(), dataProcessor.getData());
        }catch (Exception e){
            System.out.println("Error occured : " + e.getMessage());
        }


        mainLayout.setVisible(true);
        mainLayout.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
