package com.hangoclong.httpurlconnection.data.source.remote;

import com.hangoclong.httpurlconnection.data.model.User;
import com.hangoclong.httpurlconnection.data.source.UserDataSource;
import com.hangoclong.httpurlconnection.data.source.remote.config.service.FetchDataFromGithub;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Admin on 3/5/2018.
 */

public class UserRemoteDataSource implements UserDataSource.RemoteDataSource {

    private static UserRemoteDataSource instance;

    public static synchronized UserRemoteDataSource getInstance() {
        if (instance == null) {
            instance = new UserRemoteDataSource();
        }
        return instance;
    }
    @Override
    public List<User> getListUser() {
        List<User> userList = new ArrayList<>();
        try {
            String jsonString =  new FetchDataFromGithub()
                    .execute("https://api.github.com/users/google/followers")
                    .get();
            if (jsonString != null) {
                JSONArray jsonArray = new JSONArray(jsonString);
                int count = jsonArray.length();
                for (int i = 0; i < count; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    User user = new User();
                    user.setLogin(jsonObject.getString("login"));
                    user.setAvatarUrl(jsonObject.getString("avatar_url"));
                    userList.add(user);
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
