package dsa.upc.edu.listapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dsa.upc.edu.listapp.github.API;
import dsa.upc.edu.listapp.github.EETACBROSSystemService;
import dsa.upc.edu.listapp.github.Team;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankingActivity extends AppCompatActivity {
    private static final String TAG = "RankingActivity";
    private EETACBROSSystemService api;
    private RecyclerView teamRecyclerView;
    private MyAdapterTeamsRanking mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Team> teams = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);


        teamRecyclerView = findViewById(R.id.teamRecyclerView);

        if (teamRecyclerView != null) {
            teamRecyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            teamRecyclerView.setLayoutManager(layoutManager);
            mAdapter = new MyAdapterTeamsRanking();
            teamRecyclerView.setAdapter(mAdapter);
        }

        api = API.getGithub();

        loadTeamsRanking();
    }

/*    @Override
    protected void onResume() {
        super.onResume();
    }*/


    private void renderUserItems() {
        if (mAdapter != null) {
            mAdapter.setData(teams);
        }
    }

    private void loadTeamsRanking() {
        api.getTeamsRanking().enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    teams.clear();
                    teams.addAll(response.body());
                    renderUserItems();
                    Toast.makeText(RankingActivity.this,
                            "Inventory loaded: " + teams.size() + " items",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RankingActivity.this,
                            "Failed to load inventory",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {
                Log.e(TAG, "Connection error", t);
                Toast.makeText(RankingActivity.this,
                        "Connection error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
