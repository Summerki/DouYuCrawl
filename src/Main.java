import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        DouYuCrawl server = new DouYuCrawl("1126960");

        while (true) {
//            System.out.println(server.read());
            regexMatches(server.read());
            Thread.sleep(1);
        }

    }



    // 通过正则表达式匹配对象
    public static void regexMatches(String str) throws IOException {
        String nn;  // 斗鱼用户昵称
        String txt;  // 斗鱼用户弹幕发送内容
        String danMu;  // 按照指定格式拼接好的str

//        System.out.println(str);  // 原字符串
        String regexStr = "(.*)(nn@=)(.*)(/txt@=)(.*)(/cid@=.*)";
        Pattern pattern = Pattern.compile(regexStr);
        Matcher matcher = pattern.matcher(str);
        if(matcher.find()){
            nn = matcher.group(3);
//            System.out.println(nn);
            txt = matcher.group(5);
//            System.out.println(txt);

            danMu = "用户: " + nn + "\t" + "  发送弹幕: " + txt + "\r\n";
            strToFile(danMu);
            System.out.println("用户: " + nn + "\t" + "  发送弹幕: " + txt);
        }
    }


    public static String filePath = "./DanMuFile/DanMu.txt";
    public static void strToFile(String str) throws IOException {
        FileWriter fw = new FileWriter(filePath, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(str);
        bw.close();
        fw.close();
    }


}


