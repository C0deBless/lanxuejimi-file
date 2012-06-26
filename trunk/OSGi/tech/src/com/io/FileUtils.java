package com.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class FileUtils {
	//private static final int BUFFER_SIZE = 1024;
	public static void save(File file,String savePath) throws FileNotFoundException{
		file.renameTo(new File(savePath));
	}
	public static void save(byte[] src,String savePath) throws IOException{
		OutputStream out=new BufferedOutputStream(new FileOutputStream( savePath,true));
        out.write(src);
        out.close();
	}
	/*private static void copy(File src, File dst) {
        try {
           InputStream in = null ;
           OutputStream out = null ;
            try {                
               in = new BufferedInputStream( new FileInputStream(src), BUFFER_SIZE);
               out = new BufferedOutputStream( new FileOutputStream(dst), BUFFER_SIZE);
                byte [] buffer = new byte [BUFFER_SIZE];
                while (in.read(buffer) > 0 ) {
                   out.write(buffer);
               } 
           } finally {
                if ( null != in) {
                   in.close();
               } 
                if ( null != out) {
                   out.close();
               } 
           } 
       } catch (Exception e) {
           e.printStackTrace();
       } 
   }*/
}
