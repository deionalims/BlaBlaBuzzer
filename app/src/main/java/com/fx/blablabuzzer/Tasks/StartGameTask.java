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
public class StartGameTask extends SpiceRequest<Void> {

    private List<Player> players;

    public StartGameTask(List<Player> players) {
        super(Void.class);
        this.players = players;
    }

    @Override
    public Void loadDataFromNetwork() throws Exception {

        for (Player p : players){
            Socket socket = new Socket(p.getIpAddress().getHostAddress(), Constants.PORT);
            OutputStream outputStream;

            try {
                outputStream = socket.getOutputStream();
                PrintStream printStream = new PrintStream(outputStream);
                printStream.print(Constants.START_GAME);
                printStream.close();
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();

            }
        }


        return null;
    }
}
