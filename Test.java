/**
 * @(#)Test.java, 2018年6月21日. Copyright 2018 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Test {

    private int a = 1;

    private int b = 1;
    
    private int id = 0;
    
    private int count =0;

    private Configuration configuration = null;

    private String[] testType = {
        "(单选题)", "(不定向选择题)", "(填空题)", "(编程题)", "(解答题)"
    };

    public Test() {
        configuration = new Configuration(Configuration.VERSION_2_3_28);
        configuration.setDefaultEncoding("UTF-8");
    }

    public static void main(String[] args) throws Exception {
        // System.out.println(testName);
        Test test = new Test();
        test.createWord();
        System.out.println("操作了" + test.b + "套题");
        System.out.println("生成了" + test.a + "道题");

    }

    public void createWord() throws Exception {
        String testName;

        File files = new File("/Users/heqiang/Desktop/newJson/");

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
                    // System.out.println(testName);
                    File[] listFiles2 = fl.listFiles();
                    for (File file: listFiles2) {
                       
                        
                        if (!file.isHidden()&&file.length()>0) {
                            System.out.println(file.getName());
                            fangfa(file, testName);
                        }

                    }

                }

            }

        }

    }

    public void fangfa(File file, String testName) throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FileReader reader = new FileReader(file);
        BufferedReader bReader = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder();
        String s = "";
        while ((s = bReader.readLine()) != null) {
            sb.append(s + "\n");

        }
        bReader.close();
        // System.out.println(s);
        String str = sb.toString().trim();
        String paperName = json(dataMap, str);

        try {
            configuration.setDirectoryForTemplateLoading(
                    new File("/Users/heqiang/Desktop/"));
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        Template t = null;
        try {
            t = configuration.getTemplate("htmlmoban.ftl");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = "/Users/heqiang/Desktop/2018校招题最新版/" + testName + "/";
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        File outFile = new File(path + paperName + "_"+id+"_" +count+"道题"+".html");
        if (!f.exists()) {
            f.createNewFile();
        }
        Writer out = null;
        try {
            out = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(outFile)));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        try {
            t.process(dataMap, out);
            b++;
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String json(Map<String, Object> dataMap, String str)
            throws Exception {
        String paperName;
        JSONObject jo = JSONObject.parseObject(str);

        paperName = JSONObject.parseObject(str).getString("title").replaceAll("/", "-");
        JSONArray ja = jo.getJSONArray("questions");
        String iid = jo.getString("id");
        id = Integer.parseInt(iid);
        List<Map<String, Object>> list = new ArrayList<>();

        for (int i = 0; i < ja.size(); i++) {

            Map<String, Object> map = new HashMap<String, Object>();
            JSONObject j = JSONObject.parseObject(ja.get(i).toString())
                    .getJSONObject("question");
            String s1 = j.getString("content");
            // System.out.println(s1);
            s1 = s1.replaceAll("&amp;", "&").replaceAll("&lt;", "<")
                    .replaceAll("&quot;", "\"").replaceAll("&gt;", ">");
            String type = j.getString("type");
            String s2 = del(s1, type, j);
            // System.out.println(s2);
            map.put("num", i + 1 + ".");
            map.put("timu", s2);
            count = ja.size();
            a++;
            list.add(map);
        }
        System.out.println(paperName);
        dataMap.put("paperName", paperName);
        String url = "<a href=http://m.nowcoder.com/test/exam?tid="+id+">题目参考链接</a>";
        dataMap.put("url", url);
        dataMap.put("list1", list);
        return paperName;
    }

    public String del(String htmlStr, String type, JSONObject j)
            throws Exception {
        htmlStr = "<p><b>" + htmlStr + "</b></p>";
        if (type == null) {
            System.err.println();
        }
        if (Integer.parseInt(type) == 1 || Integer.parseInt(type) == 2) {
            htmlStr = "<b>" + testType[Integer.parseInt(type) - 1] + "</b>"
                    + htmlStr;
            JSONArray answer = j.getJSONArray("answer");
            for (int i = 0; i < answer.size(); i++) {
                String s = answer.getString(i);

                JSONObject js = JSONObject.parseObject(s);
                String s1 = js.getString("content").replaceAll("&amp;", "&")
                        .replaceAll("&amp;", "&");
                // System.out.println(s1);
                if (s1.startsWith("&lt;img")) {
                    s1 = s1.replaceAll("&lt;", "<").replaceAll("&quot;", "\"")
                            .replaceAll("&gt;", ">");
                }
                if (js.getString("type").equals("1")) {
                    htmlStr = htmlStr + "<p style='color:red'>"
                            + (char) (i + 65) + "." + s1 + "</p>";
                } else {
                    htmlStr = htmlStr + "<p>" + (char) (i + 65) + "." + s1
                            + "</p>";

                }

            }

        } else if (Integer.parseInt(type) == 4&&j.getString("inputDesc")!=null) {
            String inputDesc = j.getString("inputDesc").replaceAll("&amp;", "&")
                    .replaceAll("&lt;", "<").replaceAll("&quot;", "\"")
                    .replaceAll("&gt;", ">");
            String outputDesc = j.getString("outputDesc")
                    .replaceAll("&amp;", "&").replaceAll("&lt;", "<")
                    .replaceAll("&quot;", "\"").replaceAll("&gt;", ">");
            String inputSample = j.getString("inputSample")
                    .replaceAll("&amp;", "&").replaceAll("&lt;", "<")
                    .replaceAll("&quot;", "\"").replaceAll("&gt;", ">");
            String outputSample = j.getString("outputSample")
                    .replaceAll("&amp;", "&").replaceAll("&lt;", "<")
                    .replaceAll("&quot;", "\"").replaceAll("&gt;", ">");
            if (inputDesc != null && outputDesc != null && inputSample != null
                    && outputSample != null) {
                htmlStr = testType[Integer.parseInt(type) - 1] + htmlStr;
                htmlStr = htmlStr + "<p>输入描述：</p><p>" + inputDesc + "</p>"
                        + "<p>输出描述：</p><p>" + outputDesc + "<p>输入例子：</p><p>"
                        + inputSample + "<p>输出例子：</p><p>" + outputSample
                        + "</p>";
            } else {
                htmlStr = testType[Integer.parseInt(type) - 1] + htmlStr;
            }

        } else {
            htmlStr = testType[Integer.parseInt(type) - 1] + htmlStr;
        }

        return htmlStr.replaceAll("牛客网", "粉笔网").replaceAll("牛牛", "小粉笔")
                .replaceAll("犇犇", "龙龙").trim();
    }

}
