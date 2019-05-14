import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

// 第三方连接斗鱼弹幕服务器
public class DouYuCrawl {
    private static String host = "openbarrage.douyutv.com";  // IP

    private static int port = 8601;  // Port

    private Socket serverSocket;

    private String roomId;

    // 构造函数
    public DouYuCrawl(String roomId) {
        this.roomId = roomId;

        try {
            connect();
            login();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connect() throws IOException {
        serverSocket = new Socket(host, port);

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        send("type@=mrkl");  // 该消息用于维持与后台间的心跳（新版）
                        Thread.sleep(30000); // 30000ms == 30s
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void login() throws IOException {
        send("type@=loginreq/roomid@=" + roomId);   // 登录请求消息，该消息用于完成登录授权
        send("type@=joingroup/rid@=" + roomId + "/gid@=-9999");  // 入组消息，该消息用于完成加入房间分组
    }

    public String read() throws IOException {
        // ByteBuffer.wrap(btte[] array)获取ByteBuffer实例，缓冲区的数据会存放在byte[]数组中
        // ByteOrder.LITTLE_ENDIAN:指示为小端数
        // getInt():从ByteBuffer中读出一个int值
        int msgSize = ByteBuffer.wrap(getBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt();
        byte[] msgBytes =getBytes(msgSize);

        return new String(msgBytes, 8, msgSize - 9);
    }

    public void send(String msg) throws IOException {
        // 取入数据就用 getInputStream()
        // 传出数据就用 getOutputStream()
        serverSocket.getOutputStream().write(getSendBytes(msg));
        serverSocket.getOutputStream().flush();
    }


    // 这个函数相当于是从服务器取出多少数据
    private byte[] getBytes(int byteCount) throws IOException {
        byte[] result = new byte[byteCount];
        int alreadyReadSize = 0;

        while (alreadyReadSize != byteCount) {
            alreadyReadSize += serverSocket.getInputStream().read(result, alreadyReadSize, byteCount - alreadyReadSize);
        }

        return result;
    }



    // 这个函数是和发送数据相关的
    // 将指定的msg参数转为byte[]准备发送出去
    private byte[] getSendBytes(String msg) throws IOException {
        ByteArrayOutputStream outBytes = new ByteArrayOutputStream(getPacketSize(msg));
        outBytes.write(intToggle(getPacketSize(msg)));
        outBytes.write(intToggle(getPacketSize(msg)));
        outBytes.write(shortToggle(MessageType.SEND.getCode()));
        outBytes.write(0);
        outBytes.write(0);
        outBytes.write(msg.getBytes());
        outBytes.write(0);

        return outBytes.toByteArray();
    }

    private int getPacketSize(String msg) {
        return 9 + msg.length();  // 这里为什么加9？我还没明白
    }

    private byte[] intToggle(int value) {
        byte[] result = new byte[4];
        result[3] = (byte) ((value >> 24) & 0xFF);
        result[2] = (byte) ((value >> 16) & 0xFF);
        result[1] = (byte) ((value >> 8) & 0xFF);
        result[0] = (byte) (value & 0xFF);

        return result;
    }

    private byte[] shortToggle(short value) {
        byte[] result = new byte[2];
        result[1] = (byte) ((value >> 8) & 0xFF);
        result[0] = (byte) (value & 0xFF);

        return result;
    }
}
