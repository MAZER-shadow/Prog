package ru.ifmo.se.server.util;

import lombok.extern.slf4j.Slf4j;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.server.command.CommandManager;

import java.io.IOException;
import java.net.BindException;
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
                        log.info("Получили запрос с командой: " + request.getCommandName());
                        Response response = commandManager.execute(request);
                        send(response, readyChannel);
                    }
                }
            }
        } catch (BindException e) {
            log.error("Попытка запуска сервера на адресе, который уже занят");
            System.exit(1);
        } catch (IOException | ClassNotFoundException e) {
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
            ByteBuffer buffer = ByteBuffer.wrap(ru.ifmo.se.common.Serialization.serialize(response));
            channel.send(buffer, clientAddress);
            buffer.clear();
            log.info("Отправили данные клиенту");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
