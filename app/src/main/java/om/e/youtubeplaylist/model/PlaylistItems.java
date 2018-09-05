package om.e.youtubeplaylist.model;

public class PlaylistItems {
    String videoTitle;
    String videoDescription;
    String videoId;
    String videoUrl;

    public PlaylistItems(){

    }
    public PlaylistItems(String videoTitle, String videoDescription, String videoId, String videoUrl) {
        this.videoTitle = videoTitle;
        this.videoDescription = videoDescription;
        this.videoId = videoId;
        this.videoUrl = videoUrl;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
