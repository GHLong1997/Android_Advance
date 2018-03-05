package com.hangoclong.httpurlconnection.data.repository;

import com.hangoclong.httpurlconnection.data.model.User;
import com.hangoclong.httpurlconnection.data.source.UserDataSource;
import com.hangoclong.httpurlconnection.data.source.local.UserLocalDataSource;
import com.hangoclong.httpurlconnection.data.source.remote.UserRemoteDataSource;

import java.util.List;

/**
 * Created by Admin on 3/5/2018.
 */

public class UserRepository implements UserDataSource.RemoteDataSource, UserDataSource.LocalDataSource{

    private static UserRepository instance;
    private UserRemoteDataSource mUserRemoteDataSource;
    private UserLocalDataSource mUserLocalDataSource;

    private UserRepository(UserRemoteDataSource userRemoteDataSource, UserLocalDataSource userLocalDataSource) {
        this.mUserRemoteDataSource = userRemoteDataSource;
        this.mUserLocalDataSource = userLocalDataSource;
    }

    public static synchronized UserRepository getInstance(UserRemoteDataSource userRemoteDataSource,
                                           UserLocalDataSource userLocalDataSource) {
        if (instance == null ) {
            instance = new UserRepository(userRemoteDataSource, userLocalDataSource);
        }
        return instance;
    }

    @Override
    public List<User> getListUser() {
       return mUserRemoteDataSource.getListUser();
    }
}
