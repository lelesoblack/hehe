/**
 * @(#)JsFix.java, 2018年8月8日. Copyright 2018 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * @author heqiang
 */
public class JsFix {
    public static void main(String[] args) throws Exception {
        String testName;

        File files = new File("/Users/heqiang/Desktop/xztk/");

        if (files.isDirectory()) {
            File[] listFiles = files.listFiles();
            for (File file: listFiles) {
                if (file.getName().startsWith(".")) {
                    file.delete();
                }
            }

            listFiles = files.listFiles();

            for (File fl: listFiles) {
                if (fl.isDirectory()) {
                    testName = fl.getName();
                    System.out.println(testName);
                    File[] listFiles2 = fl.listFiles();
                    for (File file: listFiles2) {
                        if (!file.isHidden()) {
                            FileReader reader = new FileReader(file);
                            BufferedReader bReader = new BufferedReader(reader);
                            StringBuilder sb = new StringBuilder();
                            String s = "";
                            while ((s = bReader.readLine()) != null) {
                                sb.append(s + "\n");

                            }
                            String ss = sb.toString().trim();
                            if(ss.contains("img")) {
                                System.err.println(file.getName());
                            }
                            ss = ss.replaceAll("id:","\"id\":").replaceAll("title:", "\"title\":")
                                    .replaceAll("questions:", "\"questions\":").replaceAll("paperId:", "\"paperId\":")
                                    .replaceAll("leftTime:", "\"leftTime\":").replaceAll("curPos:", "\"curPos\":")
                                    .replaceAll("userAnswer:", "\"userAnswer\":");
                            if(ss.endsWith(";")) {
                                ss = ss.substring(0, ss.lastIndexOf("};")+1);
                            }
                            File fff =new File("/Users/heqiang/Desktop/newJson/"+testName+"/");
                            if(!fff.exists()) {
                                fff.mkdirs();
                            }
                            File ff = new File("/Users/heqiang/Desktop/newJson/"+testName+"/"+file.getName());
                            if(!ff.exists()) {
                                ff.createNewFile();
                            }
                            FileWriter fw = new FileWriter(ff);
                            fw.write(ss);
                            fw.flush();

                        }

                    }

                }

            }

        }

    }

}
