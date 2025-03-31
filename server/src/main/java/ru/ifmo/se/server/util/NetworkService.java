package ru.ifmo.se.server.util;

import lombok.extern.slf4j.Slf4j;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.server.command.CommandManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;

@Slf4j
public class NetworkService {
    private InetSocketAddress clientAddress;

    public void run(CommandManager commandManager) {
        try (DatagramChannel channel = DatagramChannel.open()) {
            log.info("Connecting to {}", clientAddress);
            channel.configureBlocking(false);
            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);
            channel.bind(new InetSocketAddress("0.0.0.0",12345));

            ByteBuffer buffer = ByteBuffer.allocate(4096);

            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                for (SelectionKey key : keys) {
                    if (key.isReadable()) {
                        DatagramChannel readyChannel = (DatagramChannel) key.channel();
                        Request request = receive(buffer, readyChannel);
                        log.info("Received request: " + request.getCommandName());
                        Response response = commandManager.execute(request);
                        log.info("получили респонс");
                        send(response, readyChannel);
                    }
                }

            }
        }  catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Request receive(ByteBuffer buffer, DatagramChannel channel) throws IOException, ClassNotFoundException {
        buffer.clear();
        this.clientAddress = (InetSocketAddress) channel.receive(buffer);
        log.info("Пришли данные c данного адреса: {}", this.clientAddress);
        buffer.flip();
        return (Request) Serialization.deserialize(buffer);
    }

    public void send(Response response, DatagramChannel channel) {
        try  {
            log.info("в сенде");
            ByteBuffer buffer = ByteBuffer.wrap(ru.ifmo.se.common.Serialization.serialize(response));
            channel.send(buffer, clientAddress);
            buffer.clear();
//            buffer.put(Serialization.serialize(response));
//            buffer.flip();
//
//            channel.send(buffer, clientAddress);
            log.info("Отправили данные");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
