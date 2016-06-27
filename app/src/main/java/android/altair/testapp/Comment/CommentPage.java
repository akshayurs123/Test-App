package android.altair.testapp.Comment;

import android.altair.testapp.Data.Raw;
import android.altair.testapp.News.Var;
import android.altair.testapp.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CommentPage extends AppCompatActivity {

    Raw rw = new Raw();
    static JSONObject obj;
    TextView tv_title, tv_author, tv_ago, tv_comment;
    Context con = this;
    static JSONArray kidsArr;
   // Button btn_cmt;
    CommentListAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_page);




        intialUI();
        setUIValues();


        rw.clearCommentData();                  /*** Clears Previous comment Data ***/
        rw.commentShot(con, kidsArr);           /*** Fetches comment Data for the current News ***/


/*
        btn_cmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.e("FIND DETECTIVE", rw.getReplydata().toString());


                adapter = new CommentListAdapter(CommentPage.this, rw.getCommentIdArray());
                listView.setAdapter(adapter);

            }
        });*/


       /* final IntentFilter theFilter = new IntentFilter();
        theFilter.addAction("alldone");
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                //rw.replyShot(con, rw.getReplydataIDs());

                adapter = new CommentListAdapter(CommentPage.this, rw.getCommentIdArray());
                listView.setAdapter(adapter);


            }
        };

        this.registerReceiver(receiver, theFilter);*/


    }



    private void intialUI() {

        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_author = (TextView) findViewById(R.id.tv_author);
        tv_ago = (TextView) findViewById(R.id.tv_ago);

        tv_comment = (TextView) findViewById(R.id.tv_comment);
        //btn_cmt = (Button) findViewById(R.id.btn_cmt);
        listView = (ListView) findViewById(R.id.lv_comment);

    }

    private void setUIValues() {

        String data = getIntent().getExtras().getString("data");
        try {
            obj = new JSONObject(data);
            kidsArr = obj.getJSONArray("kids");
            long time = obj.getInt("time");

            tv_title.setText(obj.getString("title"));
            tv_author.setText(obj.getString("by"));
            tv_ago.setText(Var.getTimeAgo(time));
            //tv_source.setText(obj.getString("url"));
            String commentCount = "Comments " + "(" + kidsArr.length() + ")";
            tv_comment.setText(commentCount);

            final ProgressDialog dialog = ProgressDialog.show(this, "", "Loading Comments and replies...",
                    true);
            dialog.show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {

                    adapter = new CommentListAdapter(CommentPage.this, rw.getCommentIdArray());
                    listView.setAdapter(adapter);
                    dialog.dismiss();
                }}, 8000);  // 3000 milliseconds



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
