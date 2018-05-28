package com.hangoclong.roompersistent.screen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hangoclong.roompersistent.R;
import com.hangoclong.roompersistent.data.model.Product;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long on
 */

public class MainAdapter extends BaseRecyclerViewAdapter<MainAdapter.ViewHolder> {

    private List<Product> mProducts = new ArrayList<>();
    private ItemClickListener mItemClickListener;

    public MainAdapter(@NonNull Context context, ItemClickListener itemClickListener)
    {
        super(context);
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_item_product, parent, false);
        return new ViewHolder(view, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(mProducts.get(position));
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public void replaceData(List<Product> product) {
        mProducts.clear();
        mProducts.addAll(product);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private Product mProduct;
        public ViewHolder(@NonNull View itemView, final ItemClickListener itemClickListener) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.txt_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mProduct = mProducts.get(getAdapterPosition());
                    itemClickListener.onItemClicked(mProduct);
                }
            });
        }

        public void setData(Product product) {
            if (product == null) {
                return;
            }
            mTextView.setText(product.getName());
        }

    }

    public interface ItemClickListener {
        void onItemClicked(Product product);
    }
}
