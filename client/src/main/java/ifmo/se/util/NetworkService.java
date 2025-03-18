package ifmo.se.util;

import ifmo.se.request.AbstractRequest;
import ifmo.se.response.AbstractResponse;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class NetworkService {
    private int port;
    private String ip;

    public NetworkService(int port, String ip) {
        this.port = port;
        this.ip = ip;
    }

    public AbstractResponse send(AbstractRequest request) {
        try (DatagramSocket socket = new DatagramSocket()) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(request);
            oos.flush();
            byte[] sendData = baos.toByteArray();

            InetAddress address = InetAddress.getByName(ip);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
            socket.send(sendPacket);

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(receivePacket.getData());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (AbstractResponse) objectInputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
