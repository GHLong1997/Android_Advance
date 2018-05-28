package com.hangoclong.roompersistent.screen;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.hangoclong.roompersistent.R;
import com.hangoclong.roompersistent.data.model.Product;
import com.hangoclong.roompersistent.data.repository.ProductRepository;
import com.hangoclong.roompersistent.data.source.local.ProductDatabase;
import com.hangoclong.roompersistent.data.source.local.ProductLocalDataSource;
import com.hangoclong.roompersistent.utils.rx.SchedulerProvider;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View,
        View.OnClickListener, MainAdapter.ItemClickListener,
        SearchView.OnQueryTextListener, SearchView.OnCloseListener{

    private MainContract.Presenter mPresenter;
    private RecyclerView mListView;
    private MainAdapter mAdapter;
    private List<Product> mProducts;
    private Button mButtonAddData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        ProductDatabase productDatabase = ProductDatabase.getInstance(this);
        mPresenter = new MainPresenter(ProductRepository.getInstance(
                ProductLocalDataSource.getInstance(productDatabase.productDAO())),
                SchedulerProvider.getInstance());
        mPresenter.setView(this);
        mPresenter.getProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {
                Log.d("haha", "onChanged: " + products.get(0).getName());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setIconified(true);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
            mPresenter.deleteAllProduct();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void showProducts(List<Product> products) {
        Toast.makeText(this, "haha" + products.size(), Toast.LENGTH_SHORT).show();
        mAdapter.replaceData(products);
    }

    @Override
    public void showNoProduct() {

    }

    @Override
    public void showError(Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteSuccess() {
        Toast.makeText(this, "Delete Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateSuccess() {
        Toast.makeText(this, "Updated ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_insert:
                Product product = new Product("Xiaomi Redmi Note 4", 5, 100000);
                mPresenter.insertProduct(product);
//                mPresenter.deleteProductById(3);
                break;
        }

    }

    @Override
    public void onItemClicked(final Product product) {

        final EditText editText = new EditText(this);
        editText.setText(product.getName());
        new AlertDialog.Builder(this).setTitle("Edit")
                .setMessage("Edit product name")
                .setView(editText)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (TextUtils.isEmpty(editText.getText().toString())) {
                            return;
                        }
                        product.setName(editText.getText().toString());
                        mPresenter.updateProduct(product);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create()
                .show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mPresenter.searchProduct(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mPresenter.searchProduct(newText);
        return false;
    }

    @Override
    public boolean onClose() {
        return false;
    }

    private void initViews() {
        mListView = findViewById(R.id.list_product);
        mButtonAddData = findViewById(R.id.btn_insert);
        mButtonAddData.setOnClickListener(this);
        mProducts = new ArrayList<>();
        mAdapter = new MainAdapter(this, this);
        RecyclerView.LayoutManager layoutManager  = new LinearLayoutManager(this);
        mListView.setLayoutManager(layoutManager);
        mListView.setAdapter(mAdapter);
    }
}
