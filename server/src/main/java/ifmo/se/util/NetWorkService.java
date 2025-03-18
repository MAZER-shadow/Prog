package ifmo.se.util;

import ifmo.se.request.AbstractRequest;
import ifmo.se.response.AbstractResponse;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class NetWorkService {
    private int port = 12345;
    private DatagramSocket socket;
    private DatagramPacket receivePacket;

    public AbstractRequest getRequest() {
        try (DatagramSocket socket = new DatagramSocket(port)) {
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            socket.receive(receivePacket);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(receivePacket.getData());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            this.socket = socket;
            this.receivePacket = receivePacket;
            System.out.println(objectInputStream.readObject());
            return (AbstractRequest) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void sendResponse(AbstractResponse response) {
        try {
            socket.receive(receivePacket);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(response);
            objectOutputStream.flush();
            byte[] sendData = byteArrayOutputStream.toByteArray();
            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
            socket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
