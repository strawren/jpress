package com.orientpay.clivia.portal.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 根据指定目录读取该目录和子目录的文件
 * @author liukh
 *
 */
public class FileViewer {


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
        return FileViewer.listFile(lstFileNames, file, resPath, suffix, isdepth);
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

}
