package com.siotman.batchwos.batch.component;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Component
public class SocketSessionContainer {
    private Map<String, WebSocketSession> clients = new HashMap<>();

    public void add(WebSocketSession session) {
        clients.put(session.getId(), session);
    }

    public void remove(WebSocketSession session) {
        clients.remove(session.getId());
    }

    public void broadcast(String msg) throws IOException {
        Iterator<String> iter = clients.keySet().iterator();

        while(iter.hasNext()) {
            String cid = iter.next();

            WebSocketSession client = clients.get(cid);
            if(!client.isOpen()) {
                clients.remove(cid);
                continue;
            }
            client.sendMessage(new TextMessage(msg));
        }
    }
}
