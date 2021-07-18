package com.chauduong.photoeditor.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chauduong.photoeditor.Adapter.ThumbnailsAdapter;
import com.chauduong.photoeditor.Interface.FiltersListFragmentListener;
import com.chauduong.photoeditor.Interface.MainActivityListener;
import com.chauduong.photoeditor.Interface.ThumbnailsAdapterListener;
import com.chauduong.photoeditor.MainActivity;
import com.chauduong.photoeditor.R;
import com.chauduong.photoeditor.Utils.BitmapUtils;
import com.chauduong.photoeditor.Utils.Utils;
import com.chauduong.photoeditor.View.SpacesItemDecoration;
import com.zomato.photofilters.FilterPack;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.utils.ThumbnailItem;
import com.zomato.photofilters.utils.ThumbnailsManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FiltersListFragment extends FragmentCustom implements ThumbnailsAdapterListener, MainActivityListener {
    static final int SIZE_THUMBNAIL = 80;
    @BindView(R.id.recycler_view)
    RecyclerView rvThumbnail;
    Context mContext;
    private ThumbnailsAdapter mThumbnailsAdapter;
    private List<ThumbnailItem> mListThumbnailItems;
    private FiltersListFragmentListener mFiltersListFragmentListener;

    public void setmFiltersListFragmentListener(FiltersListFragmentListener mFiltersListFragmentListener) {
        this.mFiltersListFragmentListener = mFiltersListFragmentListener;
    }

    public FiltersListFragment(Context mContext, int resoure) {
        this.mContext = mContext;
        this.iconDrawable = resoure;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filters_list, container, false);
        ButterKnife.bind(this, view);
        initView();
        Log.i("chau", "onCreateView: null");
        return view;
    }

    private void initView() {
        if (mListThumbnailItems == null)
            mListThumbnailItems = new ArrayList<>();
        if (mThumbnailsAdapter == null)
            mThumbnailsAdapter = new ThumbnailsAdapter(mListThumbnailItems, getContext());
        mThumbnailsAdapter.setListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvThumbnail.setLayoutManager(mLayoutManager);
        rvThumbnail.setItemAnimator(new DefaultItemAnimator());
        int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
        rvThumbnail.addItemDecoration(new SpacesItemDecoration(space));
        rvThumbnail.setAdapter(mThumbnailsAdapter);
    }

    public void prepareThumbnail(final Bitmap bitmap) {
        Runnable r = new Runnable() {
            public void run() {
                if (bitmap == null) return;
                Bitmap thumbImage = Bitmap.createScaledBitmap(bitmap, SIZE_THUMBNAIL, SIZE_THUMBNAIL, false);
                if (thumbImage == null)
                    return;

                ThumbnailsManager.clearThumbs();
                mListThumbnailItems.clear();

                // add normal bitmap first
                ThumbnailItem thumbnailItem = new ThumbnailItem();
                thumbnailItem.image = thumbImage;
                thumbnailItem.filterName = getString(R.string.filter_normal);
                ThumbnailsManager.addThumb(thumbnailItem);

                List<Filter> filters = FilterPack.getFilterPack(getActivity());

                for (Filter filter : filters) {
                    ThumbnailItem tI = new ThumbnailItem();
                    tI.image = thumbImage;
                    tI.filter = filter;
                    tI.filterName = filter.getName();
                    ThumbnailsManager.addThumb(tI);
                }

                mListThumbnailItems.addAll(ThumbnailsManager.processThumbs(getActivity()));

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mThumbnailsAdapter.notifyDataSetChanged();
                    }
                });
            }
        };

        new Thread(r).start();
    }

    @Override
    public void onFilterSelected(Filter filter) {
        if (mFiltersListFragmentListener != null)
            mFiltersListFragmentListener.onFilterSelected(filter);
    }

    @Override
    public void onResetClick() {
        rvThumbnail.scrollToPosition(0);
        mThumbnailsAdapter.setSelectedIndex(0);
        mThumbnailsAdapter.notifyDataSetChanged();
        onFilterSelected(new Filter());
    }
}
