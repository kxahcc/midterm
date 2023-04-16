//package org.javaparser.examples.chapter2;
//
//import com.github.javaparser.StaticJavaParser;
//import com.github.javaparser.ast.CompilationUnit;
//import com.github.javaparser.ast.body.MethodDeclaration;
//import com.github.javaparser.ast.stmt.BlockStmt;
//import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
//
//import java.io.FileInputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class VoidVisitorComplete {
//
//    private static final String FILE_PATH = "src/main/java/org/javaparser/samples/ReversePolishNotation.java";
//
//    public static void main(String[] args) throws Exception {
//
//        CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(FILE_PATH));
//
//        MethodVisitorAdapter methodVisitor = new MethodVisitorAdapter();
//        cu.accept(methodVisitor, null);
//
//        List<Map<String, String>> methods = methodVisitor.getMethods();
//        for (Map<String, String> method : methods) {
//            System.out.println("Method Name: " + method.get("name"));
//            System.out.println("Method Body: " + method.get("body"));
//        }
//    }
//
//    private static class MethodVisitorAdapter extends VoidVisitorAdapter<Void> {
//        private final List<Map<String, String>> methods = new ArrayList<>();
//
//        @Override
//        public void visit(MethodDeclaration md, Void arg) {
//            super.visit(md, arg);
//            Map<String, String> method = new HashMap<>();
//            method.put("name", md.getNameAsString());
//            BlockStmt body = md.getBody().orElse(null);
//            if (body != null) {
//                method.put("body", body.toString());
//            } else {
//                method.put("body", "");
//            }
//            methods.add(method);
//        }
//
//        public List<Map<String, String>> getMethods() {
//            return methods;
//        }
//    }
//
//}
package org.javaparser;

import com.github.javaparser.*;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoidVisitorComplete {

//    private static final String DIR_PATH = "src/main/java/org/javaparser/samples/";

    public List<String> absoluteFile =new ArrayList<>();
    public List<String> filename =new ArrayList<>();
    public List<String> getFilename(){
        return filename;
    }
    public List<String> getAbsoluteFile(){
        return absoluteFile;
    }
    public Map<File,List<Map<String, String>>> run(String DIR_PATH) throws Exception{

        File dir = new File(DIR_PATH);
//        File[] files = dir.listFiles((dir1, name) -> name.endsWith(".java"));

        List<File> files = getJavaFiles(new File(DIR_PATH));

        // 输出Java文件路径
        for (File javaFile : files) {
            filename.add(javaFile.getName());
            absoluteFile.add(javaFile.getAbsolutePath());
        }

        Map<File,List<Map<String, String>>> results = new HashMap<>();

        for (File file : files) {



            CompilationUnit cu = null;
            try {
                cu = StaticJavaParser.parse(new FileInputStream(file));
            } catch (Exception e) {
                continue;
//                e.printStackTrace();
            }
            MethodVisitorAdapter methodVisitor = new MethodVisitorAdapter();

            cu.accept(methodVisitor, null);

            List<Map<String, String>> methods = methodVisitor.getMethods();
            results.put(file,methods);




        }
//        for (Map.Entry<File, List<Map<String, String>>> entry : results.entrySet()) {
//            File file = entry.getKey();
//            List<Map<String, String>> result = entry.getValue();
//            System.out.println("File Name: "+file.getAbsolutePath());
//        }
        return results;

    }
    private static List<File> getJavaFiles(File dir) {
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
                List<File> subJavaFiles = getJavaFiles(file);
                javaFiles.addAll(subJavaFiles);
            } else {
                // 如果是Java文件，则将其添加到列表中
                if (file.getName().endsWith(".java")) {
                    javaFiles.add(file);
                }
            }
        }

        return javaFiles;
    }
    private static class MethodVisitorAdapter extends VoidVisitorAdapter<Void> {

        private final List<Map<String, String>> methods = new ArrayList<>();

        @Override
        public void visit(MethodDeclaration md, Void arg) {

            super.visit(md, arg);

            Map<String, String> method = new HashMap<>();
            method.put("name", md.getNameAsString());
            method.put("declareName",md.getDeclarationAsString());
            BlockStmt body = md.getBody().orElse(null);
            if (body != null) {

                method.put("body", body.toString());
            } else {

                method.put("body", "");
            }

            method.put("line", String.valueOf(md.getBegin().get().line));


            methods.add(method);
        }

        public List<Map<String, String>> getMethods() {
            return methods;
        }
    }

}