package com.fx.blablabuzzer.UI.Activities.Slave;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.fx.blablabuzzer.BusEvents.AuthenticationConfirmedEvent;
import com.fx.blablabuzzer.BusEvents.QuitEvent;
import com.fx.blablabuzzer.Constants;
import com.fx.blablabuzzer.Managers.SlaveManager;
import com.fx.blablabuzzer.R;
import com.fx.blablabuzzer.Services.SocketService;
import com.fx.blablabuzzer.UI.Activities.BaseActivity;
import com.fx.blablabuzzer.UI.Activities.MainActivity;
import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by FX on 23/01/15.
 */
public class SlaveActivity extends BaseActivity {

    @InjectView(R.id.enter_your_name_editText) EditText enterYourNameEditText;
    @InjectView(R.id.enter_name_button) Button enterNameButton;
    @InjectView(R.id.enter_name_progressBar) ProgressBar progressBar;

    private SlaveManager slaveManager;
    private String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slave);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        ButterKnife.inject(this);

        slaveManager = new SlaveManager(spiceManager);
    }


    @OnClick(R.id.enter_name_button)
    public void enterNameButtonClick(View v){
        if (!TextUtils.isEmpty(enterYourNameEditText.getText().toString())){
            slaveManager.authenticate(enterYourNameEditText.getText().toString());
            playerName = enterYourNameEditText.getText().toString();
        }
    }

    @Subscribe
    public void onAuthenticationConfirmedReceive(AuthenticationConfirmedEvent event){
        if (event.isConfirmed()){
            Intent intent = new Intent(this, BuzzerActivity.class);
            intent.putExtra(Constants.EXTRA_PLAYER_NAME, playerName);
            startActivity(intent);
        }
    }

    @Subscribe
    public void onQuitEventReceived(QuitEvent event){
        if (event.shouldQuit()){
            SocketService.shouldContinue = false;
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            slaveManager.quitGame();
        }
    }
}
