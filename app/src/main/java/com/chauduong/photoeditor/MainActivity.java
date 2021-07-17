package com.chauduong.photoeditor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.chauduong.photoeditor.Adapter.ViewPagerAdapter;
import com.chauduong.photoeditor.Fragment.ToneFragment;
import com.chauduong.photoeditor.Interface.ActionbarListener;
import com.chauduong.photoeditor.Interface.EditImageFragmentListener;
import com.chauduong.photoeditor.Interface.FiltersListFragmentListener;
import com.chauduong.photoeditor.Interface.ImageManagerListener;
import com.chauduong.photoeditor.Interface.MainActivityListener;
import com.chauduong.photoeditor.Manager.ActionbarManager;
import com.chauduong.photoeditor.Manager.DialogManager;
import com.chauduong.photoeditor.Manager.EditorManager;
import com.chauduong.photoeditor.Manager.ImageManager;
import com.chauduong.photoeditor.Manager.PreviewManager;
import com.chauduong.photoeditor.Utils.BitmapUtils;
import com.chauduong.photoeditor.Fragment.FiltersListFragment;
import com.chauduong.photoeditor.Utils.Utils;
import com.google.android.material.tabs.TabLayout;
import com.zomato.photofilters.imageprocessors.Filter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements FiltersListFragmentListener, EditImageFragmentListener, ImageManagerListener, ActionbarListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final int SELECT_GALLERY_IMAGE = 101;
    public static final int SCALE = 10;
    public static final int BEHAVIOR_RESET = 100;

    @BindView(R.id.image_preview)
    ImageView imagePreview;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;


    FiltersListFragment filtersListFragment;
    ToneFragment editImageFragment;
    ImageManager mImageManager;
    DialogManager mDialogManager;
    PreviewManager mPreviewManager;
    ActionbarManager mActionbarManager;
    Bitmap mPreviewImage;
    List<MainActivityListener> mainActivityListeners;


    // load native image filters library
    static {
        System.loadLibrary("NativeImageProcessor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupViewPager(viewPager);
        initManager();
    }

    private void initManager() {
        mActionbarManager = new ActionbarManager(this);
        mActionbarManager.setmActionbarListener(this);
        mDialogManager = new DialogManager(this);
        mImageManager = new ImageManager(mDialogManager, this);
        mImageManager.setmImageManagerListener(this);
        mPreviewManager = new PreviewManager(this, imagePreview);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // adding filter list fragment
        filtersListFragment = new FiltersListFragment();
        filtersListFragment.setmFiltersListFragmentListener(this);

        // adding edit image fragment
        editImageFragment = new ToneFragment(this);
        editImageFragment.setListener(this);

        adapter.addFragment(filtersListFragment, getString(R.string.tab_filters));
        adapter.addFragment(editImageFragment, getString(R.string.tab_edit));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        addObserver(filtersListFragment);
        addObserver(editImageFragment);
    }

    @Override
    public void onFilterSelected(Filter filter) {
        // applying the selected filter
        EditorManager.setFilter(filter);
        mImageManager.applyBitmap();
    }


    @Override
    public void onToneProgressChanged() {
        if (mImageManager.getFilePath() == null) return;
        mImageManager.applyBitmap();
    }


    /**
     * Resets image edit controls to normal when new filter
     * is selected
     */
    private void resetControls() {
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECT_GALLERY_IMAGE) {
            mImageManager.setFilePath(data.getData());
            Bitmap bitmap = BitmapUtils.getBitmapFromGallery(this, data.getData(), SCALE);
            mImageManager.setmOriginal(bitmap.copy(Bitmap.Config.ARGB_8888, true));
            if (bitmap != null) {
                mPreviewImage = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                mPreviewManager.setImage(mPreviewImage);
                bitmap.recycle();
            }
            // render selected image thumbnails
            filtersListFragment.prepareThumbnail(mPreviewImage);
        }
    }




    /*
     * saves image to camera gallery
     * */

    // opening image in default image viewer app
    private void openImage(String path) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(path), "image/*");
        startActivity(intent);
    }


    @Override
    public void onDoneApply() {
        mPreviewManager.setImage(mImageManager.getPreView());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActionbarManager.enableSave(EditorManager.isHasChange());
                mActionbarManager.enabaleReset(EditorManager.isHasChange());
            }
        });

    }

    @Override
    public void onActionbarClick(View view) {
        switch (view.getId()) {
            case R.id.txtSave:
                mImageManager.saveImageToGallery();
                break;
            case R.id.txtReset:
                EditorManager.resetAll();
                notifyObserver(BEHAVIOR_RESET);
                mImageManager.applyBitmap();
                break;
            case R.id.imgOpen:
                mImageManager.openImageFromGallery();
                break;
        }
    }

    public void addObserver(MainActivityListener listener) {
        if (mainActivityListeners == null)
            mainActivityListeners = new ArrayList<>();
        mainActivityListeners.add(listener);
    }

    public void notifyObserver(int behavior) {
        for (MainActivityListener listener : mainActivityListeners) {
            if (behavior == BEHAVIOR_RESET)
                listener.onResetClick();
        }
    }
}