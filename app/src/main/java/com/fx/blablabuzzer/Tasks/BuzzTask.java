package com.fx.blablabuzzer.Tasks;

import com.fx.blablabuzzer.Constants;
import com.octo.android.robospice.request.SpiceRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by FX on 23/01/15.
 */
public class BuzzTask extends SpiceRequest<Void> {

    private String playerName;

    public BuzzTask(String playerName) {
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
            printStream.print(Constants.BUZZ + playerName);
            printStream.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();

        }

        return null;
    }
}
