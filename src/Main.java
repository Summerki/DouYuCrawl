import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        DouYuCrawl server = new DouYuCrawl("1126960");

        while (true) {
            System.out.println(server.read());
            Thread.sleep(1);
        }

    }

}
