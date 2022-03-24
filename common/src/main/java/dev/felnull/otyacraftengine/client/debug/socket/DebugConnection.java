package dev.felnull.otyacraftengine.client.debug.socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class DebugConnection {
    private static final Logger LOGGER = LogManager.getLogger(DebugConnection.class);
    private final String adress;
    private final int port;
    private final Object lock = new Object();
    private ServerSocket serverSocket;
    private Socket socket;
    private InputThread inputThread;
    private ObjectOutputStream outputStream;

    public DebugConnection(String adress, int port) {
        this.adress = adress;
        this.port = port;
    }

    public void run() throws Exception {
        LOGGER.info("Socket connection started");
        serverSocket = new ServerSocket(port);
        //serverSocket.bind(new InetSocketAddress(adress, ));
        LOGGER.info("Wait for client connection");
        socket = serverSocket.accept();
        inputThread = new InputThread();
        inputThread.start();
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        LOGGER.info("Completed to connect with client");
        synchronized (lock) {
            lock.wait();
        }
        LOGGER.info("The connection with the client has ended");
        close();
        LOGGER.info("Stopped communication with the socket");
    }

    public void sendText(String text) {
        send(new TextSocketObject(text));
    }

    public synchronized void send(Object object) {
        if (outputStream == null)
            return;

        try {
            outputStream.writeObject(object);
            outputStream.flush();
        } catch (IOException e) {
            LOGGER.error("Failed to send", e);
            DebugConnection.this.stop();
        }
    }

    public void stop() {
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    public void close() throws IOException {
        serverSocket.close();
        socket.shutdownInput();
        socket.shutdownOutput();
        socket.close();
    }

    private class InputThread extends Thread {
        @Override
        public void run() {
            ObjectInputStream stream;
            try {
                stream = new ObjectInputStream(socket.getInputStream());
            } catch (Exception e) {
                LOGGER.error("Failed to start reception", e);
                DebugConnection.this.stop();
                return;
            }
            boolean loop;
            do {
                loop = true;
                try {
                    var obj = stream.readObject();
                    if (obj instanceof TextSocketObject textSocketObject)
                        DebugSocketService.onText(textSocketObject.getDataText());
                } catch (IOException e) {
                    LOGGER.error("受信に失敗しました", e);
                    loop = false;
                } catch (ClassNotFoundException e) {
                    LOGGER.error("受信に失敗しました", e);
                }
            } while (loop);
            DebugConnection.this.stop();
        }
    }
}
