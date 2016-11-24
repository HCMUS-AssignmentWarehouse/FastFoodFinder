package com.example.mypc.fastfoodfinder.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mypc.fastfoodfinder.R;
import com.example.mypc.fastfoodfinder.adapter.StoreDetailAdapter;
import com.example.mypc.fastfoodfinder.model.Store;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.mypc.fastfoodfinder.R.id.map;

/**
 * Created by taq on 18/11/2016.
 */

public class StoreDetailActivity extends AppCompatActivity {

    private static final String STORE = "store";

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.backdrop)
    ImageView ivBackdrop;

    @BindView(R.id.content)
    RecyclerView rvContent;

    private SupportMapFragment mMapFragment;
    private GoogleMap mGoogleMap;
    private StoreDetailAdapter mStoreDetailAdapter;

    public static Intent getIntent(Context context, Store store) {
        Intent intent = new Intent(context, StoreDetailActivity.class);
        intent.putExtra(STORE, store);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);
        ButterKnife.bind(this);

        Store store = (Store) getIntent().getSerializableExtra(STORE);
        mStoreDetailAdapter = new StoreDetailAdapter(store);
        rvContent.setAdapter(mStoreDetailAdapter);
        rvContent.setLayoutManager(new LinearLayoutManager(this));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String storeName = "Vin Mart";
        collapsingToolbar.setTitle(storeName);

        Glide.with(this)
                .load(R.drawable.circle_k_cover)
                .centerCrop()
                .into(ivBackdrop);

        //setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mMapFragment == null) {
            mMapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(map));
            mMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap map) {
                    mGoogleMap = map;
                    Marker m = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(40, -4)));
                }
            });
        }
    }
}
