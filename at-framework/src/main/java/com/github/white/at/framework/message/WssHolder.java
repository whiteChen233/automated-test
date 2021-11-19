package com.github.white.at.framework.message;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WssHolder {

    private final Set<WebSocketServer> set = new CopyOnWriteArraySet<>();

    private int online;

    public void add(WebSocketServer wss) {
        set.add(wss);
        online++;
        sendMessage(wss, wss.getSid() + " has joined");
        log.info("Connection: {} joined, the current online number is: {}", wss.getSid(), getOnline());
    }

    public void sub(WebSocketServer wss) {
        set.remove(wss);
        online--;
        sendMessage(wss,wss.getSid() + " has left");
        log.info("Connection: {} closed, the current number of people online:{}", wss.getSid(), getOnline());
    }

    public int getOnline() {
        return online;
    }

    public void sendMessage(WebSocketServer curr, String s) {
        log.info("Receive a message from the window {}: {}", curr.getSid(), s);
        set.stream()
            .filter(wss -> !curr.getSid().equals(wss.getSid()))
            .forEach(wss -> {
            try {
                wss.getSession().getBasicRemote().sendText(s);
            } catch (IOException e) {
                log.error("Failed to send message", e);
            }
        });
    }

    public static WssHolder get() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final WssHolder INSTANCE = new WssHolder();
        private Holder() {}
    }
}
