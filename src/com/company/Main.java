package com.company;

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

            /*while((line = reader.readLine()) != null){
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
            }*/




            /*while (scanner.hasNext()){
                System.out.println(scanner.next());
            }*/


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
                            new Scanner(dataFile), "PNY")
                    + " times");

            reader.close();



        }catch (Exception e){
            System.out.println("Error occured : " + e.getMessage());
        }

    }
}
