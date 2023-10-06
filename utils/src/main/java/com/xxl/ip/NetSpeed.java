package com.xxl.ip;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 网速测试
 *
 * @author xxl
 * @date 2023/10/01
 */
public class NetSpeed {

    public volatile static boolean upYes;
    public volatile static boolean downYes;

    public static void main(String[] args) throws Exception {


        new Thread(() -> {
            try {
//                Socket socket = new Socket("公网ip地址", 80);
                Socket socket = new Socket("39.156.66.14", 80);
//                Socket socket = new Socket("112.9.208.202", 80);
                OutputStream output = socket.getOutputStream();
                while (true) {
                    if (upYes) {
                        output.write(-1);
                        break;
                    }
                    output.write(1);
                }
                InputStream input = socket.getInputStream();
                int downNum = 0;
                while (true) {
                    if (downYes) {
                        break;
                    }
                    input.read();
                    ++downNum;
                }
                System.out.println("下载速度:" + (downNum / 10 >> 10) + "kb/s");
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        ServerSocket server = new ServerSocket(80);
        Socket socket = server.accept();

        // 计时 十秒一次
        new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            upYes = true;
            System.out.println("上传测试已完成");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            downYes = true;
            System.out.println("下载测试已完成");
        }).start();

        //服务端处理
        InputStream input = socket.getInputStream();
        int upNum = 0;
        while (true) {
            if (upYes) {
                break;
            }
            input.read();
            ++upNum;
        }

        System.out.println("上传速度:" + (upNum / 10 >> 10) + "kb/s");

        OutputStream output = socket.getOutputStream();
        while (true) {
            if (downYes) {
                output.write(-1);
                break;
            }
            output.write(1);
        }
        server.close();
    }

}
