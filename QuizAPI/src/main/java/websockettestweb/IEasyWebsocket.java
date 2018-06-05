package websockettestweb;

import java.util.Set;
import javax.websocket.Session;

/**
 *
 * @author mathiasjepsen
 */
public interface IEasyWebsocket extends Observer {
    
    String handleMessage(String msg);
    void pushNotification(Set<Session> sessions);
    
}
