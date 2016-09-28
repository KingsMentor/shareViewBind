package xyz.belvi.sharedview;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import java.util.Calendar;

import xyz.belvi.sharedview.Sharedpref.SharedBind;
import xyz.belvi.sharedview.Sharedpref.SharedField;
import xyz.belvi.sharedview.Sharedpref.SharedMethod;

public class MainActivity extends AppCompatActivity {


    @SharedField(key = "sample", defaultValue = "0l", classType = Bitmap.class)
    public CheckBox helloPref;

    @SharedField(key = "sample")
    public String data = "";

    SharedBind sharedBind = new SharedBind();

    @SharedMethod(key = "sample")
    public void methodTest() {
        Log.e("test", "" + data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helloPref = (CheckBox) findViewById(R.id.fieldTxt);
        sharedBind.shareView(this);
        sharedBind.bind(getSharedPreferences("default", Context.MODE_PRIVATE));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSharedPreferences("default", Context.MODE_PRIVATE).edit().putString("sample", "yea" + Calendar.getInstance().getTimeInMillis()).commit();
//                sharedBind.putString(getSharedPreferences("default", Context.MODE_PRIVATE), "sample", "yea" + Calendar.getInstance().getTimeInMillis());
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
        // automatically handle clicks on the Home/Up button, so long.
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
