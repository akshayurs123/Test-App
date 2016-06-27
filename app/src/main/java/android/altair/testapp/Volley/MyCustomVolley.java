package android.altair.testapp.Volley;

import android.altair.testapp.Data.Raw;
import android.altair.testapp.News.Var;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AkshayV on 02-May-16.
 */


public class MyCustomVolley {

    String TAG = "MyCustomVolley";
    private Context context;
    private String ApiLink;
    private Map<String, String> params = new HashMap<>();
    private Map<String, String> headerparam = new HashMap<>();
    private Var.VolleyInterface VolleyInterface;
    private int POST_TYPE;

    private static int reqCnt = 0;

    Raw rw = new Raw();
    int GET = 0;
    int POST = 1;
    int PUT = 2;

    /**
     * @param con:        Calling activity Context
     * @param link:       Api link to send the data.
     * @param parameters: key - value structure.
     */
    public MyCustomVolley(Context con, String link, Map hparam, Map parameters, int i) {

        this.context = con;
        this.ApiLink = link;
        this.params = parameters;
        this.headerparam = hparam;
        this.VolleyInterface = (Var.VolleyInterface) con;
        this.POST_TYPE = i;

    }

    public void makeVolleyRequests(final int OpType) {

/*
        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();*/
        StringRequest putRequest = new StringRequest(POST_TYPE/*Request.Method.PUT*/, ApiLink,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (OpType == Var.NEWSID_REQUEST) {
                            try {
                                VolleyInterface.processFinish(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if (OpType == Var.NEWSITEM_REQUEST) {
                            rw.setObservationData(response);
                            reqCnt++;
                            if(reqCnt >=Var.NUMBER_OF_NEWS_LOADS){

                                Intent i = new Intent("newsdone");
                                context.sendBroadcast(i);
                            }
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // pDialog.hide();
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                /*Map<String, String> params = new HashMap<String, String>();
                params.put("op", "list");
                params.put("restid", "indblr2016getseatedr9999");*/
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {


                return headerparam;
            }
        };
        AppController2.getInstance().addToRequestQueue(putRequest);
    }


}
