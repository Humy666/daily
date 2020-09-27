package com.humy.executor.socket;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Humy
 * @date 2020/9/23 22:37
 */
public class SocketClient {

    public static void main(String[] args) throws IOException {

        //端口号
        int port = 7000;

        String host = "localhost";

        //创建一个套接字 并将其连接到指定端口号
        Socket socket = new Socket(host, port);

        DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        Scanner scanner = new Scanner(System.in);
        boolean flag = false;

        while (!flag) {
            System.out.println("请输入正方形的边长：");
            double length = scanner.nextDouble();

            dos.writeDouble(length);
            dos.flush();

            double area = dis.readDouble();
            System.out.println("服务器端返回的面积为：" + area);

            while (true) {
                System.out.println("继续计算？(Y/N)");
                String str = scanner.next();

                if (str.equalsIgnoreCase("N")) {
                    dos.writeInt(0);
                    dos.flush();
                    flag = true;
                    break;
                } else if (str.equalsIgnoreCase("Y")) {
                    dos.writeInt(1);
                    dos.flush();
                    break;
                }
            }
        }
        socket.close();
    }

}
