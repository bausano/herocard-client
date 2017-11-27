[1mdiff --git a/src/herocard/client/Client.java b/src/herocard/client/Client.java[m
[1mindex 64cc82a..ae6fcec 100644[m
[1m--- a/src/herocard/client/Client.java[m
[1m+++ b/src/herocard/client/Client.java[m
[36m@@ -50,6 +50,7 @@[m [mpublic class Client {[m
      */[m
     public void onConnectionLost() {[m
         if (! conn.t.isAlive()) {[m
[32m+[m[32m            System.out.println("reset");[m
             Client.conn = new Connection(host, port);[m
         }[m
     }[m
[1mdiff --git a/src/herocard/client/Connection.java b/src/herocard/client/Connection.java[m
[1mindex 71087f0..d420852 100644[m
[1m--- a/src/herocard/client/Connection.java[m
[1m+++ b/src/herocard/client/Connection.java[m
[36m@@ -5,8 +5,7 @@[m [mimport java.io.IOException;[m
 import java.io.InputStream;[m
 import java.io.OutputStream;[m
 import java.net.Socket;[m
[31m-import java.util.Comparator;[m
[31m-import java.util.PriorityQueue;[m
[32m+[m[32mimport java.util.concurrent.PriorityBlockingQueue;[m
 import java.util.concurrent.TimeUnit;[m
 [m
 /**[m
[36m@@ -15,6 +14,21 @@[m [mimport java.util.concurrent.TimeUnit;[m
  * @author michael[m
  */[m
 public final class Connection implements Runnable {[m
[32m+[m[32m    /**[m
[32m+[m[32m     * Socket communication connecting.[m
[32m+[m[32m     */[m
[32m+[m[32m    public Thread t;[m
[32m+[m[41m    [m
[32m+[m[32m    /**[m
[32m+[m[32m     * Queue of requests to send.[m
[32m+[m[32m     */[m
[32m+[m[32m    public PriorityBlockingQueue<Request> queue = new PriorityBlockingQueue();[m
[32m+[m[41m    [m
[32m+[m[32m    /**[m
[32m+[m[32m     * Is true if last request was successful, otherwise false.[m
[32m+[m[32m     */[m
[32m+[m[32m    public Boolean established = false;[m
[32m+[m[41m    [m
     /**[m
      * TCP socket.[m
      */[m
[36m@@ -31,14 +45,9 @@[m [mpublic final class Connection implements Runnable {[m
     private final Integer port;[m
     [m
     /**[m
[31m-     * Socket communication connecting.[m
[32m+[m[32m     * Flag breaking the cycle.[m
      */[m
[31m-    public Thread t;[m
[31m-    [m
[31m-    /**[m
[31m-     * Queue of requests to send.[m
[31m-     */[m
[31m-    public PriorityQueue<Request> queue;[m
[32m+[m[32m    private Boolean running = true;[m
     [m
     /**[m
      * [m
[36m@@ -49,14 +58,12 @@[m [mpublic final class Connection implements Runnable {[m
         this.host = host;[m
         [m
         this.port = port;[m
[31m-        [m
[31m-        this.queue = new PriorityQueue<>(Comparator.comparing(Request::getPriority));[m
 [m
         start();[m
     }[m
     [m
     /**[m
[31m-     * Starts a new thread for attempting the connection.[m
[32m+[m[32m     * Starts a new thread for connection.[m
      */[m
     public void start() {[m
         t = new Thread(this);[m
[36m@@ -65,35 +72,42 @@[m [mpublic final class Connection implements Runnable {[m
     }[m
 [m
     /**[m
[31m-     * Attempts to connect to server every seconds until program is stopped or[m
[31m-     * connection established.[m
[32m+[m[32m     * Connects to the server and handles communication.[m
      */[m
     @Override[m
     public void run() {[m
[31m-        // TODO: Flag[m
[31m-        while (true) {[m
[31m-            try {[m
[31m-                if (! isConnected()) {[m
[31m-                    connect();[m
[32m+[m[32m        try {[m
[32m+[m[32m            if (! isConnected()) {[m
[32m+[m[32m                TimeUnit.SECONDS.sleep(1);[m
 [m
[31m-                    TimeUnit.SECONDS.sleep(1);[m
[31m-                }[m
[32m+[m[32m                connect();[m
 [m
[31m-                Request req = queue.poll();[m
[31m-[m
[31m-                if (req == null) {[m
[31m-                    continue;[m
[31m-                }[m
[32m+[m[32m                Emitor.dispatch("connected");[m
[32m+[m[32m            }[m
 [m
[31m-                req.execute();[m
[32m+[m[32m            Request req = queue.take();[m
 [m
[31m-                //Emitor.dispatch("connected");[m
[31m-            } catch(IOException | InterruptedException ex) {[m
[31m-                Emitor.dispatch("disconnected");[m
[32m+[m[32m            if (req == null) {[m
[32m+[m[32m                return;[m
             }[m
[32m+[m
[32m+[m[32m            req.execute();[m
[32m+[m[32m        } catch(IOException | InterruptedException ex) {[m
[32m+[m[32m            established = false;[m
[32m+[m
[32m+[m[32m            Emitor.dispatch("disconnected");[m
[32m+[m[32m        }[m
[32m+[m[41m        [m
[32m+[m[32m        if (running) {[m
[32m+[m[32m            run();[m
         }[m
     }[m
     [m
[32m+[m[32m    /**[m
[32m+[m[32m     * Adds a new request to the executing queue.[m
[32m+[m[32m     *[m[41m [m
[32m+[m[32m     * @param req[m[41m [m
[32m+[m[32m     */[m
     public void spawn(Request req) {[m
         queue.add(req);[m
     }[m
[36m@@ -107,6 +121,8 @@[m [mpublic final class Connection implements Runnable {[m
         socket = new Socket(host, port);[m
 [m
         socket.setKeepAlive(true);[m
[32m+[m[41m        [m
[32m+[m[32m        established = true;[m
     }[m
     [m
     /**[m
[36m@@ -136,6 +152,21 @@[m [mpublic final class Connection implements Runnable {[m
             return false;[m
         }[m
         [m
[31m-        return socket.isConnected();[m
[32m+[m[32m        if (! socket.isConnected()) {[m
[32m+[m[32m            return false;[m
[32m+[m[32m        }[m
[32m+[m[41m        [m
[32m+[m[32m        return established;[m
[32m+[m[32m    }[m
[32m+[m[41m    [m
[32m+[m[32m    /**[m
[32m+[m[32m     * Breaks the connection.[m
[32m+[m[32m     */[m
[32m+[m[32m    public void disconnect() {[m
[32m+[m[32m        running = false;[m
[32m+[m[41m        [m
[32m+[m[32m        try {[m
[32m+[m[32m            socket.close();[m
[32m+[m[32m        } catch (IOException ex) { }[m
     }[m
 }[m
[1mdiff --git a/src/herocard/client/Request.java b/src/herocard/client/Request.java[m
[1mindex 7687830..804b356 100644[m
[1m--- a/src/herocard/client/Request.java[m
[1m+++ b/src/herocard/client/Request.java[m
[36m@@ -1,6 +1,5 @@[m
 package herocard.client;[m
 [m
[31m-import herocard.events.Emitor;[m
 import java.io.BufferedReader;[m
 import java.io.DataOutputStream;[m
 import java.io.IOException;[m
[36m@@ -11,7 +10,7 @@[m [mimport java.io.InputStreamReader;[m
  *[m
  * @author michael[m
  */[m
[31m-public class Request {[m
[32m+[m[32mpublic class Request implements Comparable<Request> {[m
     /**[m
      * Body of a request.[m
      */[m
[36m@@ -52,7 +51,9 @@[m [mpublic class Request {[m
         send();[m
 [m
         String response = read();[m
[31m-[m
[32m+[m[41m        [m
[32m+[m[32m        System.out.println(response);[m
[32m+[m[41m        [m
         resolve.call(response);[m
     }[m
     [m
[36m@@ -96,4 +97,14 @@[m [mpublic class Request {[m
     public Integer getPriority() {[m
         return priority;[m
     }[m
[32m+[m
[32m+[m[32m    /**[m
[32m+[m[32m     * Comparing for PriorityBlockingQueue.[m
[32m+[m[32m     * @param t Request[m
[32m+[m[32m     * @return[m
[32m+[m[32m     */[m
[32m+[m[32m    @Override[m
[32m+[m[32m    public int compareTo(Request t) {[m
[32m+[m[32m        return t.priority.compareTo(priority);[m
[32m+[m[32m    }[m
 }[m
[1mdiff --git a/src/herocard/gui/listeners/NewGameListener.java b/src/herocard/gui/listeners/NewGameListener.java[m
[1mindex 7692357..8220968 100644[m
[1m--- a/src/herocard/gui/listeners/NewGameListener.java[m
[1m+++ b/src/herocard/gui/listeners/NewGameListener.java[m
[36m@@ -14,7 +14,9 @@[m [mpublic class NewGameListener implements ActionListener {[m
             Menu.getInstance().heading.setText(response);[m
         };[m
 [m
[31m-        Client.message("message1").send(cb);[m
[32m+[m[32m        Client.message("message1").priority(8).send(cb);[m
[32m+[m[41m        [m
[32m+[m[32m        Client.message("message2").send(cb);[m
         //Menu.getInstance().dispose();[m
         [m
         // TODO: Open window "Wait for another player".[m
