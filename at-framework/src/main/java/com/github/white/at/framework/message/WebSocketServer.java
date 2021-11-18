package com.github.white.at.framework.message;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Service
@ServerEndpoint("/api/websocket/{sid}")
public class WebSocketServer {

    private Session session;

    private String sid;

    @OnOpen
    public void open(Session session, @PathParam("sid") String sid) {
        this.session = session;
        this.sid = sid;
        WssHolder.get().add(this);
    }

    @OnClose
    public void close() {
        WssHolder.get().sub(this);
    }

    @OnMessage
    public void message(String message, Session session) {
        log.info("Receive a message from the window {}: {}", sid, message);
        WssHolder.get().sendMessage(this, message);
    }

    @OnError
    public void error(Session session, Throwable t) {
        log.error("An error occurred", t);
    }
}
