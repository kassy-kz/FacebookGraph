package orz.kassy.facebookgraph;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import net.vvakame.util.jsonpullparser.JsonFormatException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GraphPostFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "GraphGet";
    private ListView mListView1;
    private ArrayList<CustomListItem> mItemList1;
    private CustomListAdapter mListAdapter;


    public GraphPostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph_get, container, false);

        Button btnGraph = (Button) view.findViewById(R.id.btnGraph);
        btnGraph.setOnClickListener(this);

        mListView1 = (ListView) view.findViewById(R.id.listView);
        mItemList1 = new ArrayList<CustomListItem>();
        mListAdapter = new CustomListAdapter(getActivity(), R.layout.view_custom_list_item, mItemList1);
        mListView1.setAdapter(mListAdapter);
        //mListView1.setOnItemClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGraph:

                Bundle params = new Bundle();
                params.putString("message", "テスト444");
                /* make the API call */
                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/me/feed",
                        params,
                        HttpMethod.POST,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                                Toast.makeText(getActivity(), "completed", Toast.LENGTH_SHORT).show();
                            }
                        }
                ).executeAsync();
                break;

        }
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
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
