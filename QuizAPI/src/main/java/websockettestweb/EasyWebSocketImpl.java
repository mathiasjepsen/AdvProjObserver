package websockettestweb;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;

/**
 *
 * @author pravien
 */
public class EasyWebSocketImpl implements IEasyWebsocket {

    @Override
    public String handleMessage(String msg) {
        return "message received: " + msg;
    }

    @Override
    public void pushNotification(Set<Session> sessions) {
        int count = 1;
        while (true) {
            for (Session s : sessions) {
                if (s.isOpen()) {
                    try {
                        System.out.println("sending..to " + s.getId());
                        s.getBasicRemote().sendText("message " + count);
                        count++;
                    } catch (IOException e) {}
                }
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(EasyWebSocketImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void update(Object o) {
        pushNotification((Set<Session>) o);
    }

}
