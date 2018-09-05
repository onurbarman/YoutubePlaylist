package om.e.youtubeplaylist.model;

import java.util.ArrayList;

public class AllPlayLists {
    ArrayList<PlaylistItems> playlists;

    public AllPlayLists(){

    }
    public AllPlayLists(ArrayList<PlaylistItems> playlists) {
        this.playlists = playlists;
    }

    public ArrayList<PlaylistItems> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<PlaylistItems> playlists) {
        this.playlists = playlists;
    }
}
