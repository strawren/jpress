package com.strawren.bsp.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 根据指定目录读取该目录和子目录的文件
 * @author liukh
 *
 */
public class FileUtils {
	private static Log log = LogFactory.getLog(FileUtils.class);
	
	private FileUtils(){
        
    }

    /**
     * 根据指定目录读取该目录和子目录的文件，返回list
     * @param resPath
     * @param suffix
     * @param isdepth
     * @return
     */
    public static List<String> getListFiles(String resPath, String suffix, boolean isdepth) {
        List<String> lstFileNames = new ArrayList<String>();
        File file = new File(resPath);
        return FileUtils.listFile(lstFileNames, file, resPath, suffix, isdepth);
       }

       private static List<String> listFile(List<String> lstFileNames, File f, String resPath, String suffix, boolean isdepth) {

        // 若是目录, 采用递归的方法遍历子目录
        if (f.isDirectory()) {
         File[] t = f.listFiles();

         for (int i = 0; i < t.length; i++) {
          if (isdepth || t[i].isFile()) {
           listFile(lstFileNames, t[i], resPath, suffix, isdepth);
          }
         }
        } else {
         String filePath = f.getAbsolutePath();

         if (!suffix.equals("")) {
          int lastIndex = filePath.lastIndexOf("."); // 最后一个.(即后缀名前面的.)的索引
          String tempsuffix = "";

          if (lastIndex != -1) {
           tempsuffix = filePath.substring(lastIndex + 1, filePath.length());
           if (tempsuffix.equals(suffix)) {
            lstFileNames.add(subStringNoSprit(filePath,resPath));
           }
          }
         } else {
          lstFileNames.add(subStringNoSprit(filePath,resPath));
         }
        }
        return lstFileNames;
       }

       public static String subString(String resStr, String subStr){
           int strIndex = subStr.length();
           String returnStr = resStr.substring(strIndex);
           return returnStr;
       }

       public static String subStringNoSprit(String resStr, String subStr){
           int strIndex = subStr.length();
           String returnStr = resStr.substring(strIndex + 1);
           return returnStr;
       }

       public static String replaceSuffix(String str, String suffix){
           return str.replaceAll(str, suffix);
       }


       public static void createPath(String str){
           int poitIndex = str.lastIndexOf("/");
           String path = str.substring(0, poitIndex + 1);
           File filePath = new File(path);
           if(!filePath.exists()){
               filePath.mkdirs();
           }
       }

       public static String replaceBackSprit(String str){
           int index = str.indexOf("\\");
           if(index > 0){
               String retStr = str.replace("\\", "/");
               return retStr;
           }
           return str;
       }

       public static boolean isFileExsit(String filePathAndName){
           log.debug("filePathAndName... filePathAndName--> " + filePathAndName);
           
           try{            
               File file = new File(filePathAndName);
               if(file.exists()){
                   return true;
               }
               else{
                   return false;
               }
           }
           catch(Exception e){
               log.error(null, e);
               return false;
           }
       }
       
       public static boolean makeDir(String filePath){
           log.debug("makeDir... filePath--> " + filePath);
           
           try{            
               File file = new File(filePath);
               if(file.exists()){
                   return true;
               }
               else{
                   if(file.mkdirs()){
                       return true;
                   }
                   else{
                       return false;
                   }
               }
           }
           catch(Exception e){
               log.error(null, e);
               return false;
           }
       }
       
       /**
        * 删除文件
        * @param fileName
        * @return
        */
       public static boolean deleteFile(String fileName) {
           return new File(fileName).delete();
       }

       /**
        * 读取文件内容，并把每行作为string存放到list中
        * @param file
        * @return
        * @throws IOException
        */
       public static List<String> readFile(File file) throws IOException {
           List<String> content = new ArrayList<String>();
           FileReader reader = new FileReader(file);
           BufferedReader br = new BufferedReader(reader);
           String lineInfo = br.readLine();
           while (lineInfo != null) {
               content.add(lineInfo);
               lineInfo = br.readLine();
           }
           br.close();
           reader.close();
           return content;
       }

       public static void writeFile(String filePath, String fileName, List<String> fileContent) throws IOException {
           File file = new File(filePath + fileName);
           file.createNewFile();
           FileWriter write = new FileWriter(file);

           for (int i = 0; i < fileContent.size(); i++) {
               write.write(fileContent.get(i));
           }

           write.flush();
           write.close();
       }

       public static String getLastLine(File file) throws IOException {
           String lastLine = "";
           RandomAccessFile rf = new RandomAccessFile(file, "r");
           long count = file.length();
           String beginLine = rf.readLine();
           if (beginLine != null) {
               long seek=0;
               String secondLine=rf.readLine();
               if(secondLine!=null){
                   seek=count - secondLine.length() * 2;
               }else{
                   seek=count - beginLine.length() * 2;
               }
               if(seek<0){
                   seek=0;
               }
               rf.seek(seek);
           }
           String ss = beginLine;
           while (ss != null) {
               lastLine = ss;
               ss = rf.readLine();
           }
           rf.close();
           return lastLine;
       }
       
       public static String readFileToString(File file) throws IOException {
           StringBuffer sb = null;
           BufferedReader in = null;
           try {
               in = new BufferedReader(new FileReader(file));
               sb = new StringBuffer();
               for (String line; (line = in.readLine()) != null;) {
                   sb.append(line + "\r\n");
               }
           } finally {
               if (in != null)
                   in.close();
           }

           return sb.toString();
       }
       
       public static List<String> readLines(File file, String charset) throws IOException {
    	   List<String> ret = new ArrayList<String>(10);
           BufferedReader in = null;
           try {
               in = new BufferedReader(new FileReader(file));
               for (String line; (line = in.readLine()) != null;) {
            	   ret.add(line);
               }
           } finally {
               if (in != null)
                   in.close();
           }

           return ret;
       }
       
       public static String readFileToStringByEncoding(File file, String encoding) throws IOException {
           StringBuffer sb = null;
           BufferedReader in = null;
           try {
               InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
               in = new BufferedReader(read);
               sb = new StringBuffer();
               for (String line; (line = in.readLine()) != null;) {
                   sb.append(line + "\r\n");
               }
           } finally {
               if (in != null)
                   in.close();
           }
              
           return sb.toString();
       }

       public static void writeFileByEncoding(String filePath, String fileName, String fileContent, String encoding) throws IOException {
           BufferedWriter out = null;
           try {
               File file = new File(filePath);
               if (!file.exists()) {
                   file.mkdirs();
               }
               file = new File(filePath + fileName);
               file.createNewFile();
               OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file), encoding);
               out = new BufferedWriter(write);   
               out.write(fileContent);
           } finally {
               out.flush();
               out.close();
           }
       }
       
       public static void writeFileByEncoding(String fileFullPath,String fileContent,String encoding) throws IOException{
           int lastCommIndex = fileFullPath.lastIndexOf("/");
           String filePath = fileFullPath.substring(0,lastCommIndex + 1);
           String fileName = fileFullPath.substring(lastCommIndex + 1);
           writeFileByEncoding(filePath,fileName,fileContent,encoding);
       }   
       
       public static void writeFileByEncoding(File file, String fileContent, String encoding) throws IOException {
           BufferedWriter out = null;
           try {
               if (file.isDirectory()) {
                   throw new IllegalArgumentException(file.getAbsoluteFile() + "-- is a directory!!");
               }
               OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file), encoding);
               out = new BufferedWriter(write);   
               out.write(fileContent);
           } finally {
               out.flush();
               out.close();
           }
       }
       
       public static String getExtension(String filename) {
         if (filename == null) {
           return "";
         }
         
         int index = filename.lastIndexOf(46);
         int lastUnixPos = filename.lastIndexOf(47);
         int lastWindowsPos = filename.lastIndexOf(92);
         int lastSeparator = Math.max(lastUnixPos, lastWindowsPos);
         index = ((lastSeparator > index) ? -1 : index);
         
         if (index == -1) {
           return "";
         }
         
         return filename.substring(index + 1);
       }
       
       public static void copyInputStreamToFile(InputStream fis, File targetFile) {
    	   FileOutputStream write = null;
           try {
        	   targetFile.createNewFile();
        	   write = new FileOutputStream(targetFile);
               int count = fis.available();
               byte[] srcBytes = new byte[count];
               fis.read(srcBytes);
               write.write(srcBytes);
           }
           catch(Exception e) {
        	   log.warn(e.getMessage(), e);
           }
           finally {
        	   try {
	        	   write.flush();
	        	   write.close();
        	   }
        	   catch(Exception e) {
        		   log.warn(e.getMessage(), e);
        	   }
           }
       }
       
       public static void main(String [] args) throws Exception {
    	   FileInputStream ins = new FileInputStream("D:\\download\\chrome\\1-100916005016.rar");
    	   copyInputStreamToFile(ins, new File("D:\\temp\\1-100916005016.rar"));
    	   
    	   ins = new FileInputStream("D:\\download\\chrome\\《培训效果评估表》(汇总).doc");
    	   copyInputStreamToFile(ins, new File("D:\\temp\\《培训效果评估表》(汇总).doc"));
    	   
    	   ins = new FileInputStream("D:\\download\\chrome\\login.jsp");
    	   copyInputStreamToFile(ins, new File("D:\\temp\\login.jsp"));
       }
}
