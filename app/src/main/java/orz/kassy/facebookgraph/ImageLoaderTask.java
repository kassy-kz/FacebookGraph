package orz.kassy.facebookgraph;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ImageLoaderTask extends AsyncTask<ImageView, Void, Bitmap> {

    private static final String USER_AGENT = "WeatherForecasts Sample";

    private final Context mContext;

    ImageView mImageView;
    String mUrl;

    public ImageLoaderTask(Context context, String url) {
        mContext = context;
        mUrl = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(ImageView... params) {
        mImageView = params[0];
        try {
            return getImage(mContext, mUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        if (bitmap != null) {
            mImageView.setImageBitmap(bitmap);
        }
    }

    private static Bitmap getImage(Context context, String uri) throws IOException {

        InputStream is;
        URL u = new URL(uri);
        HttpURLConnection con = (HttpURLConnection) u.openConnection();
        con.setRequestMethod("GET");
        con.connect();
        is = new BufferedInputStream(con.getInputStream());
        return BitmapFactory.decodeStream(is);
    }

}
