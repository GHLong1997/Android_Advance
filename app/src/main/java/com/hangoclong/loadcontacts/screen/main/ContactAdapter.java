package com.hangoclong.loadcontacts.screen.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hangoclong.loadcontacts.R;
import com.hangoclong.loadcontacts.data.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 3/2/2018.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contact> mContactList = new ArrayList<>();

    public ContactAdapter(List<Contact> contactList) {
        mContactList = contactList;
    }
    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_contact, parent, false);
        if (view!= null) {
            return new ContactViewHolder(view) ;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ContactAdapter.ContactViewHolder holder, int position) {
        holder.fillData(mContactList.get(position));
    }

    @Override
    public int getItemCount() {
        return mContactList != null ? mContactList.size() : 0;
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextName;
        private TextView mTextPhone;

         ContactViewHolder(View itemView) {
            super(itemView);
             mTextName = itemView.findViewById(R.id.text_name);
             mTextPhone = itemView.findViewById(R.id.text_phone);
        }

        void fillData(Contact contact) {
            mTextName.setText(contact.getName());
            mTextPhone.setText(contact.getNumber());
        }
    }
}
