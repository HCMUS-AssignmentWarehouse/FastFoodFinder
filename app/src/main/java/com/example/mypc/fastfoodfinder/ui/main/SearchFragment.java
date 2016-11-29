package com.example.mypc.fastfoodfinder.ui.main;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.mypc.fastfoodfinder.R;
import com.example.mypc.fastfoodfinder.helper.SearchResult;
import com.example.mypc.fastfoodfinder.utils.Constant;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    @BindView(R.id.btn_search_circle_k) CircleImageView quickSearchCircleK;
    @BindView(R.id.btn_search_family_mart) CircleImageView quickSearchFamilyMart;
    @BindView(R.id.btn_search_mini_stop) CircleImageView quickSearchMiniStop;
    @BindView(R.id.btn_load_more) CircleImageView quickSearchLoadMore;
    @BindView(R.id.btn_search_bsmart) CircleImageView quickSearchBsMart;
    @BindView(R.id.btn_search_shop_n_go) CircleImageView quickSearchShopNGo;

    @BindView(R.id.cv_action_container) ViewGroup cardViewQuickSearch;
    @BindView(R.id.ll_load_more_container) ViewGroup searchMoreLayout;
    @BindView(R.id.sv_search_container) ScrollView searchContainer;
    boolean visible;
    String searchText;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, root);

        setupQuickSearchBar();

        return root;
    }

    private void setupQuickSearchBar()
    {
        quickSearchCircleK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchText = "Circle K";
                EventBus.getDefault().post(new SearchResult(SearchResult.SEARCH_QUICK, searchText, Constant.TYPE_CIRCLE_K));
                searchContainer.setVisibility(View.GONE);
            }
        });

        quickSearchFamilyMart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchText = "Family Mart";
                EventBus.getDefault().post(new SearchResult(SearchResult.SEARCH_QUICK, searchText, Constant.TYPE_FAMILY_MART));
                searchContainer.setVisibility(View.GONE);
            }
        });

        quickSearchMiniStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchText = "Mini Stop";
                EventBus.getDefault().post(new SearchResult(SearchResult.SEARCH_QUICK, searchText, Constant.TYPE_MINI_STOP));
                searchContainer.setVisibility(View.GONE);
            }
        });

        quickSearchBsMart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchText = "BsMart";
                EventBus.getDefault().post(new SearchResult(SearchResult.SEARCH_QUICK, searchText, Constant.TYPE_BSMART));
                searchContainer.setVisibility(View.GONE);
            }
        });

        quickSearchShopNGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchText = "Shop and Go";
                EventBus.getDefault().post(new SearchResult(SearchResult.SEARCH_QUICK, searchText, Constant.TYPE_SHOP_N_GO));
                searchContainer.setVisibility(View.GONE);
            }
        });

        quickSearchLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(cardViewQuickSearch);
                    visible = !visible;
                    searchMoreLayout.setVisibility(visible ? View.VISIBLE : View.GONE);
                }
            }
        });
    }
}
