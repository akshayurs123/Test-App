package android.altair.testapp.News;

import android.altair.testapp.Data.Raw;
import android.altair.testapp.R;
import android.altair.testapp.Volley.MyCustomVolley;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, Var.VolleyInterface/*Var.VolleyJsonInterface*/ {


    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private SwipeListAdapter adapter;
    Raw rw = new Raw();
    Context con = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setUI();
        final IntentFilter theFilter = new IntentFilter();
        theFilter.addAction("newsdone");
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                adapter = new SwipeListAdapter(MainActivity.this, rw.getObservationDataArray());
                listView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);

            }
        };

        this.registerReceiver(receiver, theFilter);


    }

    private void setUI() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.listView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        fetchNewsIds();
                                    }
                                }
        );

    }

    /**
     * fetchNewsIds method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {

        fetchNewsIds();
    }


    private void fetchNewsIds() {


        MyCustomVolley volley;
        Map<String, String> HeadersParams = new HashMap<>();
        HeadersParams.put("Content-Type", "application/json");
        volley = new MyCustomVolley(this, "https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty", HeadersParams, null, 0);
        volley.makeVolleyRequests("GetNewsId");


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

    @Override
    public void processFinish(String output) throws JSONException {


        Raw rw = new Raw();
        JSONArray jArr = new JSONArray(output);
        rw.setIds(jArr);
        rw.newsShot(con);


    }
}
