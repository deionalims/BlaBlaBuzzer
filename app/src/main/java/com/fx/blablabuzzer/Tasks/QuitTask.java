package com.fx.blablabuzzer.Tasks;

import com.fx.blablabuzzer.Constants;
import com.fx.blablabuzzer.Objects.Player;
import com.octo.android.robospice.request.SpiceRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

/**
 * Created by FX on 24/01/15.
 */
public class QuitTask extends SpiceRequest<Void> {

    private List<Player> players;

    public QuitTask(List<Player> players) {
        super(Void.class);
        this.players = players;
    }

    @Override
    public Void loadDataFromNetwork() throws Exception {

        if (null != players){
            for (Player p : players){
                sendQuitMessage(p.getIpAddress().getHostAddress());
            }
        } else {
            sendQuitMessage(Constants.HOST_IP_ADDRESS);
        }

        return null;
    }

    private void sendQuitMessage(String ipAddress) throws Exception {
        Socket socket = new Socket(ipAddress, Constants.PORT);
        OutputStream outputStream;

        try {
            outputStream = socket.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            printStream.print(Constants.QUIT_GAME);
            printStream.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
