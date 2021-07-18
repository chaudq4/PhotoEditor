package com.chauduong.photoeditor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chauduong.photoeditor.Interface.ThumbnailsAdapterListener;
import com.chauduong.photoeditor.R;
import com.zomato.photofilters.utils.ThumbnailItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThumbnailsAdapter extends RecyclerView.Adapter<ThumbnailHolder> {
    private List<ThumbnailItem> thumbnailItemList;
    private ThumbnailsAdapterListener listener;
    private Context mContext;
    private int selectedIndex = 0;

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public ThumbnailsAdapter(List<ThumbnailItem> thumbnailItemList, Context mContext) {
        this.thumbnailItemList = thumbnailItemList;
        this.mContext = mContext;
    }

    public void setListener(ThumbnailsAdapterListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ThumbnailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.thumbnail_list_item, parent, false);
        return new ThumbnailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThumbnailHolder holder, int position) {
        final ThumbnailItem thumbnailItem = thumbnailItemList.get(position);

        holder.imgThumbnail.setImageBitmap(thumbnailItem.image);

        holder.imgThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFilterSelected(thumbnailItem.filter);
                selectedIndex = position;
                notifyDataSetChanged();
            }
        });

        holder.txtNameFilter.setText(thumbnailItem.filterName);

        if (selectedIndex == position) {
            holder.txtNameFilter.setTextColor(ContextCompat.getColor(mContext, R.color.filter_label_selected));
        } else {
            holder.txtNameFilter.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
        }
    }

    @Override
    public int getItemCount() {
        return thumbnailItemList.size();
    }
}

class ThumbnailHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.filter_name)
    TextView txtNameFilter;
    @BindView(R.id.thumbnail)
    ImageView imgThumbnail;

    public ThumbnailHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
