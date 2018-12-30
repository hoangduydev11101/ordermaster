package com.master.ordercoffee;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.adamstyrc.cookiecutter.ImageUtils;
import com.master.ordercoffee.service.FirebaseService;
import com.master.ordercoffee.utils.Constants;
import com.master.ordercoffee.utils.Logger;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PickImageActivity extends BaseActivity {
    public enum ImageShapeType {
        CIRCLE,
        RECTANGLE,
        REC_NOT_AC
    }

    private ImageShapeType imageShapeType;
    private CropImage.ActivityResult mCropImageResult;
    private String pathForImage;

    @BindView(R.id.cropImageView)
    CropImageView mCropImageView;


    @OnClick(R.id.btn_choose)
    void onChooseClicked () {
        mCropImageView.getCroppedImageAsync();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_image);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            pathForImage = bundle.getString(Constants.Keys.kPathImageUpload);
        }

        CropImage.startPickImageActivity(this);

        mCropImageView.setOnCropImageCompleteListener((view, result) -> handleCropResult(result));

        imageShapeType = (ImageShapeType) getIntent().getExtras().get(Constants.Keys.kCaptureImageType);

        if(imageShapeType == ImageShapeType.CIRCLE) {
            mCropImageView.setCropShape(CropImageView.CropShape.OVAL);
            mCropImageView.setAspectRatio(5, 5);
        }
        else if(imageShapeType == ImageShapeType.RECTANGLE) {
            mCropImageView.setCropShape(CropImageView.CropShape.RECTANGLE);
            mCropImageView.setAspectRatio(16, 9);
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mCropImageView.rotateImage(90);
            }
        }
        else {
            mCropImageView.setCropShape(CropImageView.CropShape.RECTANGLE);
            mCropImageView.setAspectRatio(16, 10);
            mCropImageView.setFixedAspectRatio(false);
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mCropImageView.rotateImage(90);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE) {
                Uri imageUri = CropImage.getPickImageResultUri(this, data);
                mCropImageView.setImageUriAsync(imageUri);
            }
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                mCropImageResult = CropImage.getActivityResult(data);
                handleCropResult(mCropImageResult);
            }
        }
        else {
            finish();
        }
    }

    private void handleCropResult(CropImageView.CropResult result) {
        if (result.getError() == null) {
            if (result.getUri() != null) {
                Logger.logDebug("URI: "+ result.getUri());

            }
            else {

                Bitmap bitmap;
                Bitmap scaledBitmap = null;
                Point screenSize = ImageUtils.getScreenSize(this);

                if (imageShapeType == ImageShapeType.RECTANGLE) {
                    bitmap = result.getBitmap();
                    scaledBitmap = getResizedBitmap(bitmap, screenSize.x/3);}
                else if(imageShapeType == ImageShapeType.CIRCLE){
                    bitmap = CropImage.toOvalBitmap(result.getBitmap());
                    scaledBitmap = getResizedBitmap(bitmap, screenSize.x/3);
                }else {
                    bitmap = result.getBitmap();
                    scaledBitmap = getResizedBitmap(bitmap, screenSize.x/2);
                }

                uploadImageToServer(scaledBitmap);
            }
        } else {
            Log.e("AIC", "Failed to crop image", result.getError());
        }
    }
    /*
    - Upload image to server
     */
    private void uploadImageToServer (Bitmap bitmap) {
        FirebaseService.getInstance().uploadImages(bitmap, pathForImage, new FirebaseService.FirebaseStorageListener() {
            @Override
            public void onUploadSuccess(final String imageUrl) {

                Logger.logDebug("Image Url: "+imageUrl);

                Intent intent = new Intent();
                intent.putExtra(Constants.Keys.kImageUrl, imageUrl);
                setResult(RESULT_OK, intent);

                finish();

            }

            @Override
            public void onUploadImageFailed() {
//                Indicator.dismissLoading(UploadImageFakeActivity.this);
            }
        });
    }

    public Bitmap getResizedBitmap(Bitmap bm, int size) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scale = ((float) size) / width;

        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();

        // RESIZE THE BIT MAP
        matrix.postScale(scale, scale);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bm, (int) (width*scale), (int) (height*scale), false);
        bm.recycle();

        return resizedBitmap;
    }
}
