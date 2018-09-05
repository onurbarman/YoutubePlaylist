package om.e.youtubeplaylist.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;

import om.e.youtubeplaylist.constants.DynamicConstants;
import om.e.youtubeplaylist.fragment.VideoFragment;
import om.e.youtubeplaylist.model.Playlists;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    //public ArrayList<Playlists> playlists=new ArrayList<>();
    ArrayList<Integer> listPages=new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //DynamicConstants.currentPage=listPages.get(position);
        VideoFragment fragment=new VideoFragment();
        Log.e("Burdayız ","2");
        return fragment;
    }

    public void AddFragment(int page)
    {
        listPages.add(page);
    }

    @Override
    public int getCount() {
        return DynamicConstants.pageCount;
    }
}

