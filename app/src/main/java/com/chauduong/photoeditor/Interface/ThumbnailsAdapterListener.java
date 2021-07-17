package com.chauduong.photoeditor.Interface;

import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.utils.ThumbnailItem;

public interface ThumbnailsAdapterListener {
    void onFilterSelected(Filter filter);
}