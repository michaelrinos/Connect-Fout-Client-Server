import java.io.*;
import java.net.Socket;
import java.util.Scanner;


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
    private PrintStream out;
    private Scanner in;
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
            out = new PrintStream(socket.getOutputStream());
            in = new Scanner(socket.getInputStream());
            new ReaderThread().start();
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
    }


    // Implemented methods
    @Override
    public void id(int id) throws IOException {
        out.printf("id "+ id + "%n");
    }

    @Override
    public void name(int id, String name) throws IOException {
        out.printf("name "+ id + " " + name + "%n");
    }

    @Override
    public void score(int id, int Score) throws IOException {
        out.printf("score " + id + " " + Score + "%n");
    }


    @Override
    public void move(int id, int x, int y) throws IOException {
        out.printf("move "+ id + " " + x + " " + y + "%n");
    }

    @Override
    public void turn(int id) throws IOException {
        out.printf("turn "+ id + " %n");
    }

    @Override
    public void win(int id) throws IOException {
        out.printf("win "+ id +" %n");
    }

    @Override
    public void reset() throws IOException {
        out.printf("reset %n");
    }

    @Override
    public void quit() throws IOException {
        out.printf("quit %n");
    }

    /**
     * Class ReaderThread receives messages from the network, decodes them, and
     * invokes the proper methods to process them.
     */
    private class ReaderThread extends Thread {
        public void run() {

            try {
                while (in.hasNextLine()) {
                    String message = in.nextLine();
                    Scanner s = new Scanner(message);
                    String op = s.next();

                    if (op.equals("join")){
                        String name = s.next();
                        viewListener.join(ViewProxy.this, name);
                    }
                    else if (op.equals("placed")){
                        int id = s.nextInt();
                        int x = s.nextInt();
                        int y = s.nextInt();
                        viewListener.placed(id, x, y);
                    }
                    else if (op.equals("newgame")){
                        viewListener.newgame();
                    }
                    else if (op.equals("quit")){
                        viewListener.quit();
                    }
                    else{
                        System.err.println("Bad message");
                    }
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