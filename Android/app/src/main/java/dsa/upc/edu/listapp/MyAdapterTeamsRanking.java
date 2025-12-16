package dsa.upc.edu.listapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import dsa.upc.edu.listapp.github.Team;

public class MyAdapterTeamsRanking extends RecyclerView.Adapter<MyAdapterTeamsRanking.ViewHolder> {
    private List<Team> values;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView teamName;
        public TextView teamPoints;
        public ImageView teamAvatar;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            teamName = (TextView) v.findViewById(R.id.teamName);
            teamPoints = (TextView) v.findViewById(R.id.teamPoints);
            teamAvatar = (ImageView) v.findViewById(R.id.teamAvatar);
        }
    }

    public void setData(List<Team> myDataset) {
        values = myDataset;
        notifyDataSetChanged();
    }

    public MyAdapterTeamsRanking() {
        values = new ArrayList<>();
    }

    @Override
    public MyAdapterTeamsRanking.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.team_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Team team = values.get(position);
        final String name = team.getName();
        final String points = String.valueOf(team.getPoints());
        holder.teamName.setText(name);
        holder.teamPoints.setText(points);

        Glide.with(holder.itemView.getContext())
                .load(team.getAvatar())
                .into(holder.teamAvatar);
    }

    @Override
    public int getItemCount() {
        return (values != null) ? values.size() : 0;
    }
}
