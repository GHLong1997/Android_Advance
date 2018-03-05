package com.hangoclong.httpurlconnection.screen.userlist;

import com.hangoclong.httpurlconnection.data.model.User;
import com.hangoclong.httpurlconnection.screen.BasePresenter;
import java.util.List;

public interface UserListContract {

    interface View {
        void showUsers(List<User> userList);
    }

    interface Presenter extends BasePresenter<View>{
        void loadUser();
    }
}
