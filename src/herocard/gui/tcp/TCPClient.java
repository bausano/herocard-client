package herocard.gui.tcp;

import java.io.*;

import java.net.*;

import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPClient {
    Socket socket = null;
        
    DataOutputStream outToServer = null;
    
    BufferedReader reader = null;
    
    public TCPClient() {
        try {
            this.listen();
        } catch (Exception ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listen() throws Exception {
        try {
            BlockingQueue<String> messages = new LinkedBlockingQueue<>();

            socket = new Socket("localhost", 5551);

            socket.setKeepAlive(true);

            //socket.setSoTimeout(3000);

            outToServer = new DataOutputStream(socket.getOutputStream());

            Transmitter trans = new Transmitter(socket, messages);

            trans.start();

            outToServer.writeBytes("quit$");

            String recv = messages.take();

            System.out.print(recv);

        } finally {
           outToServer.close();
        }
    }
}