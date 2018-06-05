package websockettestweb;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author mathiasjepsen
 */
@ServerEndpoint("/api")
public class AnnotatedEndpoint implements Observable {

    private IEasyWebsocket userImplementation;
    private static final Set<Session> SESSIONS = Collections.synchronizedSet(new HashSet<Session>());
    private static final List<Observer> OBSERVERS = new ArrayList();

    public AnnotatedEndpoint() throws InstantiationException, IllegalAccessException {
        try {

            URL url = getClass().getResource(".");
            File dir = new File(url.toURI());
            
            MyClassFinder cf = new MyClassFinder();            
            userImplementation = cf.findClass(dir);
            addObserver(userImplementation);

            Thread t1 = new Thread(() -> {
                userImplementation.pushNotification(SESSIONS);
            });
            t1.start();

        } catch (URISyntaxException | SecurityException | IllegalArgumentException ex) {
            Logger.getLogger(MyClassFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        SESSIONS.add(session);
        System.out.println("added session");

    }

    @OnMessage
    public void onMessage(Session session, String msg) throws IOException {
        System.out.println("amount of sessions " + SESSIONS.size());
        System.out.println("Recived message" + msg);
        session.getBasicRemote().sendText(userImplementation.handleMessage(msg));
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Session: " + session.getId() + " has disconected");
        SESSIONS.remove(session);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : OBSERVERS) {
            o.update(SESSIONS);
        } 
    }

    @Override
    public void addObserver(Observer o) {
        OBSERVERS.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        OBSERVERS.remove(o);
    }

}
