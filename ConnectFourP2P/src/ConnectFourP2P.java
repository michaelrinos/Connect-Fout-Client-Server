import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by michael on 22/2/2016.
 */
public class ConnectFourP2P {

    private static ConnectModel model;
    private static ConnectUI view;

    /**
     * Main program.
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 3) usage();

        String host = args[0];
        int port = Integer.parseInt (args[1]);
        String session = args[2];

        boolean client;
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port));      //Connect to server
            client = true;
        }
        catch (IOException e) {
            client = false;
        }
        if (client) {

            ModelProxy proxy = new ModelProxy(socket);
            ConnectUI view = ConnectUI.create(session);

            proxy.setModelListener(view);
            view.setViewListener(proxy);

            proxy.join(null, session);
            view.reset();

        }
        else {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(host, port));

            model = new ConnectModel();
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
    /*ServerSocket serverSocket = new ServerSocket();
serverSocket.bind(new InetSocketAddress(host, port));

        model = new ConnectModel();
        ConnectUI view = ConnectUI.create(session);
        model.addModelListener(view, 0, session);
        view.setViewListener(model);

        socket = serverSocket.accept();
        serverSocket.close();

        ViewProxy proxy = new ViewProxy(socket);
        model.addModelListener(proxy, 1, session);
        proxy.setViewListener(model);*/