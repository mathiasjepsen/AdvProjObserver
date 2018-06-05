/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websockettestweb;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;

/**
 *
 * @author thomasthimothee
 */
public class EasyWebSocketImpl implements IEasyWebsocket {
    int countdown = 30;
    
    @Override
    public String handleMessage(String msg) {
        return "message received: " + msg;
    }

    @Override
    public void pushNotification(Set<Session> sessions) {
            for (Session s : sessions) {   
                if (s.isOpen()) {
                    try {
                        if (countdown > 0){
                            s.getBasicRemote().sendText("Bomb explodes in "+ countdown + " to session " + s.getId()+1);

                        }
                        else s.getBasicRemote().sendText("This session " + s.getId()+1 + " is soooo dead ");
                    } catch (IOException e) {}
                }
                
            }
            countdown --;
            try {
                Thread.sleep(1000);
                this.pushNotification(sessions);
            } catch (InterruptedException ex) {
                Logger.getLogger(EasyWebSocketImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        

    }


}