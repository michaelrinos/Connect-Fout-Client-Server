import java.io.*;
import java.net.Socket;
import java.util.Scanner;


/**
 * Class ModelProxy provides the network proxy for the model object in the
 * Connect four Game. The model proxy resides in the client program and
 * communicates with the server program.
 *
 * @author  Michael Rinos
 */
public class ModelProxy implements ViewListener {

// Hidden data members.

    private Socket socket;
    private PrintStream out;
    private Scanner in;
    private ModelListener modelListener;

// Exported constructors.

    /**
     * Construct a new model proxy.
     *
     * @throws IOException Thrown if an I/O error occurred.
     */
    public ModelProxy(Socket socket) throws IOException {
        this.socket = socket;
        out = new PrintStream(socket.getOutputStream());
        in = new Scanner(socket.getInputStream());
    }

// Exported operations.

    /**
     * Set the model listener object for this model proxy.
     *
     * @param modelListener Model listener.
     */
    public void setModelListener(ModelListener modelListener) {
        this.modelListener = modelListener;
        new ReaderThread().start();
    }

    /**
     * Tells server someone has joined
     */
    @Override
    public void join(ViewProxy proxy, String session) throws IOException {
        out.printf("join " + session + "%n");
    }

    /**
     * tells server that a player took these numbers from a heap
     *
     * @param x the heap
     * @param y the number of items removed
     * @throws IOException
     */

    @Override
    public void placed(int id, int x, int y) throws IOException {
        out.printf("placed " + id + " " + x + " " + y + "%n");
    }

    /**
     * Tells server to start a new game
     *
     * @throws IOException
     */
    @Override
    public void newgame() throws IOException {
        out.printf("newgame %n");
    }

    /**
     * Tells server to quit the game
     *
     * @throws IOException
     */
    @Override
    public void quit() throws IOException {
        out.printf("quit%n");
    }

// Hidden helper classes.

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

                    if (op.equals("id")){
                        int id = s.nextInt();
                        modelListener.id(id);
                    }
                    else if (op.equals("name")){
                        int id = s.nextInt();
                        String name = s.next();
                        modelListener.name(id,name);
                    }
                    else if (op.equals("score")){
                        int id = s.nextInt();
                        int score = s.nextInt();
                        modelListener.score(id, score);

                    }
                    else if (op.equals("move")){
                        int id = s.nextInt();
                        int x = s.nextInt();
                        int y = s.nextInt();
                        modelListener.move(id, x, y);
                    }
                    else if (op.equals("turn")){
                        int id = s.nextInt();
                        modelListener.turn(id);
                    }
                    else if (op.equals("win")){
                        int id = s.nextInt();
                        modelListener.win(id);
                    }
                    else if (op.equals("reset")){
                        modelListener.reset();
                    }
                    else if (op.equals("quit")){
                        modelListener.quit();
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


