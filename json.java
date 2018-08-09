/**
 * @(#)json.java, 2018年6月21日. 
 * 
 * Copyright 2018 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package demo;

import java.io.File;

/**
 * @author heqiang
 *
 */
public class json {
    
    
    public static void main(String[] args){
        
        File file = new File("/Users/heqiang/Desktop/百度");
        
        if(file.isDirectory()) {
            File[] listFiles = file.listFiles();
            int index = 1;
            for(File f:listFiles ){
                File ff = new File("/Users/heqiang/Desktop/百度/"+index+".json");
                f.renameTo(ff);
                index++;
            }
            
            
            
        }
        
    }
}
