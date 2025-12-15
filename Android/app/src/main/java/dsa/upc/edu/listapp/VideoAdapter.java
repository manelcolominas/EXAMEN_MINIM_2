package dsa.upc.edu.listapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import dsa.upc.edu.listapp.github.Video;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private List<Video> values;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView videoTitle;
        public ImageView videoThumbnail;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            videoTitle = (TextView) v.findViewById(R.id.videoTitle);
            videoThumbnail = (ImageView) v.findViewById(R.id.videoThumbnail);
        }
    }

    public void setData(List<Video> myDataset) {
        values = myDataset;
        notifyDataSetChanged();
    }

    public void addVideos(List<Video> newVideos) {
        if (values == null) {
            values = newVideos;
        } else {
            values.addAll(newVideos);
        }
        notifyDataSetChanged();
    }

    public VideoAdapter() {
        values = null;
    }

    public VideoAdapter(List<Video> myDataset) {
        values = myDataset;
    }

    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.video_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Video video = values.get(position);
        final String name = video.getTitle();
        holder.videoTitle.setText(name);

        Glide.with(holder.itemView.getContext())
                .load(video.getThumbnailUrl())
                .into(holder.videoThumbnail);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + video.getVideoId()));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + video.getVideoId()));
                try {
                    v.getContext().startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    v.getContext().startActivity(webIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (values == null) return 0;
        return values.size();
    }
}
