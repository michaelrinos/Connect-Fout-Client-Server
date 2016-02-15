import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


/**
 * Class ViewProxy provides the network proxy for the view object in the Network.
 * The view proxy resides in the server program and communicates with
 * the client program.
 *
 * @author  Michael Rinos
 */
public class ViewProxy implements ModelListener {

// Hidden data members.
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private ViewListener viewListener;

// Exported constructors.

    /**
     * Construct a new view proxy.
     *
     * @param  socket
     */
    public ViewProxy(Socket socket) {
        this.socket = socket;
        try {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// Exported operations.

    /**
     * Set the view listener object for this view proxy.
     *
     * @param  viewListener  View listener.
     */
    public void setViewListener(ViewListener viewListener) {
        this.viewListener = viewListener;
        new ReaderThread().start();
    }


    // Implemented methods
    @Override
    public void id(int id) throws IOException {
        out.writeByte('I');
        out.writeByte(id);
    }

    @Override
    public void name(int id, String name) throws IOException {
        out.writeByte('N');
        out.writeByte(id);
        out.writeUTF(name);
    }

    @Override
    public void score(int id, int Score) throws IOException {
        out.writeByte('S');
        out.writeByte(id);
        out.writeByte(Score);
    }

    @Override
    public void reset() throws IOException {
        out.writeByte('H');
    }

    @Override
    public void move(int id, int x, int y) throws IOException {
        out.writeByte('M');
        out.writeByte(id);
        out.writeByte(x);
        out.writeByte(y);
    }

    @Override
    public void turn(int id) throws IOException {
        out.writeByte('T');
        out.writeByte(id);
    }

    @Override
    public void win(int id) throws IOException {
        out.writeByte('W');
        out.writeByte(id);
    }

    @Override
    public void quit() throws IOException {
        out.writeByte('Q');
    }

    /**
     * Class ReaderThread receives messages from the network, decodes them, and
     * invokes the proper methods to process them.
     */
    private class ReaderThread extends Thread {
        public void run() {
            try {
                for (;;) {
                    String session;
                    
                    byte t2 = in.readByte();
                    if (t2 == 'J'){
                        session = in.readUTF();
                        viewListener.join(ViewProxy.this, session);
                    }
                    if (t2 == 'P'){
                        int id = in.readByte();
                        int r = in.readByte();
                        int c = in.readByte();
                        System.out.println("ID: "+ id);
                        System.out.println("X: "+ r);
                        System.out.println("Y: "+ c);
                    }
                    System.out.println(t2);


                    /*byte b = in.readByte();
                    System.out.println(b);

                    switch (b)
                    {
                        case 'J':
                            session = in.readUTF();
                            System.out.println("Received J");
                            viewListener.join (ViewProxy.this, session);
                            break;
                        case 'P':
                            System.out.println("Received P");
                            int temp = in.readByte();
                            System.out.println(temp);
                            int id = in.readInt();
                            System.out.println(id);
                            int r = in.readInt();
                            System.out.println(r);
                            int c = in.readInt();
                            System.out.println(c);
                            viewListener.placed (id, r, c);
                            break;
                        case 'N':
                            System.out.println("Received N");
                            viewListener.newgame();
                            break;
                        case 'Q':
                            System.out.println("Received Q");
                            viewListener.quit();
                            break;
                        default:
                            System.err.println ("Bad message");
                            break;
                    }*/
                }
            } catch (IOException exc) {
            } finally {
                try {
                    socket.close();
                } catch (IOException exc) {
                }
            }
        }
    }
}