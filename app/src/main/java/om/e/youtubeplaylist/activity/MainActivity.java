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

    RecyclerView recyclerView;
    ArrayList<Playlists> playlists;
    ArrayList<PlaylistItems> playlistItems;
    ArrayList<PlaylistWithPage> playlistWithPage;
    PlaylistItemAdapter playlistItemAdapter;

    String TAG="MainActivity";
    //https://www.googleapis.com/youtube/v3/playlists               Bu playlistlerin idlerini içeren api
    //https://www.googleapis.com/youtube/v3/playlistItems?          Bu playlistlerin içeriklerini içeren api
    String playlistId="";
    String playlistURL="https://www.googleapis.com/youtube/v3/playlists?part=snippet&key=AIzaSyBU3bJ7N-RYuV76huwEB4eHGrXdS-cdTIc&channelId=UCoQp_Duwqh0JWEZrg4DT2Ug&maxResults=25";
    String playlistitemsURL="https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId="+playlistId+"&key=AIzaSyBU3bJ7N-RYuV76huwEB4eHGrXdS-cdTIc&channelId=UCoQp_Duwqh0JWEZrg4DT2Ug&maxResults=25";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();






    }

    private void getPlayListWithPage() {
        DynamicConstants.allPlayListItems=new ArrayList<>();
        for (int i=0;i<playlists.size();i++)
        {
            PlaylistWithPage page=new PlaylistWithPage();
            page.setPageNo(i+1);
            page.setPlaylistId(playlists.get(i).getPlaylistId());
            playlistWithPage.add(page);

            playlistId=playlistWithPage.get(i).getPlaylistId();
            getAllPlaylistItems();
        }
    }

    private void getAllPlaylistItems() {
        playlistitemsURL="https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId="+playlistId+"&key=AIzaSyBU3bJ7N-RYuV76huwEB4eHGrXdS-cdTIc&channelId=UCoQp_Duwqh0JWEZrg4DT2Ug&maxResults=25";

        Log.e("Burdayız ","4");
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, playlistitemsURL, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {
                try {
                    playlistItems.clear();

                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("items");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        //JSONObject jsonVideoId=jsonObject1.getJSONObject("id");
                        JSONObject jsonsnippet= jsonObject1.getJSONObject("snippet");
                        JSONObject jsonObjectdefault = jsonsnippet.getJSONObject("thumbnails").getJSONObject("medium");

                        PlaylistItems playlistItem=new PlaylistItems();

                        String videoid=jsonObject1.getString("id");
                        //String videoid=jsonVideoId.getString("videoId");
                        Log.e(TAG," New Video Id" +videoid);

                        playlistItem.setVideoUrl(jsonObjectdefault.getString("url"));
                        playlistItem.setVideoTitle(jsonsnippet.getString("title"));
                        playlistItem.setVideoDescription(jsonsnippet.getString("description"));
                        playlistItem.setVideoId(videoid);

                        playlistItems.add(playlistItem);

                    }
                    DynamicConstants.playlistItems=playlistItems;
                    AllPlayLists play=new AllPlayLists();
                    play.setPlaylists(playlistItems);
                    DynamicConstants.allPlayListItems.add(play);
                    Log.e("Burdayız","1 )Eklendi Adet : "+playlistItems.size());

                    if(playlists.size()==DynamicConstants.allPlayListItems.size()) {
                        for (int i=0;i<playlists.size();i++)
                            pagerAdapter.AddFragment(i);
                        pagerAdapter.notifyDataSetChanged();
                    }
                    /*recyclerView.setAdapter(playlistItemAdapter);
                    playlistItemAdapter.setListItems(playlistItems);*/

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



    }

    private void getAllPlaylists() {

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest=new StringRequest(Request.Method.GET, playlistURL, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {

                try {

                    JSONObject jsonObject=new JSONObject(response);

                    JSONObject jsonPageInfo=jsonObject.getJSONObject("pageInfo");
                    DynamicConstants.pageCount=jsonPageInfo.getInt("totalResults");
                    //DynamicConstants.pageCount=Integer.parseInt(String.valueOf(pageCount));

                    JSONArray jsonArray=jsonObject.getJSONArray("items");

                    for(int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONObject jsonsnippet= jsonObject1.getJSONObject("snippet");
                        JSONObject jsonObjectdefault = jsonsnippet.getJSONObject("thumbnails").getJSONObject("medium");

                        Playlists playlist=new Playlists();
                        playlist.setPlaylistId(jsonObject1.getString("id"));
                        playlist.setPlaylistName(jsonsnippet.getString("title"));
                        playlists.add(playlist);
                    }

                    DynamicConstants.playlists=playlists;

                    getPlayListWithPage();

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

    }

    private void initList() {
        playlists=new ArrayList<>();
        playlistItems=new ArrayList<>();
        playlistWithPage=new ArrayList<>();
       /* recyclerView=(RecyclerView)findViewById(R.id.recyler_view);
        playlistItemAdapter = new PlaylistItemAdapter(MainActivity.this);
        recyclerView.setAdapter(playlistItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));*/

        getAllPlaylists();
    }

    private void initViewPager() {
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewpager = (ViewPager)findViewById(R.id.viewpager);
        CircleIndicator indicator = (CircleIndicator)findViewById(R.id.tabs);
        viewpager.setAdapter(pagerAdapter);
        indicator.setViewPager(viewpager);
        pagerAdapter.registerDataSetObserver(indicator.getDataSetObserver());

        initList();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViewPager();
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
