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
    public static void regexMatches(String str){
        String nn;  // 斗鱼用户昵称
        String txt;  // 斗鱼用户弹幕发送内容

//        System.out.println(str);  // 原字符串
        String regexStr = "(.*)(nn@=)(.*)(/txt@=)(.*)(/cid@=.*)";
        Pattern pattern = Pattern.compile(regexStr);
        Matcher matcher = pattern.matcher(str);
        if(matcher.find()){
            nn = matcher.group(3);
//            System.out.println(nn);
            txt = matcher.group(5);
//            System.out.println(txt);
            System.out.println("用户: " + nn + "\t" + "  发送弹幕: " + txt);
        }
    }


}


