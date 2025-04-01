package ru.ifmo.se.client.service;

import ru.ifmo.se.common.Serialization;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.exception.IORuntimeException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

public class NetworkService {
    private final int MAX_ATTEMPTS = 5;
    private String ip;
    private int port;
    private DatagramChannel channel;
    private Selector selector;
    private SocketAddress socketAddress;
    public NetworkService(String ip, int port, DatagramChannel channel, Selector selector) {
        this.ip = ip;
        this.port = port;
        this.channel = channel;
        this.selector = selector;
        socketAddress = new InetSocketAddress(ip, port);
    }

    public Response send(Request request) {
        try {
            int attempt = 1;
            while (attempt < MAX_ATTEMPTS) {
                ByteBuffer buffer = ByteBuffer.wrap(Serialization.serialize(request));
                channel.send(buffer, socketAddress);
                buffer.clear();
                buffer = ByteBuffer.allocate(4096);

                Response response = receive(selector, buffer);
                if (response == null) {
                    System.out.println("Ошибка отправки:( " + ++attempt + " попытка отправки");
                    continue;
                }
                return response;
            }
            System.out.println("сервер не доступен");
            System.exit(1);
            return null;
        } catch (Throwable e) {
            throw new IORuntimeException(e.getMessage());
        }
    }

    private Response receive(Selector selector, ByteBuffer buffer) {
        try {
            while (true) {
                int res = selector.select(5000);
                if (res == 0) {
                    return null;
                }
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iter = keys.iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    if (key.isReadable()) {
                        DatagramChannel dc = (DatagramChannel) key.channel();
                        buffer.clear();
                        dc.receive(buffer);
                        buffer.flip();
                        Response response = (Response) Serialization.deserialize(buffer);
                        iter.remove();
                        return response;
                    }
                }
                iter.remove();
                keys.clear();
            }
        } catch (IOException e) {
            throw new IORuntimeException(e.getMessage());
        }
    }
}
