package om.e.youtubeplaylist.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import om.e.youtubeplaylist.R;
import om.e.youtubeplaylist.constants.DynamicConstants;
import om.e.youtubeplaylist.model.PlaylistItems;
import om.e.youtubeplaylist.model.PlaylistWithPage;
import om.e.youtubeplaylist.model.Playlists;

public class PlaceholderFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    RecyclerView recyclerView;
    ArrayList<Playlists> playlists;
    ArrayList<PlaylistItems> playlistItems;
    ArrayList<PlaylistWithPage> playlistWithPage;
    PlaylistItemAdapter playlistItemAdapter;

    static int pageNumber=0;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        pageNumber=DynamicConstants.currentPage;
    }

    public PlaceholderFragment() {
    }

    static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        pageNumber=sectionNumber-1;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        /*TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));*/
        playlists=new ArrayList<>();
        playlistItems=new ArrayList<>();
        playlistWithPage=new ArrayList<>();
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recycler_view);
        playlistItemAdapter = new PlaylistItemAdapter(getContext());
        recyclerView.setAdapter(playlistItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        playlistItemAdapter.setListItems(DynamicConstants.allPlayListItems.get(pageNumber).getPlaylists());

        return rootView;
    }
}
