package walden.com.movietest;

/**
 * Created by Administrator on 2017/6/28 0028.
 */

public class DataBean {
    String imgPath;
    long createTime;
    long mp4Size;
    String mp4Path;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getMp4Size() {
        return mp4Size;
    }

    public void setMp4Size(long mp4Size) {
        this.mp4Size = mp4Size;
    }

    public String getMp4Path() {
        return mp4Path;
    }

    public void setMp4Path(String mp4Path) {
        this.mp4Path = mp4Path;
    }
}
