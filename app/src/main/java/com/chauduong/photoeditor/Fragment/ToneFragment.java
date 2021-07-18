package com.chauduong.photoeditor.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chauduong.photoeditor.Adapter.ToneApdater;
import com.chauduong.photoeditor.Interface.ActionbarListener;
import com.chauduong.photoeditor.Interface.EditImageFragmentListener;
import com.chauduong.photoeditor.Interface.MainActivityListener;
import com.chauduong.photoeditor.Interface.ToneItemListener;
import com.chauduong.photoeditor.MainActivity;
import com.chauduong.photoeditor.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ToneFragment extends FragmentCustom implements ToneItemListener, MainActivityListener {
    public static final int BRIGHTNESS_STATE = 1;
    public static final int CONTRAST_STATE = 2;
    public static final int SATURATION_STATE = 3;
    private EditImageFragmentListener mEditImageFragmentListener;

    @BindView(R.id.rvTone)
    RecyclerView rvTone;
    ToneApdater mToneApdater;
    public void setListener(EditImageFragmentListener listener) {
        this.mEditImageFragmentListener = listener;
    }

    public ToneFragment(Context mContext, int resource) {
        this.iconDrawable=resource;
        // Required empty public constructor
        mToneApdater = new ToneApdater(mContext);
        mToneApdater.setmToneItemListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tone_image, container, false);

        ButterKnife.bind(this, view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvTone.setAdapter(mToneApdater);
        rvTone.setLayoutManager(linearLayoutManager);
        return view;
    }

    @Override
    public void onToneChange() {
        mEditImageFragmentListener.onToneProgressChanged();
    }

    public void onResetClick() {
        mToneApdater.updateValueTone();
        mToneApdater.notifyDataSetChanged();
    }
}