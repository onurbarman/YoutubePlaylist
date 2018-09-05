package om.e.youtubeplaylist.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import om.e.youtubeplaylist.R;
import om.e.youtubeplaylist.adapter.PlaylistItemAdapter;
import om.e.youtubeplaylist.constants.DynamicConstants;

public class VideoFragment extends BaseFragment {

    int pageNumber=0;
    View v;
    RecyclerView recyclerView;
    PlaylistItemAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_main, parent, false);
        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view);

        adapter = new PlaylistItemAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (DynamicConstants.currentPage==0)
            adapter.setListItems(DynamicConstants.allPlayListItems.get(DynamicConstants.currentPage).getPlaylists());
        if(DynamicConstants.currentPage==1)
            adapter.setListItems(DynamicConstants.allPlayListItems.get(DynamicConstants.currentPage).getPlaylists());
        recyclerView.setAdapter(adapter);
        Log.e("Burdayız ","3 " + DynamicConstants.currentPage);
        DynamicConstants.currentPage++;
        return v;
    }
}
