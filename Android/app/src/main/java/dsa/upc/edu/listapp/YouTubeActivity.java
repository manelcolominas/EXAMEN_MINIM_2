package dsa.upc.edu.listapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dsa.upc.edu.listapp.github.API_YouTube;
import dsa.upc.edu.listapp.github.EETACBROSSystemService;
import dsa.upc.edu.listapp.github.SearchListResponse;
import dsa.upc.edu.listapp.github.Video;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YouTubeActivity extends AppCompatActivity {

    private EETACBROSSystemService api;
    private RecyclerView recyclerView;
    private VideoAdapter mAdapter;
    private LinearLayoutManager layoutManager;

    private static final String YOUTUBE_API_KEY = "AIzaSyDNr0BQ0EVna4F9Uh00jWnKqUnK8kAAm2w";
    private String nextPageToken;
    private boolean isLoading = false;
    private String query = "music"; // Search for music
    private static final String MUSIC_CATEGORY_ID = "10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        recyclerView = (RecyclerView) findViewById(R.id.videoRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new VideoAdapter();
        recyclerView.setAdapter(mAdapter);

        api = API_YouTube.getGithub();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    loadVideos(nextPageToken);
                }
            }
        });

        loadVideos(null);
    }

    private void loadVideos(String pageToken) {
        isLoading = true;

        api.searchVideos(YOUTUBE_API_KEY, query, pageToken, MUSIC_CATEGORY_ID).enqueue(new Callback<SearchListResponse>() {
            @Override
            public void onResponse(Call<SearchListResponse> call, Response<SearchListResponse> response) {
                if (response.isSuccessful()) {
                    SearchListResponse searchResponse = response.body();
                    if (searchResponse != null) {
                        nextPageToken = searchResponse.nextPageToken;
                        List<Video> videos = new ArrayList<>();
                        for (SearchListResponse.SearchResult item : searchResponse.items) {
                            String title = item.snippet.title;
                            String thumbnailUrl = item.snippet.thumbnails.defaultThumbnail.url;
                            String videoId = item.id.videoId;
                            videos.add(new Video(title, thumbnailUrl, videoId));
                        }
                        mAdapter.addVideos(videos);
                    }
                }
                isLoading = false;
            }

            @Override
            public void onFailure(Call<SearchListResponse> call, Throwable t) {
                Log.e("YouTubeActivity", "Error fetching videos", t);
                isLoading = false;
            }
        });
    }
}
