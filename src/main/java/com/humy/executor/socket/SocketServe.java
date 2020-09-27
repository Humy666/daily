package com.humy.executor.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Humy
 * @date 2020/9/23 22:18
 */
public class SocketServe {

    public static void main(String[] args) throws IOException {

        // 端口号
        int port = 7000;

        // 在端口上创建一个服务器套接字
        ServerSocket serverSocket = new ServerSocket(port);

        // 监听来自客户端的连接
        Socket socket = serverSocket.accept();

        DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        do {
            double length = dis.readDouble();
            System.out.println("服务器端收到边长为：" + length);
            double area = length * length;
            dos.writeDouble(area);
            dos.flush();
        } while (dis.readInt() != 0);

        socket.close();
        serverSocket.close();

    }

}
