package com.humy.executor.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 前面的服务器端程序和客户端程序是一对一的关系，
 * 为了能让一个服务端同时为多个客户端提供服务，可以使用多线程机制，每个客户端的请求都由一个独立的线程进行处理
 *
 * @author Humy
 * @date 2020/9/23 23:04
 */
public class SocketServeWithExecutor {

    public static void main(String[] args) throws IOException {

        int port = 7000;

        int clientNo = 1;

        ServerSocket serverSocket = new ServerSocket(port);

        //创建线程池
        ExecutorService threadPool = Executors.newCachedThreadPool();

        try {
            while (true) {
                Socket socket = serverSocket.accept();
                threadPool.execute(new SingleThreadServe(socket, clientNo));
                clientNo++;
            }

        } finally {
            serverSocket.close();
        }

    }

}


class SingleThreadServe implements Runnable {

    private Socket socket;

    private int clientNo;

    public SingleThreadServe(Socket socket, int clientNo) {
        this.socket = socket;
        this.clientNo = clientNo;
    }

    @Override
    public void run() {

        try {
            DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            do {
                double length = dis.readDouble();
                System.out.println("服务端收到的边长为：" + length);
                double area = length * length;

                dos.writeDouble(area);
                dos.flush();
            } while (dis.readInt() != 0);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("与客户端 " + clientNo + " 通信结束");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}