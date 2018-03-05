package com.hangoclong.httpurlconnection.screen.userlist;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.hangoclong.httpurlconnection.R;
import com.hangoclong.httpurlconnection.data.model.User;
import com.hangoclong.httpurlconnection.data.repository.UserRepository;
import com.hangoclong.httpurlconnection.data.source.remote.UserRemoteDataSource;
import com.hangoclong.httpurlconnection.screen.BaseActivity;
import java.util.List;

public class UserListActivity extends BaseActivity implements UserListContract.View {

    private UserListContract.Presenter mPresenter;
    private RecyclerView mRecyclerViewUser;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        UserRepository userRepository = UserRepository.getInstance(UserRemoteDataSource.getInstance(), null);
        mPresenter = new UserListPresenter(userRepository);
        mPresenter.setView(this);
        mPresenter.loadUser();
    }

    @Override
    public void showUsers(List<User> userList) {
        UserListAdapter adapter = new UserListAdapter(this, userList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewUser.setLayoutManager(layoutManager);
        mRecyclerViewUser.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        mRecyclerViewUser = findViewById(R.id.recycler_user);
    }
}
