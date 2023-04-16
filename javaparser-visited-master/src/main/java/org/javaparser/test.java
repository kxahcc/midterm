package org.javaparser;

import org.javaparser.VoidVisitorComplete;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
    private static final String DIR_PATH = "C:\\Users\\kxahcc\\Desktop\\大创\\ant\\";
    private static final String DOT_DIR_PATH = "C:\\Users\\kxahcc\\Desktop\\大创\\dot\\";
    static String[] swc = {"ant_bug\\","ant_patch\\"};
    static String[] dep = {"1.3\\","1.4\\","1.5\\","1.6\\","1.7\\"};
    static String pattern = "(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)";
    public static void main(String[] args) throws Exception {
        Pattern p = Pattern.compile(pattern, Pattern.MULTILINE | Pattern.DOTALL);
        VoidVisitorComplete voidVisitorComplete = new VoidVisitorComplete();
        for(int j=0;j<=3;j++){
            String dirpath=DIR_PATH+swc[0]+dep[j];

            VoidVisitorComplete voidVisitorComplete1 = new VoidVisitorComplete();
            Map<File,List<Map<String, String>>> results=voidVisitorComplete1.run(dirpath);

//            List<String>filename =voidVisitorComplete1.getFilename();


            String dirpath_bug=DIR_PATH+swc[0]+dep[j+1];
            VoidVisitorComplete voidVisitorComplete2 = new VoidVisitorComplete();
            Map<File,List<Map<String, String>>> results_bug=voidVisitorComplete2.run(dirpath_bug);
//            List<String>filename1 =voidVisitorComplete1.getFilename();

            String dirpath_patch=DIR_PATH+swc[1]+dep[j+1];
            VoidVisitorComplete voidVisitorComplete3 = new VoidVisitorComplete();
            Map<File,List<Map<String, String>>> results_patch=voidVisitorComplete3.run(dirpath_patch);
//            List<String>filename2 =voidVisitorComplete1.getFilename();

            for (Map.Entry<File, List<Map<String, String>>> entry : results.entrySet()) {
                File file=entry.getKey();
                List<Map<String, String>> result = entry.getValue();
                System.out.println("File Name: "+file.getAbsolutePath());
                int f=0;

//                if(file.getName().equals("BuilderSupport.java"))System.out.println(file.getName());
                for (Map.Entry<File, List<Map<String, String>>> entry_bug : results_bug.entrySet()) {
                    File file_bug=entry_bug.getKey();
                    if(file_bug.getName().equals(file.getName())){
                        f=1;
                        List<Map<String, String>> result_bug = entry_bug.getValue();
                        for (Map<String, String> method : result) {
                            String declearName,name,body,line;
                            declearName = method.get("declareName");
                            name = method.get("name");
                            body = method.get("body");
                            line = method.get("line");
                            for (Map<String, String> method_bug : result_bug){
                                String name_bug,body_bug,line_bug,declearName_bug;
                                name_bug = method_bug.get("name");
                                declearName_bug = method_bug.get("declareName");
                                body_bug = method_bug.get("body");
                                line_bug = method_bug.get("line");
                                if(declearName_bug.equals(declearName)){

                                    Matcher m = p.matcher(body_bug);
                                    body_bug = m.replaceAll("");
                                    m = p.matcher(body);
                                    body = m.replaceAll("");

                                    if(!body_bug.equals(body)){
                                        send(file,name,line,"bug");
                                        send(file_bug,name,line_bug,"patch");
//                                        System.out.println("Method Line: " + line);
//                                        System.out.println("Method_bug Line: " + line_bug);
//                                        System.out.println("Method Name: " + declearName);
//                                        System.out.println("Method_bug Name: " + declearName_bug);
//                                        System.out.println("Method Body: " + body);
//                                        System.out.println("Method_bug Body: " + body_bug);
//                                        System.out.println("-------------------------------------------");
                                    }
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
                if(f==0){
                    for (Map.Entry<File, List<Map<String, String>>> entry_patch : results_patch.entrySet()) {
                        File file_patch=entry_patch.getKey();
                        if(file_patch.getName().equals(file.getName())){
                            List<Map<String, String>> result_patch = entry_patch.getValue();
                            for (Map<String, String> method : result) {
                                String name,body,line,declearName;
                                name = method.get("name");
                                body = method.get("body");
                                line = method.get("line");
                                declearName = method.get("declareName");
                                for (Map<String, String> method_patch : result_patch){
                                    String name_patch,body_patch,line_patch,declearName_patch;
                                    name_patch = method_patch.get("name");
                                    body_patch = method_patch.get("body");
                                    line_patch = method_patch.get("line");
                                    declearName_patch = method_patch.get("declareName");
                                    if(declearName_patch.equals(declearName)){
                                        Matcher m = p.matcher(body_patch);
                                        body_patch = m.replaceAll("");
                                        m = p.matcher(body);
                                        body = m.replaceAll("");
                                        if(!body_patch.equals(body)){
                                            send(file,name,line,"bug");
                                            send(file_patch,name,line_patch,"patch");
//                                            System.out.println("Method Line: " + line);
//                                            System.out.println("Method_patch Line: " + line_patch);
//                                            System.out.println("Method Name: " + declearName);
//                                            System.out.println("Method_patch Name: " + declearName_patch);
//                                            System.out.println("Method Body: " + body);
//                                            System.out.println("Method_patch Body: " + body_patch);
//                                            System.out.println("-------------------------------------------");
                                        }
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }


    }
    public static void send(File f, String name, String line,String type) throws InterruptedException {
        String address = f.getAbsolutePath();

        String regex = ".*\\\\(ant\\\\.+\\\\).*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(address);

        if (matcher.find()) {
            address = matcher.group(1);
        }
        String dotaddress = DOT_DIR_PATH+address+f.getName().split(".java")[0]+"\\";
        File dir = new File(dotaddress);
        File[] files = dir.listFiles((dir1, name1) -> name1.endsWith(".dot"));

        for (File dotFile : files) {

            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();
            try {
                br = new BufferedReader(new FileReader(dotFile));
                String lineread;
                while ((lineread = br.readLine()) != null) {
                    sb.append(lineread);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String dotString = sb.toString();
            Pattern nwpattern = Pattern.compile("\"(.*?)\".*?<SUB>(.*?)</SUB>");
            Matcher nwmatcher = nwpattern.matcher(dotString);
            if (nwmatcher.find()) {
                String dotname = nwmatcher.group(1);
                String dotline = nwmatcher.group(2);
                if(dotname.equals(name)&&dotline.equals(line)){
                    store(dotaddress+dotFile.getName(),type);
                    break;
                }
            }


        }

    }
    private static List<File> getDotFiles(File dir) {
        List<File> javaFiles = new ArrayList<>();

        // 获取目录下的所有文件和文件夹
        File[] files = dir.listFiles();
        if (files == null) {
            return javaFiles;
        }

        // 遍历所有文件和文件夹
        for (File file : files) {
            if (file.isDirectory()) {
                // 如果是文件夹，则递归获取其中的Java文件
                List<File> subJavaFiles = getDotFiles(file);
                javaFiles.addAll(subJavaFiles);
            } else {
                // 如果是Java文件，则将其添加到列表中
                if (file.getName().endsWith(".dot")) {
                    javaFiles.add(file);
                }
            }
        }

        return javaFiles;
    }
    public static void store(String address,String type){
        try {
            String regex = ".*\\\\(ant\\\\.+\\\\.*)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(address);
            if (matcher.find()) {
                address = matcher.group(1);
            }
//            address+=name.split(".java")[0]+"\\";
            FileWriter writer = new FileWriter("dotFileAddress.txt", true);
            if(type.equals("bug"))writer.write("\n"+address);
            else if(type.equals("patch"))writer.write("|"+address);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
