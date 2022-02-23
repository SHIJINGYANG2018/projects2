package com.sjy.test.test;


import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class Test {

    public static void main(String[] args) throws Exception {
        send();
    }

    public static void send() throws Exception {
        Socket skt = new Socket();
        SocketAddress add = new InetSocketAddress("127.0.0.1", 5000);
        skt.connect(add);

        Object instance = new Object();
        // 序列化
        ByteArrayOutputStream aout = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(aout);
        oos.writeObject(instance);
        oos.flush();
        oos.close();

        OutputStream out = skt.getOutputStream();
        out.write(aout.toByteArray());
        aout.flush();
        out.flush();
        out.close();
        skt.close();

    }
}
