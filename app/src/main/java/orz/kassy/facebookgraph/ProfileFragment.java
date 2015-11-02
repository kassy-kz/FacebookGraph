package orz.kassy.facebookgraph;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private static final String TAG = "Profile";
    private TextView mTextView1, mTextView2, mTextView3;
    private ImageView mImageView;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mTextView1 = (TextView) view.findViewById(R.id.txtProfile1);
        mTextView2 = (TextView) view.findViewById(R.id.txtProfile2);
        mTextView3 = (TextView) view.findViewById(R.id.txtProfile3);
        mImageView = (ImageView) view.findViewById(R.id.imgProfile);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        mTextView1.setText(profile.getFirstName()+" " + profile.getLastName());
        mTextView2.setText(profile.getProfilePictureUri(640, 480).toString());

        // 読み込み処理の実行
        ImageLoaderTask task = new ImageLoaderTask(getActivity(), profile.getProfilePictureUri(640, 480).toString());
        task.execute(mImageView);
    }
}
