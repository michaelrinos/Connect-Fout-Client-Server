import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;

/**
 * Created by michael on 22/2/2016.
 */
public class ConnectFourP2P {
    private static HashMap<SocketAddress,ViewProxy> proxyMap = new HashMap<>();

    /**
     * Main program.
     *@exception `1 Exception thrown
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 3) usage();

        String host = args[0];
        int port = Integer.parseInt (args[1]);
        String session = args[2];

        boolean client;
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port));
            client = true;
        }
        catch (IOException e) {
            client = false;
        }
        System.out.println(client);
        if (client) {
            ConnectModelClone model = new ConnectModelClone();
            ConnectUI view = ConnectUI.create(session);
            ModelProxy proxy = new ModelProxy(socket);
            model.setModelListener(view);
            view.setViewListener(proxy);
            proxy.setModelListener(model);
        }
        else {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(host, port));

            ConnectModel model = new ConnectModel();
            ConnectUI view = ConnectUI.create(session);
            model.addModelListener(view, 0, session);
            view.setViewListener(model);

            socket = serverSocket.accept();
            serverSocket.close();

            ViewProxy proxy = new ViewProxy(socket);
            model.addModelListener(proxy, 1, session);
            proxy.setViewListener(model);


        }

    }

    /**
     * Print a usage message and exit.
     */
    private static void usage()
    {
        System.err.println ("Usage: java ConnectFourP2P <host> <port> <session>");
        System.exit (1);
    }

}
/*
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(host, port));

        SessionManager sessionManager = new SessionManager();

        for (;;) {
            Socket socket = serverSocket.accept();
            ViewProxy proxy = proxyMap.get(socket);
            if (proxy == null) {
                proxy = new ViewProxy(socket);
                proxy.setViewListener(sessionManager);
                proxyMap.put(socket.getRemoteSocketAddress(), proxy);
            }

        }*/

