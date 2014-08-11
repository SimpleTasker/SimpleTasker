package com.simpletasker.common.util;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Created by David on 11-8-2014.
 */
public class FileUtilities {

    public static final String charset = "UTF-8";

    public static String getStringfromFile(File f) {
        InputStream in = null;
        try {
            in = new FileInputStream(f);
            int byteAvailable = in.available();
            byte[] data = new byte[byteAvailable];
            int read = in.read(data);
            if(read != byteAvailable) {
                System.out.println("Bytes read in not equal to bytes available");
            }
            return new String(data,charset);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {
            if(in!=null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    public static boolean writeStringToFile(File f,String s) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(f);
            out.write(s.getBytes(charset));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(out!=null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    public static boolean appendStringToFile(File f,String s){
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));
            out.println(s);
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
