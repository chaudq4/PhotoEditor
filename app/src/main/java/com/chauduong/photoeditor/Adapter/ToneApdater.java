package com.chauduong.photoeditor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauduong.photoeditor.Interface.ToneItemListener;
import com.chauduong.photoeditor.Manager.EditorManager;
import com.chauduong.photoeditor.Model.ToneItem;
import com.chauduong.photoeditor.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.chauduong.photoeditor.Fragment.ToneFragment.BRIGHTNESS_STATE;
import static com.chauduong.photoeditor.Fragment.ToneFragment.CONTRAST_STATE;
import static com.chauduong.photoeditor.Fragment.ToneFragment.SATURATION_STATE;

public class ToneApdater extends RecyclerView.Adapter<ToneHolder> {
    Context mContext;
    List<ToneItem> toneItemList;
    ToneItemListener mToneItemListener;

    public ToneApdater(Context mContext) {
        this.mContext = mContext;
        initToneItem();
    }


    @NonNull
    @Override
    public ToneHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.tone_item, parent, false);
        return new ToneHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToneHolder holder, int position) {
        holder.imgToneItem.setImageResource(toneItemList.get(position).getDrawable());
        setMaxProgress(holder.sbToneValue, toneItemList.get(position).getState());
        holder.sbToneValue.setProgress((int) (toneItemList.get(position).getState() == 1 ? (toneItemList.get(position).getValue() + 100) : (toneItemList.get(position).getValue() * 10)));
        setTextValueInit(holder.txtToneValue, toneItemList.get(position));
        holder.sbToneValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateValueTone(progress, position);
                boolean isBrightness = toneItemList.get(position).getState() == 1;
                holder.txtToneValue.setText(String.valueOf(isBrightness ? progress - 100 : progress - 10));
                mToneItemListener.onToneChange();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void setmToneItemListener(ToneItemListener mToneItemListener) {
        this.mToneItemListener = mToneItemListener;
    }

    @Override
    public int getItemCount() {
        return toneItemList.size();
    }

    private void initToneItem() {
        toneItemList = new ArrayList<>();
        toneItemList.add(new ToneItem(1, mContext.getResources().getString(R.string.brightness), 0, R.drawable.ic_brightness));
        toneItemList.add(new ToneItem(2, mContext.getResources().getString(R.string.contrast), 1f, R.drawable.ic_contrast));
        toneItemList.add(new ToneItem(3, mContext.getResources().getString(R.string.saturation), 1f, R.drawable.ic_saturation));
    }

    public void resetAll() {
        for (ToneItem t : toneItemList) {
            if (t.getState() == BRIGHTNESS_STATE)
                t.setValue(0);
            if (t.getState() == CONTRAST_STATE)
                t.setValue(1f);
            if (t.getState() == BRIGHTNESS_STATE)
                t.setValue(1f);
        }

    }

    public void updateValueTone() {
        for (ToneItem toneItem : toneItemList) {
            if (toneItem.getState() == BRIGHTNESS_STATE)
                toneItem.setValue(EditorManager.getBrightnessFinal());
            if (toneItem.getState() == CONTRAST_STATE)
                toneItem.setValue(EditorManager.getContrastFinal());
            if (toneItem.getState() == SATURATION_STATE)
                toneItem.setValue(EditorManager.getSaturationFinal());
        }
    }

    public void updateValueTone(float value, int position) {
        ToneItem tone = toneItemList.get(position);
        switch (tone.getState()) {
            case BRIGHTNESS_STATE:
                EditorManager.setBrightnessFinal((int) (value) - 100);
                tone.setValue(value - 100);
                break;
            case CONTRAST_STATE:
                EditorManager.setContrastFinal(value / 10);
                tone.setValue(value / 10);
                break;
            case SATURATION_STATE:
                EditorManager.setSaturationFinal(value / 10);
                tone.setValue(value / 10);
                break;
        }
    }

    public ToneItem getToneItem(int currentState) {
        return toneItemList.get(currentState - 1);
    }

    private void setTextValueInit(TextView txtToneValue, ToneItem toneItem) {
        switch (toneItem.getState()) {
            case BRIGHTNESS_STATE:
                txtToneValue.setText(String.valueOf((int) (toneItem.getValue())));
                break;
            case CONTRAST_STATE:
                txtToneValue.setText(String.valueOf((int) (toneItem.getValue() * 10) - 10));
                break;
            case SATURATION_STATE:
                txtToneValue.setText(String.valueOf((int) (toneItem.getValue() * 10) - 10));
                break;
        }
    }

    public void setMaxProgress(SeekBar seekBar, int currentState) {
        switch (currentState) {
            case BRIGHTNESS_STATE:
                seekBar.setMax(200);
                seekBar.setMin(0);
                break;
            case CONTRAST_STATE:
                seekBar.setMax(30);
                seekBar.setMin(0);
                break;
            case SATURATION_STATE:
                seekBar.setMax(30);
                seekBar.setMin(0);
                break;
        }
    }
}

class ToneHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.imgToneItem)
    ImageView imgToneItem;
    @BindView(R.id.txtValueTone)
    TextView txtToneValue;
    @BindView(R.id.sbValueTone)
    SeekBar sbToneValue;

    public ToneHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}

