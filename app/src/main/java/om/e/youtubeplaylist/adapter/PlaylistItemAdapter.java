package om.e.youtubeplaylist.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import om.e.youtubeplaylist.R;
import om.e.youtubeplaylist.activity.VideoActivity;
import om.e.youtubeplaylist.model.PlaylistItems;
import om.e.youtubeplaylist.service.AppController;

public class PlaylistItemAdapter extends RecyclerView.Adapter<PlaylistItemAdapter.PlaylistItemViewHolder> {

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    private LayoutInflater inflater;

    private List<PlaylistItems> singletons;


    @Override
    public int getItemCount() {
        if (singletons != null)
            return singletons.size();
        else return 0;
    }

    class PlaylistItemViewHolder extends RecyclerView.ViewHolder {
        private final NetworkImageView networkImageView;
        private final TextView imgtitle;
        private final TextView imgdesc;
        private final TextView tvURL;
        private final TextView tvVideoID;

        private PlaylistItemViewHolder(View itemView) {
            super(itemView);
            networkImageView =itemView.findViewById(R.id.video_image);
            imgtitle = itemView.findViewById(R.id.video_title);
            imgdesc = itemView.findViewById(R.id.video_description);
            tvURL=itemView.findViewById(R.id.tv_url);
            tvVideoID=itemView.findViewById(R.id.tv_videoId);

            (itemView.findViewById(R.id.asser)).setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View view) {
                    Intent intent=new Intent(view.getContext(), VideoActivity.class);
                    intent.putExtra("videoId",tvVideoID.getText().toString());
                    view.getContext().startActivity(intent);
                }

            });
        }
    }

    public void setListItems(ArrayList<PlaylistItems> listItems){
        singletons = listItems;
        notifyDataSetChanged();
    }

    public PlaylistItemAdapter(Context context) { inflater = LayoutInflater.from(context); }

    @Override
    public PlaylistItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.cardview_playlist, parent, false);
        return new PlaylistItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlaylistItemViewHolder holder, int position) {
        PlaylistItems singleton = (PlaylistItems) this.singletons.get(position);

        holder.networkImageView.setImageUrl(singleton.getVideoUrl(), this.imageLoader);

        holder.tvVideoID.setText(singleton.getVideoId());

        holder.imgtitle.setText(singleton.getVideoTitle());

        holder.imgdesc.setText(singleton.getVideoDescription());

    }


}
