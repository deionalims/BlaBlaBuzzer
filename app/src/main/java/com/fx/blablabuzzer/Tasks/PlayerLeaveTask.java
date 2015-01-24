package com.fx.blablabuzzer.Tasks;

import com.fx.blablabuzzer.Constants;
import com.octo.android.robospice.request.SpiceRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by FX on 24/01/15.
 */
public class PlayerLeaveTask extends SpiceRequest<Void> {

    private String playerName;

    public PlayerLeaveTask(String playerName) {
        super(Void.class);
        this.playerName = playerName;
    }

    @Override
    public Void loadDataFromNetwork() throws Exception {

        Socket socket = new Socket(Constants.HOST_IP_ADDRESS, Constants.PORT);
        OutputStream outputStream;

        try {
            outputStream = socket.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            printStream.print(Constants.LEAVE + playerName);
            printStream.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();

        }

        return null;
    }
}
