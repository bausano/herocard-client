package herocard.gui.tcp;

import java.io.*;

import java.net.*;

import java.util.concurrent.*;

public class Transmitter extends Thread {
    final Socket socket;
    final BlockingQueue<String> queue;

    public Transmitter(Socket socket, BlockingQueue<String> queue) {        
        this.socket = socket;
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            String response = null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            queue.add(reader.readLine());
            //queue.add(reader.readLine());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
