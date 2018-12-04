package com.company.lab2;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class DataWriter {
    public void saveToFile(String data){
        try{
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(FileUtility.FILEPATH));
            fileWriter.write(data);
            fileWriter.flush();
            fileWriter.close();

            String source = FileUtility.FILEPATH;
            File fileToZip = new File(source);
            FileInputStream fis = new FileInputStream(fileToZip);

            FileOutputStream fos = new FileOutputStream(FileUtility.OUTPUT_FILE);
            ZipOutputStream zos = new ZipOutputStream(fos);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zos.putNextEntry(zipEntry);
            int length;
            final byte[] buffer = new byte[1024];

            while ((length = fis.read(buffer)) >= 0){
                zos.write(buffer, 0, length);
            }
            zos.close();
            fis.close();
            fos.close();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void saveToFile(String data, File file){
        try{
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
            fileWriter.write(data);
            fileWriter.flush();
            fileWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
