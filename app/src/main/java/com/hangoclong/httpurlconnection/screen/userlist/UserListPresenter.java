package com.hangoclong.httpurlconnection.screen.userlist;

import com.hangoclong.httpurlconnection.data.model.User;
import com.hangoclong.httpurlconnection.data.repository.UserRepository;

import java.util.List;

public class UserListPresenter implements UserListContract.Presenter {

    private UserListContract.View mView;
    private UserRepository mUserRepository;

    UserListPresenter(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    @Override
    public void setView(UserListContract.View view) {
        this.mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void loadUser() {

        List<User> userList = mUserRepository.getListUser();

        if (userList.size() > 0) {
            mView.showUsers(userList);
        }
    }
}
