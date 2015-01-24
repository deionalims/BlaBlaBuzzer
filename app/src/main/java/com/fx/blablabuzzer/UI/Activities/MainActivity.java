package com.fx.blablabuzzer.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.fx.blablabuzzer.R;
import com.fx.blablabuzzer.UI.Activities.Master.MasterActivity;
import com.fx.blablabuzzer.UI.Activities.Slave.SlaveActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.master_button)
    public void masterButtonClicked(){
        Intent intent = new Intent(this, MasterActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.slave_button)
    public void slaveButtonClicked(){
        Intent intent = new Intent(this, SlaveActivity.class);
        startActivity(intent);
    }
}
