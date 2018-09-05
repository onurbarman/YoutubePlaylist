package om.e.youtubeplaylist.model;

public class PlaylistWithPage {
    int pageNo;
    String playlistId;

    public PlaylistWithPage(){

    }
    public PlaylistWithPage(int pageNo, String playlistId) {
        this.pageNo = pageNo;
        this.playlistId = playlistId;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }
}
