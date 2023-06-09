package com.example.nolife;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ArticleRepository {
    private ArticleDao mArticleDao;
    private UserDao mUserDao;

    private LiveData<List<Article>> mAllArticles;
    private LiveData<List<Article>> mFavourites;

    ArticleRepository(Application application){
        ArticleDatabase db = ArticleDatabase.getDatabase(application);
        mArticleDao = db.articleDao();
        mUserDao = db.userDao();
        mAllArticles = mArticleDao.getAll();
        mFavourites = mArticleDao.getFavourites();
    }

    LiveData<List<Article>> getAllArticles(){
        return mAllArticles;
    }
    LiveData<List<Article>> getmFavourites(){
        return mFavourites;
    }

    void insert(Article article){
        ArticleDatabase.databaseWriteExecutor.execute(() -> {
            mArticleDao.insertAll(article);
        });
    }
    void insertUser(User user){
        ArticleDatabase.databaseWriteExecutor.execute(() -> {
            mUserDao.insertAll(user);
        });
    }
    User getUser(String mail, String password){
        return mUserDao.getUser(mail, password);
    }
}
