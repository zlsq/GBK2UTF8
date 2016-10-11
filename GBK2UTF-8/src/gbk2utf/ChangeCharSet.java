package gbk2utf;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by shuqin on 2016/9/8.
 */
public class ChangeCharSet {
    static List<File> list = new ArrayList<File>();

    public void fileFilter(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles(new FileFilter(){
                @Override
                public boolean accept(File pathname) {
                    return pathname.getName().endsWith(".java") || pathname.isDirectory();
                }

            });

            for (File file : files) {
                fileFilter(file);//递归
            }
        } else{
            list.add(dir);
        }

    }


    public void changeCharSet(File file, String srcCSN, String desCSN) throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file),srcCSN
                )
        );
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }

        PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(
                        new FileOutputStream(file),desCSN
                )
        );
        pw.print(sb.toString());

        br.close();
        pw.close();
        //System.out.println("转码完毕");

    }


    public static void main(String[] args) {
        ChangeCharSet ccs = new ChangeCharSet();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入源文件夹的名称:");
        String dirString = scanner.nextLine();
        ccs.fileFilter(new File(dirString));
        for (File file : ChangeCharSet.list) {
            try {
                ccs.changeCharSet(file, "GBK", "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("转码完毕");
    }


















}
