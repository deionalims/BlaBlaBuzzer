package com.fx.blablabuzzer.UI.Views;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fx.blablabuzzer.Objects.Player;
import com.fx.blablabuzzer.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by FX on 23/01/15.
 */
public class NewPlayerView extends LinearLayout {

    @InjectView(R.id.player_name_textView) TextView playerNameTextView;
    @InjectView(R.id.score_textView) TextView scoreTextView;

    private int score;
    private String playerName;

    public NewPlayerView(Context context, Player player) {
        super(context);

        View v = View.inflate(context, R.layout.view_new_player, this);

        ButterKnife.inject(this, v);

        score = 0;
        playerNameTextView.setText(player.getName());
        playerName = player.getName();
    }

    @OnClick(R.id.minus_score_button)
    public void minusScoreOnClick(View v){
        if (score > 0){
            score--;
        }

        scoreTextView.setText(""+score);
    }

    @OnClick(R.id.plus_score_button)
    public void plusScoreOnClick(View v){
        score++;
        scoreTextView.setText("" + score);
    }

    public void setScore(int score){
        scoreTextView.setText("" + score);
    }

    public void setColorIndicator(boolean hasWon){
        int color = hasWon ? getContext().getResources().getColor(android.R.color.holo_green_light) :
                getContext().getResources().getColor(android.R.color.holo_red_light);

        playerNameTextView.setBackgroundColor(color);
    }

    public void resetColorIndicator(){
        playerNameTextView.setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
