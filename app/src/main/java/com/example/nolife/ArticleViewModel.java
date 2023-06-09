package com.example.nolife;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ArticleViewModel extends AndroidViewModel {
    private ArticleRepository mRepository;
    private final LiveData<List<Article>> mAllArticles;
    private final LiveData<List<Article>> mFavourites;

    public ArticleViewModel (Application application){
        super(application);
        mRepository = new ArticleRepository(application);
        mAllArticles = mRepository.getAllArticles();
        mFavourites = mRepository.getmFavourites();
    }
    LiveData<List<Article>> getAllArticles(){
        return mAllArticles;
    }
    LiveData<List<Article>> getFavourites(){
        return mFavourites;
    }
    public void insert(Article article){
        mRepository.insert(article);
    }
    public void insertUser(User user){
        mRepository.insertUser(user);
    }

    public User getUser(String mail, String password){
        return mRepository.getUser(mail, password);
    }
}
