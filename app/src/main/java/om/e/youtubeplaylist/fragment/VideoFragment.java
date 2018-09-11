package om.e.youtubeplaylist.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import om.e.youtubeplaylist.R;
import om.e.youtubeplaylist.adapter.PlaylistItemAdapter;
import om.e.youtubeplaylist.constants.DynamicConstants;
import om.e.youtubeplaylist.model.AllPlayLists;
import om.e.youtubeplaylist.model.PlaylistItems;
import om.e.youtubeplaylist.model.PlaylistWithPage;
import om.e.youtubeplaylist.model.Playlists;

public class VideoFragment extends BaseFragment {
    View v;
    RecyclerView recyclerView;
    PlaylistItemAdapter adapter;

    ArrayList<Playlists> playlists;
    ArrayList<PlaylistItems> playlistItems;
    ArrayList<PlaylistWithPage> playlistWithPage;
    String TAG="MainActivity";

    String playlistId="";
    ProgressDialog pDialog;
    String playlistitemsURL="https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId="+playlistId+"&key=AIzaSyBU3bJ7N-RYuV76huwEB4eHGrXdS-cdTIc&channelId=UCoQp_Duwqh0JWEZrg4DT2Ug&maxResults=25";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (DynamicConstants.playlists.size()-1>=DynamicConstants.currentPage)
            getPlayListWithPage();
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_main, parent, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        adapter = new PlaylistItemAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
    private void getPlayListWithPage() {
        playlists=new ArrayList<>();
        playlistItems=new ArrayList<>();
        playlistWithPage=new ArrayList<>();

        PlaylistWithPage page=new PlaylistWithPage();
        page.setPageNo(DynamicConstants.currentPage+1);
        page.setPlaylistId(DynamicConstants.playlists.get(DynamicConstants.currentPage).getPlaylistId());
        playlistWithPage.add(page);

        playlistId=playlistWithPage.get(0).getPlaylistId();
        DynamicConstants.currentPage++;
        getPlaylist task=new getPlaylist();
        task.execute();
    }

    class getPlaylist extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPreExecute() {
            pDialog=new ProgressDialog(getContext());
            pDialog.setMessage("Videolar Yükleniyor...");
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            getAllPlaylistItems();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (pDialog.isShowing())
                pDialog.dismiss();
            if (DynamicConstants.pageCount==DynamicConstants.currentPage)
                Log.e("Countt",DynamicConstants.videoCount+"");

        }

    }

    private void getAllPlaylistItems() {
        playlistitemsURL="https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId="+playlistId+"&key=AIzaSyBU3bJ7N-RYuV76huwEB4eHGrXdS-cdTIc&channelId=UCoQp_Duwqh0JWEZrg4DT2Ug&maxResults=25";

        Log.e("Burdayız ","4");
        RequestQueue requestQueue= Volley.newRequestQueue(getContext().getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, playlistitemsURL, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("items");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONObject jsonsnippet= jsonObject1.getJSONObject("snippet");
                        JSONObject jsonObjectdefault = jsonsnippet.getJSONObject("thumbnails").getJSONObject("medium");

                        PlaylistItems playlistItem=new PlaylistItems();

                        String videoid=jsonObject1.getString("id");
                        Log.e(TAG," New Video Id" +videoid);

                        playlistItem.setVideoUrl(jsonObjectdefault.getString("url"));
                        playlistItem.setVideoTitle(jsonsnippet.getString("title"));
                        playlistItem.setVideoDescription(jsonsnippet.getString("description"));
                        playlistItem.setVideoId(videoid);

                        playlistItems.add(playlistItem);
                        DynamicConstants.videoCount++;

                    }
                    adapter.setListItems(playlistItems);
                    recyclerView.setAdapter(adapter);

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
}
