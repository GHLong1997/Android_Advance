package com.hangoclong.httpurlconnection.screen.userlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.hangoclong.httpurlconnection.R;
import com.hangoclong.httpurlconnection.data.model.User;
import com.hangoclong.httpurlconnection.screen.BaseRecyclerViewAdapter;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserListAdapter extends BaseRecyclerViewAdapter<UserListAdapter.ViewHolder> {

    private List<User> mUserList;
    protected UserListAdapter(Context mContext, List<User> userList) {
        super(mContext);
        mUserList = userList;
    }

    @Override
    public UserListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserListAdapter.ViewHolder holder, int position) {
        holder.setData(mUserList.get(position));
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView mImageUser;
        private TextView mTextViewUser;
         ViewHolder(View itemView) {
            super(itemView);
            mImageUser = itemView.findViewById(R.id.image_user);
            mTextViewUser = itemView.findViewById(R.id.text_name);
        }

        public void setData(User user) {
            Glide.with(itemView.getContext()).load(user.getAvatarUrl()).into(mImageUser);
            mTextViewUser.setText(user.getLogin());
        }
    }
}
