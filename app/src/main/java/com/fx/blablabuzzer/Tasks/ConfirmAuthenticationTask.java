package com.fx.blablabuzzer.Tasks;

import com.fx.blablabuzzer.Constants;
import com.fx.blablabuzzer.Objects.Player;
import com.octo.android.robospice.request.SpiceRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by FX on 24/01/15.
 */
public class ConfirmAuthenticationTask extends SpiceRequest<Void> {

    private Player player;

    public ConfirmAuthenticationTask(Player player) {
        super(Void.class);
        this.player = player;
    }

    @Override
    public Void loadDataFromNetwork() throws Exception {

        Socket socket = new Socket(player.getIpAddress().getHostAddress(), Constants.PORT);
        OutputStream outputStream;

        try {
            outputStream = socket.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            printStream.print(Constants.AUTHENTICATION_OK);
            printStream.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();

        }

        return null;
    }
}
