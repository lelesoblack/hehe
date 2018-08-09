/**
 * @(#)serchJson.java, 2018年8月8日. Copyright 2018 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @author heqiang
 */
public class serchJson {
    public static void main(String[] args) throws Exception {
        String testName;

        File files = new File("/Users/heqiang/Desktop/2018校招题最新版/");

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
                    //System.out.println(testName);
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
                            if (ss.contains("img")) {
                                System.out.println(file.getName());
                            }

                        }
                    }

                }

            }

        }

    }

}
