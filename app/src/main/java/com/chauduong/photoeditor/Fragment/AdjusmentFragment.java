package com.chauduong.photoeditor.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chauduong.photoeditor.Interface.AdjusmentListener;
import com.chauduong.photoeditor.Interface.MainActivityListener;
import com.chauduong.photoeditor.Manager.EditorManager;
import com.chauduong.photoeditor.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdjusmentFragment extends FragmentCustom implements View.OnClickListener, MainActivityListener {
    Context mContext;
    @BindView(R.id.imbRotate)
    ImageButton imbRotate;
    @BindView(R.id.imbFlipX)
    ImageButton imbFlipX;
    @BindView(R.id.imbFlipY)
    ImageButton imbFlipY;
    AdjusmentListener mAdjusmentListener;

    public void setmAdjusmentListener(AdjusmentListener mAdjusmentListener) {
        this.mAdjusmentListener = mAdjusmentListener;
    }

    public AdjusmentFragment() {
    }

    public AdjusmentFragment(Context mContext, int resource) {
        this.iconDrawable = resource;
        this.mContext = mContext;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adjusment_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imbRotate.setOnClickListener(this);
        imbFlipX.setOnClickListener(this);
        imbFlipY.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imbRotate:
                int currentDregress = EditorManager.getDegress();
                currentDregress += 90;
                if (currentDregress == 360)
                    currentDregress = 0;
                mAdjusmentListener.onRotateClick(currentDregress);
                break;
            case R.id.imbFlipX:
                boolean current = EditorManager.isIsFilpX();
                if (current == false)
                    current = true;
                else
                    current = false;
                mAdjusmentListener.onFlipX(current);
                break;
            case R.id.imbFlipY:
                boolean currentY = EditorManager.isIsFlipY();
                if (currentY == false)
                    currentY = true;
                else
                    currentY = false;
                mAdjusmentListener.onFlipY(currentY);
                break;
        }
    }

    @Override
    public void onResetClick() {

    }
}
