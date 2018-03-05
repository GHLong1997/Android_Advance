package com.hangoclong.httpurlconnection.data.source;

import com.hangoclong.httpurlconnection.data.model.User;
import java.util.List;

/**
 * Created by Admin on 3/5/2018.
 */

public interface UserDataSource {

    List<User> getListUser();

    interface LocalDataSource extends UserDataSource {
    }

    interface RemoteDataSource extends UserDataSource {
    }
}
