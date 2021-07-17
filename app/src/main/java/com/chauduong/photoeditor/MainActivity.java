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
import android.widget.ImageView;

import com.chauduong.photoeditor.Adapter.ViewPagerAdapter;
import com.chauduong.photoeditor.Interface.FiltersListFragmentListener;
import com.chauduong.photoeditor.Manager.DialogManager;
import com.chauduong.photoeditor.Manager.Editor;
import com.chauduong.photoeditor.Manager.ImageManager;
import com.chauduong.photoeditor.Manager.PreviewManager;
import com.chauduong.photoeditor.Utils.BitmapUtils;
import com.chauduong.photoeditor.View.EditImageFragment;
import com.chauduong.photoeditor.View.FiltersListFragment;
import com.google.android.material.tabs.TabLayout;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements FiltersListFragmentListener, EditImageFragment.EditImageFragmentListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final int SELECT_GALLERY_IMAGE = 101;
    public static final int SCALE = 10;

    @BindView(R.id.image_preview)
    ImageView imagePreview;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    FiltersListFragment filtersListFragment;
    EditImageFragment editImageFragment;
    ImageManager mImageManager;
    DialogManager mDialogManager;
    PreviewManager mPreviewManager;
    Bitmap mPreviewImage;


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
        mDialogManager = new DialogManager(this);
        mImageManager = new ImageManager(mDialogManager, this);
        mPreviewManager = new PreviewManager(this, imagePreview);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // adding filter list fragment
        filtersListFragment = new FiltersListFragment();
        filtersListFragment.setmFiltersListFragmentListener(this);

        // adding edit image fragment
        editImageFragment = new EditImageFragment();
        editImageFragment.setListener(this);

        adapter.addFragment(filtersListFragment, getString(R.string.tab_filters));
        adapter.addFragment(editImageFragment, getString(R.string.tab_edit));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onFilterSelected(Filter filter) {
        // reset image controls
        resetControls();
        // applying the selected filter
        Editor.setFilter(filter);
        mPreviewImage = mPreviewImage.copy(Bitmap.Config.ARGB_8888, true);
        // preview filtered image
        mPreviewManager.setImage(filter.processFilter(mPreviewImage));

//        finalImage = filteredImage.copy(Bitmap.Config.ARGB_8888, true);
    }

    @Override
    public void onBrightnessChanged(final int brightness) {
        Editor.setBrightnessFinal(brightness);
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new BrightnessSubFilter(brightness));
        mPreviewImage = myFilter.processFilter(mPreviewImage.copy(Bitmap.Config.ARGB_8888, true));
        mPreviewManager.setImage(mPreviewImage);
    }

    @Override
    public void onSaturationChanged(final float saturation) {
        Editor.setSaturationFinal(saturation);
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new SaturationSubfilter(saturation));
        mPreviewImage = myFilter.processFilter(mPreviewImage.copy(Bitmap.Config.ARGB_8888, true));
        mPreviewManager.setImage(mPreviewImage);
    }

    @Override
    public void onContrastChanged(final float contrast) {
        Editor.setContrastFinal(contrast);
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new ContrastSubFilter(contrast));
        mPreviewImage = myFilter.processFilter(mPreviewImage.copy(Bitmap.Config.ARGB_8888, true));
        mPreviewManager.setImage(mPreviewImage);
    }

    @Override
    public void onEditStarted() {

    }

    @Override
    public void onEditCompleted() {
        // once the editing is done i.e seekbar is drag is completed,
        // apply the values on to filtered image
//        Log.i(TAG, "onEditCompleted: ");
//        final Bitmap bitmap = filteredImage.copy(Bitmap.Config.ARGB_8888, true);
//

//        Filter myFilter = new Filter();
//        myFilter.addSubFilter(new BrightnessSubFilter(Editor.getBrightnessFinal()));
//        myFilter.addSubFilter(new ContrastSubFilter(Editor.getContrastFinal()));
//        myFilter.addSubFilter(new SaturationSubfilter(Editor.getSaturationFinal()));
//        finalImage = myFilter.processFilter(originalImage);
    }

    /**
     * Resets image edit controls to normal when new filter
     * is selected
     */
    private void resetControls() {
        if (editImageFragment != null) {
            editImageFragment.resetControls();
        }
       Editor.resetAll();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_open) {
            mImageManager.openImageFromGallery();
            return true;
        }

        if (id == R.id.action_save) {
            mImageManager.saveImageToGallery();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECT_GALLERY_IMAGE) {
            mImageManager.setFilePath(data.getData());
            Bitmap bitmap = BitmapUtils.getBitmapFromGallery(this, data.getData(), SCALE);

            if (bitmap != null) {
                mPreviewImage =bitmap.copy(Bitmap.Config.ARGB_8888, true);
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


}