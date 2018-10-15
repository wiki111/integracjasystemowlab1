package com.company.lab2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Statement;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtility {

    public static final String FILEPATH = "/home/reter/Dokumenty/ProjektyStudia/SkulimLab1/src/com/company/new_data.txt";
    private static final String zipfilepath = "/home/reter/Dokumenty/ProjektyStudia/SkulimLab1/src/com/company/new_data.zip";
    private static final String outputPath ="/home/reter/Dokumenty/ProjektyStudia/SkulimLab1/src/com/company/";
    public static final String OUTPUT_FILE = "/home/reter/Dokumenty/ProjektyStudia/SkulimLab1/src/com/company/new_data.zip";


    public static File getFile() throws IOException{
        //File dataFile = new File(path.getFile());
        File file = null;
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipfilepath));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null){
            String filename = zipEntry.getName();
            File newFile = new File(outputPath+filename);
            file = newFile;
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0){
                fos.write(buffer, 0, len);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
        //File dataFile = new File(filepath);
        return file;
    }

}
