package orz.kassy.facebookgraph;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

import java.util.List;

/**
 * Created by kashimoto on 2015/10/10.
 */
@JsonModel(decamelize = true)
public class Hoge {
    @JsonKey
    List<FeedJson> data;

    public List<FeedJson> getData() {
        return data;
    }

    public void setData(List<FeedJson> data) {
        this.data = data;
    }
}
