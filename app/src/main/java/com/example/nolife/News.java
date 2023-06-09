package com.example.nolife;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.nolife.databinding.FragmentNewsBinding;
import com.jacksonandroidnetworking.JacksonParserFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link News#newInstance} factory method to
 * create an instance of this fragment.
 */
public class News extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public News() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment News.
     */
    // TODO: Rename and change types and number of parameters
    public static News newInstance(String param1, String param2) {
        News fragment = new News();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private static String API_KEY="da26206921d84674a111590b3c1e8409";

    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;

    private ArticleViewModel mArticleViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentNewsBinding binding = FragmentNewsBinding.inflate(inflater);
        final ArticleAdapter adapter = new ArticleAdapter(new ArticleAdapter.ArticleDiff());
        binding.recyclerviewId.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.recyclerviewId.setAdapter(adapter);
        mProgressBar = binding.progressbarId;
        mArticleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class);
        mArticleViewModel.getAllArticles().observe(this, articles -> {
            adapter.submitList(articles);
        });

        AndroidNetworking.initialize(getContext());
        AndroidNetworking.setParserFactory(new JacksonParserFactory());
        get_news_from_api();
        return binding.getRoot();
        
    }
    public void get_news_from_api(){
        //https://newsapi.org/v2/everything?q=(%22трейлер%22OR%22консоль%22)AND%22игры%22&apiKey=da26206921d84674a111590b3c1e8409
        AndroidNetworking.get("https://newsapi.org/v2/everything")
                .addQueryParameter("q","\"dota\"OR\"геймплей\"OR\"Warhammer\"OR\"Alan\"OR\"Steam\"")
                .addQueryParameter("excludeDomains","google.com,3dnews.ru,lenta.ru")
                .addQueryParameter("language","ru")
                .addQueryParameter("apiKey", API_KEY)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mProgressBar.setVisibility(View.GONE);
                        try{
                            JSONArray articles=response.getJSONArray("articles");
                            for (int j=0;j<articles.length();j++){
                                JSONObject article = articles.getJSONObject(j);
                                if (article.getString("author").equals("null")){
                                    Article current = new Article(article.getJSONObject("source").getString("name"),
                                            article.getString("title"),
                                            article.getString("description"),
                                            article.getString("url"),
                                            article.getString("urlToImage"),
                                            article.getString("publishedAt"),
                                            article.getString("content"));
                                    mArticleViewModel.insert(current);
                                }
                                else {
                                    Article current = new Article(article.getString("author"),
                                            article.getString("title"),
                                            article.getString("description"),
                                            article.getString("url"),
                                            article.getString("urlToImage"),
                                            article.getString("publishedAt"),
                                            article.getString("content"));
                                    mArticleViewModel.insert(current);
                                }

                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            Log.d("news", "Error: "+e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.d("news","Error detail : "+error.getErrorDetail());
                        Log.d("news","Error response : "+error.getResponse());
                    }
                });
    }
}