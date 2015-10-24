package orz.kassy.facebookgraph;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import net.vvakame.util.jsonpullparser.JsonFormatException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = "MainActivity";
    LoginButton loginButton;
    CallbackManager callbackManager;
    private ListView mListView1;
    private ArrayList<CustomListItem> mItemList1;
    private CustomListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_main);

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends,user_posts");

//        LoginManager.getInstance().logInWithPublishPermissions(
//                this,
//                Arrays.asList("publish_actions,publish_actions,read_stream"));
//        LoginManager.getInstance().logInWithPublishPermissions(
//                this,
//                Arrays.asList("read_stream"));

        // If using in a fragment
        //loginButton.setFragment(this);
        // Other app specific specialization

        callbackManager = CallbackManager.Factory.create();

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });


        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                }
        );

        Button btnGraph = (Button) findViewById(R.id.btnGraph);
        btnGraph.setOnClickListener(this);

        mListView1 = (ListView) findViewById(R.id.listView);
        mItemList1 = new ArrayList<CustomListItem>();
        mListAdapter = new CustomListAdapter(this, R.layout.view_custom_list_item, mItemList1);
        mListView1.setAdapter(mListAdapter);
        mListView1.setOnItemClickListener(this);

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.btnGraph:
//                final AccessToken accessToken = AccessToken.getCurrentAccessToken();
//                if (accessToken == null) {
//                    Log.e(TAG, "access token = null");
//                }
//                Log.i(TAG, "access token = " + accessToken);
//
//                GraphRequest request = GraphRequest.newMeRequest(
//                        accessToken,
//                        new GraphRequest.GraphJSONObjectCallback() {
//                            @Override
//                            public void onCompleted(
//                                    JSONObject object,
//                                    GraphResponse response) {
//                                // Application code
//                                Log.i(TAG, "onComplete");
//                                // {"id":"914329871988909","name":"Kazutoshi Kashimoto","link":"https:\/\/www.facebook.com\/app_scoped_user_id\/914329871988909\/"}
//                                // {"id":"914329871988909","name":"Kazutoshi Kashimoto","link":"https:\/\/www.facebook.com\/app_scoped_user_id\/914329871988909\/"}, error: null}
//                            }
//                        });
//                Bundle parameters = new Bundle();
//                parameters.putString("fields", "id,name,link,birthday,gender");
//                request.setParameters(parameters);
//                request.executeAsync();
//                break;
//
            case R.id.btnGraph:
                /* make the API call */
                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        //"/me/friends",
                        "/me/feed",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                                /* handle the result */
                                Log.i(TAG, "onComplete2");
                                Log.i(TAG, response.getJSONObject().toString());
                                Hoge hoge = null;
                                try {
                                    hoge = HogeGen.get(response.getJSONObject().toString());
                                    Log.i(TAG, hoge.data.get(0).getMessage());
                                    for(int i=0; i<hoge.data.size(); i++) {
                                        FeedJson feed = hoge.data.get(i);
                                        mItemList1.add(new CustomListItem(feed.getId(), feed.getCreatedTime(), feed.getMessage(), feed.getStory()));
                                        mListAdapter.notifyDataSetChanged();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JsonFormatException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                ).executeAsync();
                break;

//            case R.id.btnGraph3:
//                GraphRequestBatch batch = new GraphRequestBatch(
////                        GraphRequest.newMeRequest(
////                                AccessToken.getCurrentAccessToken(),
////                                new GraphRequest.GraphJSONObjectCallback() {
////                                    @Override
////                                    public void onCompleted(
////                                            JSONObject jsonObject,
////                                            GraphResponse response) {
////                                        // Application code for user
////                                        Log.i(TAG,"onComplete31");
////                                        // {Response:  responseCode: 200, graphObject: {"name":"Kazutoshi Kashimoto","id":"914329871988909"}, error: null}
////                                    }
////                                }),
//                        GraphRequest.newMyFriendsRequest(
//                                AccessToken.getCurrentAccessToken(),
//                                new GraphRequest.GraphJSONArrayCallback() {
//                                    @Override
//                                    public void onCompleted(
//                                            JSONArray jsonArray,
//                                            GraphResponse response) {
//                                        // Application code for users friends
//                                        Log.i(TAG, "onComplete3");
//                                        //{Response:  responseCode: 200, graphObject: {"data":[],"summary":{"total_count":322}}, error: null}
//                                    }
//                                })
//                );
//                batch.addCallback(new GraphRequestBatch.Callback() {
//                    @Override
//                    public void onBatchCompleted(GraphRequestBatch graphRequests) {
//                        // Application code for when the batch finishes
//                    }
//                });
//                batch.executeAsync();
//                break;
//
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //TODO 処理
    }

    /**
     * ListView1のアダプター
     * @author kashimoto
     *
     */
    private class CustomListAdapter extends BaseAdapter {

        List<CustomListItem> mDataList;
        int mLayoutResId;
        Context mContext;

        /**
         * コンストラクタ
         * @param context
         * @param layoutResId
         * @param dataList
         */
        public CustomListAdapter(Context context, int layoutResId, List<CustomListItem> dataList) {
            super();
            mDataList = dataList;
            mLayoutResId = layoutResId;
            mContext = context;
        }

        public int getCount() {
            return mDataList.size();
        }

        public Object getItem(int position) {
            return mDataList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(mLayoutResId, null);
            }
            CustomListItem item = (CustomListItem) getItem(position);
            if(item == null) {
                return null;
            }
            ((TextView)convertView.findViewById(R.id.textViewMessage)).setText(item.getMessage());
            ((TextView)convertView.findViewById(R.id.textViewStory)).setText(item.getStory());
            ((TextView)convertView.findViewById(R.id.textViewCreatedTime)).setText(item.getCreatedTime());
            return convertView;
        }
    }

}
