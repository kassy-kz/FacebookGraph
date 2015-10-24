package orz.kassy.facebookgraph;

import android.graphics.drawable.Drawable;

/**
 * CustomListItem
 * @author kashimoto
 *
 */
public class CustomListItem {
	private String mMessage;
	private String mCreatedTime;
	private String mStory;
    private String mId;

	public CustomListItem(String id, String createdTime, String message, String story) {
        mId = id;
        mCreatedTime = createdTime;
        mMessage = message;
        mStory = story;
	}

    public String getCreatedTime() {
        return mCreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        mCreatedTime = createdTime;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getStory() {
        return mStory;
    }

    public void setStory(String story) {
        mStory = story;
    }
}
