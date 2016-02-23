import java.net.InetSocketAddress;
import java.net.DatagramSocket;

/**
 * Class ConnectServer is the server main program for the Network Game. The
 * command line arguments specify the host and port to which the server should
 * listen for connections.
 * <P>
 * Usage: java ConnectServer <I>host</I> <I>port</I>
 *
 * @author  Michael Rinos

 */
public class ConnectServer
{
    /**
     * Main program.
     *@exception  Exception thrown
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 2) usage();
        String host = args[0];
        int port = Integer.parseInt (args[1]);

        DatagramSocket mailbox =
                new DatagramSocket
                        (new InetSocketAddress (host, port));

        MailboxManager manager = new MailboxManager (mailbox);

        for (;;)
        {
            manager.receiveMessage();
        }
    }

    /**
     * Print a usage message and exit.
     */
    private static void usage()
    {
        System.err.println ("Usage: java ConnectServer <host> <port>");
        System.exit (1);
    }

}