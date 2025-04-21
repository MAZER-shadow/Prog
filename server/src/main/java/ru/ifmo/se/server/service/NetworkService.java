package ru.ifmo.se.server.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.ifmo.se.common.Serialization;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.server.controller.CommandManager;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class NetworkService {
    private static LinkedBlockingQueue<Map.Entry<InetSocketAddress, Request>> requestQueue = new LinkedBlockingQueue<>();
    private static LinkedBlockingQueue<Map.Entry<InetSocketAddress, Response>> responseQueue = new LinkedBlockingQueue<>();

    public void run(CommandManager commandManager) {
        try {
            DatagramChannel channel = DatagramChannel.open();
            channel.configureBlocking(false);
            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);
            channel.bind(new InetSocketAddress("0.0.0.0",12345));
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            ExecutorService executor = Executors.newFixedThreadPool(5);
            executor.submit(() -> receive(buffer, selector));
            executor.submit(() -> process(commandManager));
            executor.submit(() -> process(commandManager));
            executor.submit(() -> process(commandManager));
            executor.submit(() -> send(channel));
        } catch (BindException e) {
            log.error("Попытка запуска сервера на адресе, который уже занят");
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receive(ByteBuffer buffer, Selector selector) {
        while (true) {
            try {
                selector.select();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Set<SelectionKey> keys = selector.selectedKeys();
            for (SelectionKey key : keys) {
                if (key.isReadable()) {
                    DatagramChannel readyChannel = (DatagramChannel) key.channel();
                    try {
                        buffer.clear();
                        InetSocketAddress clientAddress = (InetSocketAddress) readyChannel.receive(buffer);
                        buffer.flip();
                        Request request = (Request) Serialization.deserialize(buffer);
                        log.info("Пришёл запрос c данного адреса: {} с командой: {}", clientAddress, request.getCommandName());
                        AbstractMap.SimpleEntry<InetSocketAddress, Request> entry = new AbstractMap.SimpleEntry<>(clientAddress, request);
                        requestQueue.add(entry);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }


    @SneakyThrows
    private void send(DatagramChannel channel) {
        while (true) {
            if (!responseQueue.isEmpty()) {
                Map.Entry<InetSocketAddress, Response> response = responseQueue.take();
                try  {
                    ByteBuffer buffer = ByteBuffer.wrap(ru.ifmo.se.common.Serialization.serialize(response.getValue()));
                    channel.send(buffer, response.getKey());
                    buffer.clear();
                    log.info("Отправили данные клиенту: {}", response.getKey());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void process(CommandManager commandManager) {
        log.info("Запустился поток обработки");
        while (true) {
            Map.Entry<InetSocketAddress, Request> entry = null;
            try {
                entry = requestQueue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Request request = entry.getValue();
            log.info("Поток приступил к обработке запроса от: {} с командой: {}", entry.getKey().toString(), request.getCommandName());
            Response response = commandManager.execute(request);
            AbstractMap.SimpleEntry<InetSocketAddress, Response> entryFinal = new AbstractMap.SimpleEntry<>(entry.getKey(), response);
            responseQueue.add(entryFinal);
            log.info("Поток обработал запрос от: {} с командой: {} и передал его в очередь на отправку", entry.getKey().toString(), request.getCommandName());
        }
    }
}
