package om.e.youtubeplaylist.activity;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;
import om.e.youtubeplaylist.R;
import om.e.youtubeplaylist.adapter.PlaylistItemAdapter;
import om.e.youtubeplaylist.adapter.ViewPagerAdapter;
import om.e.youtubeplaylist.constants.DynamicConstants;
import om.e.youtubeplaylist.model.AllPlayLists;
import om.e.youtubeplaylist.model.PlaylistItems;
import om.e.youtubeplaylist.model.PlaylistWithPage;
import om.e.youtubeplaylist.model.Playlists;

public class MainActivity extends AppCompatActivity {

    ViewPagerAdapter pagerAdapter;
    ViewPager viewpager;

    ArrayList<Playlists> playlists;
    ArrayList<PlaylistItems> playlistItems;
    ArrayList<PlaylistWithPage> playlistWithPage;

    String playlistURL="https://www.googleapis.com/youtube/v3/playlists?part=snippet&key=AIzaSyBU3bJ7N-RYuV76huwEB4eHGrXdS-cdTIc&channelId=UCgKabubhWZaEOsTQM-FrAsw&maxResults=25";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playlists=new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getAllPlaylists();
    }


    void getAllPlaylists()
    {

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, playlistURL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);

                    JSONObject jsonPageInfo=jsonObject.getJSONObject("pageInfo");
                    DynamicConstants.pageCount=jsonPageInfo.getInt("totalResults");

                    JSONArray jsonArray=jsonObject.getJSONArray("items");

                    for(int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONObject jsonsnippet= jsonObject1.getJSONObject("snippet");

                        Playlists playlist=new Playlists();
                        playlist.setPlaylistId(jsonObject1.getString("id"));
                        playlist.setPlaylistName(jsonsnippet.getString("title"));
                        playlists.add(playlist);
                    }
                    DynamicConstants.playlists =playlists;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

        initViewPager();

    }


    void initViewPager()
    {
        if ( DynamicConstants.playlists.size()-1>=DynamicConstants.currentPage)
        {
            pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewpager = (ViewPager) findViewById(R.id.viewpager);
            CircleIndicator indicator = (CircleIndicator) findViewById(R.id.tabs);
            for (int i = 0; i < playlists.size(); i++)
                pagerAdapter.AddFragment(i);
            viewpager.setAdapter(pagerAdapter);
            indicator.setViewPager(viewpager);
            pagerAdapter.registerDataSetObserver(indicator.getDataSetObserver());
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
