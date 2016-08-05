package com.abdulrahman.memenator.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.abdulrahman.memenator.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomizeMemeFragment extends Fragment {
    private static final String TAG = CustomizeMemeFragment.class.getSimpleName();
    public static final String OUTPUT_FOLDER = "saveimage";

    //faruk : initialize views
    private EditText mEtTopText;
    private EditText mEtBottomText;
    private SeekBar mSBTop;
    private SeekBar mSBBottom;
    private Button mBtnSave;
    private ImageView mIvMemeImage;

    private String mImageUrl = "https://cdn.meme.am/images/1232401.jpg";

    //faruk : bitmap for original resource
    private Bitmap mBitmapOriginal;
    private Bitmap mNewBitmap;

    //faruk : variables for saving file
    private File mFile;
    private String mPathImage;

    //faruk : newInstance with url as parameter
    public static CustomizeMemeFragment newInstance(String imageUrl) {

        Bundle args = new Bundle();

        CustomizeMemeFragment fragment = new CustomizeMemeFragment();
        fragment.setArguments(args);
        fragment.setmImageUrl(imageUrl);
        return fragment;
    }

    public CustomizeMemeFragment() {
        // Required empty public constructor
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_customize_meme, container, false);
        initUI(rootView);
        return rootView;
    }

    //faruk : initialization ui here
    private void initUI(View v) {
        mEtTopText = (EditText) v.findViewById(R.id.et_input_toptext);
        mEtBottomText = (EditText) v.findViewById(R.id.et_input_bottomtext);
        TextWatcher newTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //do nothing
            }

            @Override
            public void afterTextChanged(Editable editable) {
                reloadImage();
            }
        };
        mEtTopText.addTextChangedListener(newTextWatcher);
        mEtBottomText.addTextChangedListener(newTextWatcher);

        //faruk : seekbar for textSize
        mSBTop = (SeekBar) v.findViewById(R.id.sb_top);
        mSBBottom = (SeekBar) v.findViewById(R.id.sb_bottom);
        SeekBar.OnSeekBarChangeListener newSBListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //do nothing
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                reloadImage();
            }
        };
        mSBTop.setOnSeekBarChangeListener(newSBListener);
        mSBBottom.setOnSeekBarChangeListener(newSBListener);

        //faruk : saving file preparations
        mFile = new File(Environment.getExternalStorageDirectory() + File.separator + OUTPUT_FOLDER);
        if(!mFile.exists())
            mFile.mkdirs();

        //faruk : saving file when button clicked
        mBtnSave = (Button) v.findViewById(R.id.btn_save);
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //faruk : filename generated automatically based on timestamp
                String path = mFile.getAbsolutePath() + File.separator + System.currentTimeMillis()
                        + "_image" + ".jpg";
                mPathImage = path;

                //faruk : image format still jpeg
                //TODO : get image format from user input
                saveBitmapToFile(mNewBitmap, path, 100, Bitmap.CompressFormat.JPEG);
                Toast.makeText(getActivity(), "Image Saved!! in "+path, Toast.LENGTH_SHORT).show();

                //TODO : upload image to firebase storage

                //TODO : save meme data to firebase realtime database
            }
        });

        mIvMemeImage = (ImageView) v.findViewById(R.id.iv_meme_img);

        //faruk : convert url to bitmap
        Glide.with(getActivity())
                .load(mImageUrl)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(200, 200) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        mBitmapOriginal = resource;
                        reloadImage();
                        Log.d(TAG, "faruk: ---- FINISH LOADING IMAGE ----");
                    }
                });

    }

    /*
        faruk : this method write the bitmap with text from user input
     */
    private void reloadImage() {
        int topTextSize = mSBTop.getProgress();
        int bottomTextSize = mSBBottom.getProgress();
        mNewBitmap = mBitmapOriginal.copy(mBitmapOriginal.getConfig(), true);
        Canvas canvas = new Canvas(mNewBitmap);

        //faruk : font family
        Typeface impactTF = Typeface.createFromAsset(getContext().getAssets(), "fonts/impact.ttf");

        Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setARGB(255, 0, 0, 0);
        strokePaint.setTextAlign(Paint.Align.CENTER);

        strokePaint.setTypeface(impactTF);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(1);

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setARGB(255, 255, 255, 255);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(impactTF);
        textPaint.setStyle(Paint.Style.FILL);

        strokePaint.setTextSize(topTextSize);
        canvas.drawText(mEtTopText.getText().toString(), mNewBitmap.getWidth() / 2,
                (int) (1.5 * topTextSize), strokePaint);
        textPaint.setTextSize(topTextSize);
        canvas.drawText(mEtTopText.getText().toString(), mNewBitmap.getWidth() / 2,
                (int) (1.5 * topTextSize), textPaint);

        strokePaint.setTextSize(bottomTextSize);
        canvas.drawText(mEtBottomText.getText().toString(), mNewBitmap.getWidth() / 2,
                mNewBitmap.getHeight() - (int) (1.5 * bottomTextSize), strokePaint);
        textPaint.setTextSize(bottomTextSize);
        canvas.drawText(mEtBottomText.getText().toString(), mNewBitmap.getWidth() / 2,
                mNewBitmap.getHeight() - (int) (1.5 * bottomTextSize), textPaint);

        mIvMemeImage.setImageBitmap(mNewBitmap);

    }

    /*
        faruk : this method save the output image to file
     */
    public static String saveBitmapToFile(Bitmap bitmap, String path, int quality,
                                          Bitmap.CompressFormat imageFormat) {
        File imageFile = new File(path);
        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(imageFormat, quality, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e("BitmapToTempFile", "Error writing bitmap", e);
        }
        return imageFile.getAbsolutePath();
    }
}
