package com.faceoffaerie.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.Face;
import android.hardware.Camera.Parameters;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.faceoffaerie.R;
import com.faceoffaerie.contants.Constants;
import com.faceoffaerie.contants.PlistInfo;
import com.faceoffaerie.db.Dao;
import com.faceoffaerie.interfaces.BackgroundMusicModel;
import com.faceoffaerie.objects.SquareCameraPreview;
import com.faceoffaerie.parser.ParsePlistParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FaeryCameraActivity extends BaseActivity implements OnClickListener, SurfaceHolder.Callback, Camera.PictureCallback, Camera.FaceDetectionListener {

    @InjectView(R.id.homeButton) Button homeButton;
    @InjectView(R.id.autoImageView) ImageView autoImageView;
    @InjectView(R.id.takeImageView) ImageView takeImageView;
    @InjectView(R.id.switchImageview) ImageView switchImageview;
    @InjectView(R.id.cameraSurfaceView) SquareCameraPreview mPreviewView;
    @InjectView(R.id.takeRelativeLayout) RelativeLayout takeRelativeLayout;
    @InjectView(R.id.autoRelativeLayout) RelativeLayout autoRelativeLayout;
    @InjectView(R.id.switchRelativeLayout) RelativeLayout switchRelativeLayout;
    @InjectView(R.id.buttonsLinearLayout) LinearLayout buttonsLinearLayout;
    @InjectView(R.id.retakeButton) Button retakeButton;
    @InjectView(R.id.connectButton) Button connectButton;
    @InjectView(R.id.faerieRelativeLayout) RelativeLayout faerieRelativeLayout;
    @InjectView(R.id.faerieImageView) ImageView faerieImageView;
    @InjectView(R.id.faerieNameTextView) TextView faerieNameTextView;
    @InjectView(R.id.faerieReadingTextView) TextView faerieReadingTextView;
    @InjectView(R.id.menuLinearLayout) LinearLayout menuLinearLayout;
    @InjectView(R.id.facebookMenuImageView) ImageView facebookMenuImageView;
    @InjectView(R.id.twitterMenuImageView) ImageView twitterMenuImageView;
    @InjectView(R.id.emailMenuImageView) ImageView emailMenuImageView;
    @InjectView(R.id.messageMenuImageView) ImageView messageMenuImageView;
    @InjectView(R.id.cancelMenuImageView) ImageView cancelMenuImageView;
    @InjectView(R.id.retakeMenuImageView) ImageView retakeMenuImageView;
    @InjectView(R.id.saveMenuImageView) ImageView saveMenuImageView;
    @InjectView(R.id.homeMenuImageView) ImageView homeMenuImageView;
    @InjectView(R.id.infoMenuImageView) ImageView infoMenuImageView;

    private int mCameraID;
    private String mFlashMode;
    private Camera mCamera;
    private SurfaceHolder mSurfaceHolder;
    private Face recognizedFace = new Face();
    private ArrayList<PlistInfo> youChooseList;
    private int selectedIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setLayoutId(this, R.layout.activity_faery_camera);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initView();
        setListener();
        initData();

    }

    public void initView() {
        ButterKnife.inject(this);
        faerieNameTextView.setVisibility(View.GONE);
        faerieReadingTextView.setVisibility(View.GONE);
    }

    public void setListener() {

    }

    public void initData() {
        mCameraID = getIntent().getIntExtra("direction", -1);
        if (mCameraID == -1)
            mCameraID = getFrontCameraID();
        mFlashMode = "auto";
        mPreviewView.getHolder().addCallback(this);

        String xml = Constants.readYouChooseFromAssetsPlist(this);
        ParsePlistParser pp = new ParsePlistParser();
        youChooseList = pp.parsePlist(xml);


    }
    @Override
    public void onFaceDetection(Face[] faces, Camera camera) {
        if (faces != null && faces.length > 0) {
            Face face = faces[0];
            Log.e("score", face.score + "");
            if (face.score > recognizedFace.score) {
                recognizedFace = face;
            } else if (face.score < 300) {
                recognizedFace = new Face();
            }
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        stopCameraPreview();
    }
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.homeButton: {
                finish();
            }
            break;
            case R.id.autoImageView: {
                if (mFlashMode.equalsIgnoreCase("auto")) {
                    mFlashMode = "on";
                    autoImageView.setImageResource(R.drawable.cameraflashon);
                } else if (mFlashMode.equalsIgnoreCase("on")) {
                    mFlashMode = "off";
                    autoImageView.setImageResource(R.drawable.cameraflashoff);
                } else if (mFlashMode.equalsIgnoreCase("off")) {
                    mFlashMode = "auto";
                    autoImageView.setImageResource(R.drawable.cameraflashauto);
                }
                setupCamera();
            }
            break;
            case R.id.takeImageView: {
                takePicture();
            }
            break;
            case R.id.switchImageview: {
                if (mCameraID == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    mCameraID = getBackCameraID();
                } else {
                    mCameraID = getFrontCameraID();
                }
                restartPreview();
            }
            break;
            case R.id.retakeButton: {
                restartPreview();
            }
            break;
            case R.id.connectButton: {
                connectFunc();
            }
            break;
            case R.id.faerieImageView: {
                if (menuLinearLayout.getVisibility() == View.VISIBLE) {
                    hideMenuLayout();
                } else {
                    showMenuLayout();
                }
            }
            break;
            case R.id.cancelMenuImageView: {
                hideMenuLayout();
            }
            break;
            case R.id.homeMenuImageView: {
                finish();
            }
            break;
            case R.id.infoMenuImageView: {
                Intent intent = new Intent(this, InfoActivity.class);
                intent.putExtra("fromFaeryChoose", true);
                startActivity(intent);
                hideMenuLayout();
            }
            break;
            case R.id.retakeMenuImageView: {
                selectedIndex = 0;
                BitmapDrawable d = (BitmapDrawable) faerieImageView.getDrawable();
                if (d != null && d.getBitmap() != null)
                    d.getBitmap().recycle();
                faerieImageView.setImageBitmap(null);
                faerieRelativeLayout.setVisibility(View.GONE);
                hideMenuLayout();
                restartPreview();
            }
            break;
            case R.id.saveMenuImageView: {
                int result = saveFaeryToDB(youChooseList.get(selectedIndex), 0);
                if (result == 0) {
                    Constants.showMessage(FaeryCameraActivity.this, String.format("\"%s\"\nTo your saved faeries", youChooseList.get(selectedIndex).name));
                } else if (result == 1) {
                    new AlertDialog.Builder(new ContextThemeWrapper(FaeryCameraActivity.this, android.R.style.Theme_Holo_Light))
                            .setTitle("Faery Saved")
                            .setMessage(String.format("You have already saved the faery \"%s\" for this reading.\nAre you sure you want to save it again?", youChooseList.get(selectedIndex).name))
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    saveFaeryToDB(youChooseList.get(selectedIndex), 1);
                                    Constants.showMessage(FaeryCameraActivity.this, String.format("\"%s\"\nTo your saved faeries", youChooseList.get(selectedIndex).name));
                                    hideMenuLayout();
                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                hideMenuLayout();
            }
            break;
            case R.id.facebookMenuImageView: {
                String text = String.format("This is the message the faery \"%s\" has for me.\n %s\n\n You can get the new World of Froud app at:\n https://itunes.apple.com/us/app/faces-of-faerie/id874271272?ls=1&mt=8", youChooseList.get(selectedIndex).name, youChooseList.get(selectedIndex).reading);
                shareToFacebook(FaeryCameraActivity.this, text, Uri.parse(prepareSdcardImage()));
                hideMenuLayout();
            }
            break;
            case R.id.twitterMenuImageView: {
                String text = "Faces of Faerie!\n You can get the new World of Froud app at:\n https://itunes.apple.com/us/app/faces-of-faerie/id874271272?ls=1&mt=8\"";
                shareToTwitter(FaeryCameraActivity.this, text, Uri.parse(prepareSdcardImage()));
                hideMenuLayout();
            }
            break;
            case R.id.emailMenuImageView: {
                String subject = String.format("Check out my Faery for Today: %s", youChooseList.get(selectedIndex).name);
                String text = String.format("This is the message the faery \"%s\" has for me.\n %s\n\n You can get the new World of Froud app at:\n https://itunes.apple.com/us/app/faces-of-faerie/id874271272?ls=1&mt=8", youChooseList.get(selectedIndex).name, youChooseList.get(selectedIndex).reading);
                shareToEmail(FaeryCameraActivity.this, subject, text, Uri.parse(prepareSdcardImage()));
                hideMenuLayout();
            }
            break;
            case R.id.messageMenuImageView: {
                String text = String.format("This is the message the faery \"%s\" has for me.\n %s\n\n You can get the new World of Froud app at:\n https://itunes.apple.com/us/app/faces-of-faerie/id874271272?ls=1&mt=8", youChooseList.get(selectedIndex).name, youChooseList.get(selectedIndex).reading);
                shareToSMS(FaeryCameraActivity.this, text, Uri.parse(prepareSdcardImage()));
                hideMenuLayout();
            }

        }
    }
    private String prepareSdcardImage() {
        String faeriePath = String.format("faerie%d.png", selectedIndex);
        return "file://" + copyAssets(faeriePath);
    }
    public int saveFaeryToDB(PlistInfo info, int force) {
        Dao dao = new Dao(FaeryCameraActivity.this);
        dao.open();
        int result = dao.addFavourFunc(info, force);
        dao.close();
        return result;
    }
    public void showFaerieChoose(int index) {
        BackgroundMusicModel.getInstance().changeState(true);

        final PlistInfo selectedInfo = youChooseList.get(index);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "BarbedorSCTMed.ttf");
        faerieNameTextView.setTypeface(typeface);
        faerieReadingTextView.setTypeface(typeface);

        faerieNameTextView.setText(selectedInfo.name);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setStartOffset(3000);
        alphaAnimation.setDuration(1000);
        faerieNameTextView.startAnimation(alphaAnimation);
        faerieNameTextView.setVisibility(View.VISIBLE);

        faerieReadingTextView.setText(selectedInfo.reading);
        faerieReadingTextView.setTextSize(30);
        alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setStartOffset(1000);
        alphaAnimation.setDuration(3000);
        faerieReadingTextView.startAnimation(alphaAnimation);
        faerieReadingTextView.setVisibility(View.VISIBLE);

        InputStream ims = null;
        try {
            ims = getAssets().open(String.format("faerie%d.png", index));
            Drawable d = Drawable.createFromStream(ims, null);
            faerieImageView.setImageDrawable(d);

        } catch (Exception e) {}
        finally {
            if (ims != null) {
                try {
                    ims.close();
                } catch (IOException e) {
                    // NOOP
                }
            }
        }
        alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                String fileName = selectedInfo.name;
                if (fileName.contains(" "))
                    fileName = fileName.replaceAll(" ", "_");
                if (fileName.contains("'"))
                    fileName = fileName.replaceAll("'", "");
                fileName = fileName.toLowerCase();
                Constants.playAudio(FaeryCameraActivity.this, getResources().getIdentifier(fileName, "raw", getPackageName()));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        faerieRelativeLayout.startAnimation(alphaAnimation);
        faerieRelativeLayout.setVisibility(View.VISIBLE);
    }
    public void connectFunc() {
        Random random = new Random(System.currentTimeMillis());
        selectedIndex = random.nextInt(youChooseList.size());
        if (selectedIndex == 0)
            selectedIndex = youChooseList.size();
        showFaerieChoose(selectedIndex);
    }
    public void processPhoto() {
        if (recognizedFace == null || recognizedFace.score == 0) {
            Constants.showMessage(this, "Oops!", "The faeries need more light to see you!\nPlease retake your picture");
            restartPreview();
            return;
        }
        if (recognizedFace.score < 400) {
            Constants.showMessage(this, "Oops!", "I'm sorry we did not see you!\nPlease line up your eyes within the eye guides.\nPlease retake your picture");
            restartPreview();
            return;
        }

        switchRelativeLayout.setVisibility(View.GONE);
        autoRelativeLayout.setVisibility(View.GONE);
        takeRelativeLayout.setVisibility(View.GONE);
        buttonsLinearLayout.setVisibility(View.VISIBLE);
    }
    private void getCamera(int cameraID) {
        Log.d("TAG", "get camera with id " + cameraID);
        while( mCamera == null) {
            try {
                mCamera = Camera.open(cameraID);
                mPreviewView.setCamera(mCamera);
            } catch (Exception e) {
                Log.d("TAG", "Can't open camera with id " + cameraID);
                e.printStackTrace();
            }
            try {
                Thread.sleep(100);
            } catch (Exception e) {}

        }
    }

    /**
     * Start the camera preview
     */
    private void startCameraPreview() {
        determineDisplayOrientation();
        setupCamera();

        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();
            mCamera.startFaceDetection();
            mCamera.setFaceDetectionListener(this);
        } catch (IOException e) {
            Log.d("TAG", "Can't start camera preview due to IOException " + e);
            e.printStackTrace();
        }
    }

    /**
     * Stop the camera preview
     */
    private void stopCameraPreview() {
        // Nulls out callbacks, stops face detection
        if (mCamera != null) {
            mCamera.stopFaceDetection();
            mCamera.stopPreview();
            if (mPreviewView != null)
                mPreviewView.setCamera(null);
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     * Determine the current display orientation and rotate the camera preview
     * accordingly
     */
    private void determineDisplayOrientation() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(mCameraID, cameraInfo);

        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;

        switch (rotation) {
            case Surface.ROTATION_0: {
                degrees = 0;
                break;
            }
            case Surface.ROTATION_90: {
                degrees = 90;
                break;
            }
            case Surface.ROTATION_180: {
                degrees = 180;
                break;
            }
            case Surface.ROTATION_270: {
                degrees = 270;
                break;
            }
        }

        int displayOrientation;

        // Camera direction
        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            // Orientation is angle of rotation when facing the camera for
            // the camera image to match the natural orientation of the device
            displayOrientation = (cameraInfo.orientation + degrees) % 360;
            displayOrientation = (360 - displayOrientation) % 360;
        } else {
            displayOrientation = (cameraInfo.orientation - degrees + 360) % 360;
        }

        mCamera.setDisplayOrientation(displayOrientation);
    }

    /**
     * Setup the camera parameters
     */
    private void setupCamera() {
        // Never keep a global parameters
        Camera.Parameters parameters = mCamera.getParameters();

        Camera.Size bestPreviewSize = determineBestPreviewSize(parameters);
        Camera.Size bestPictureSize = determineBestPictureSize(parameters);

        parameters.setPreviewSize(bestPreviewSize.width, bestPreviewSize.height);
        parameters.setPictureSize(bestPictureSize.width, bestPictureSize.height);


        // Set continuous picture focus, if it's supported
        if (parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }
        parameters.setJpegQuality(100);
        final View changeCameraFlashModeBtn = findViewById(R.id.autoRelativeLayout);
        List<String> flashModes = parameters.getSupportedFlashModes();
        if (flashModes != null && flashModes.contains(mFlashMode)) {
            parameters.setFlashMode(mFlashMode);
            changeCameraFlashModeBtn.setVisibility(View.VISIBLE);
        } else {
            changeCameraFlashModeBtn.setVisibility(View.INVISIBLE);
        }

        // Lock in the changes
        mCamera.setParameters(parameters);
    }

    private Camera.Size determineBestPreviewSize(Camera.Parameters parameters) {
        return determineBestSize(parameters.getSupportedPreviewSizes());
    }

    private Camera.Size determineBestPictureSize(Camera.Parameters parameters) {
        return determineBestSize(parameters.getSupportedPictureSizes());
    }

    private Camera.Size determineBestSize(List<Camera.Size> sizes) {
        Camera.Size bestSize = null;
        Camera.Size size;
        int numOfSizes = sizes.size();
        for (int i = 0; i < numOfSizes; i++) {
            size = sizes.get(i);
            boolean isDesireRatio = (size.width / 4) == (size.height / 3);
            boolean isBetterSize = (bestSize == null) || size.width > bestSize.width;

            if (isDesireRatio && isBetterSize) {
                bestSize = size;
            }
        }
        if (bestSize == null) {
            Log.d("TAG", "cannot find the best camera size");
            return sizes.get(numOfSizes - 1);
        }

        return bestSize;
    }

    private void restartPreview() {
        recognizedFace = new Face();
        switchRelativeLayout.setVisibility(View.VISIBLE);
        autoRelativeLayout.setVisibility(View.VISIBLE);
        takeRelativeLayout.setVisibility(View.VISIBLE);
        buttonsLinearLayout.setVisibility(View.GONE);

        stopCameraPreview();

        getCamera(mCameraID);
        startCameraPreview();

    }

    private int getFrontCameraID() {
        PackageManager pm = getPackageManager();
        if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
            return Camera.CameraInfo.CAMERA_FACING_FRONT;
        }

        return getBackCameraID();
    }

    private int getBackCameraID() {
        return Camera.CameraInfo.CAMERA_FACING_BACK;
    }

    /**
     * Take a picture
     */
    private void takePicture() {

        // Shutter callback occurs after the image is captured. This can
        // be used to trigger a sound to let the user know that image is taken
        Camera.ShutterCallback shutterCallback = null;

        // Raw callback occurs when the raw image data is available
        Camera.PictureCallback raw = null;

        // postView callback occurs when a scaled, fully processed
        // postView image is available.
        Camera.PictureCallback postView = null;

        // jpeg callback occurs when the compressed image is available
        if (mCamera != null)
            mCamera.takePicture(shutterCallback, raw, postView, this);
        else
            finish();
    }

    @Override
    public void onStop() {

        super.onStop();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceHolder = holder;

        getCamera(mCameraID);
        startCameraPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // The surface is destroyed with the visibility of the SurfaceView is set to View.Invisible
        // stop the preview
        stopCameraPreview();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;

        switch (requestCode) {
            case 1:
                Uri imageUri = data.getData();
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * A picture has been taken
     * @param data
     * @param camera
     */
    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        if (data != null && data.length > 0) {
            processPhoto();
        }
    }

    /**
     * When orientation changes, onOrientationChanged(int) of the listener will be called
     */
    private static class CameraOrientationListener extends OrientationEventListener {

        private int mCurrentNormalizedOrientation;
        private int mRememberedNormalOrientation;

        public CameraOrientationListener(Context context) {
            super(context, SensorManager.SENSOR_DELAY_NORMAL);
        }

        @Override
        public void onOrientationChanged(int orientation) {
            if (orientation != ORIENTATION_UNKNOWN) {
                mCurrentNormalizedOrientation = normalize(orientation);
            }
        }

        private int normalize(int degrees) {
            if (degrees > 315 || degrees <= 45) {
                return 0;
            }

            if (degrees > 45 && degrees <= 135) {
                return 90;
            }

            if (degrees > 135 && degrees <= 225) {
                return 180;
            }

            if (degrees > 225 && degrees <= 315) {
                return 270;
            }

            throw new RuntimeException("The physics as we know them are no more. Watch out for anomalies.");
        }

        public void rememberOrientation() {
            mRememberedNormalOrientation = mCurrentNormalizedOrientation;
        }

        public int getRememberedNormalOrientation() {
            return mRememberedNormalOrientation;
        }
    }
    public void showMenuLayout() {
        menuLinearLayout.setAnimation(null);
        TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, 0.0f - Constants.getDensity(FaeryCameraActivity.this) * 355);
        animation.setDuration(500);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                menuLinearLayout.setAnimation(null);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) menuLinearLayout.getLayoutParams();
                params.setMargins(0, 0, 0, 0);
                menuLinearLayout.setLayoutParams(params);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        menuLinearLayout.startAnimation(animation);
        menuLinearLayout.setVisibility(View.VISIBLE);

    }
    public void hideMenuLayout() {
        menuLinearLayout.setAnimation(null);
        TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, Constants.getDensity(FaeryCameraActivity.this) * 355);
        animation.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) menuLinearLayout.getLayoutParams();
                params.setMargins(0, 0, 0, (int) (0 - Constants.getDensity(FaeryCameraActivity.this) * 355));
                menuLinearLayout.setLayoutParams(params);
                menuLinearLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        menuLinearLayout.startAnimation(animation);
    }
    private String copyAssets(String assetFileName) {
        AssetManager assetManager = getAssets();
        File outFile = null;
        if (assetFileName != null) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(assetFileName);
                File directory = new File(Environment.getExternalStorageDirectory() + File.separator + getResources().getString(R.string.app_name));
                if (!directory.exists())
                    directory.mkdir();
                outFile = new File(directory, assetFileName);
                out = new FileOutputStream(outFile);
                copyFile(in, out);
            } catch(IOException e) {
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
            }
        }
        if (outFile != null) {
            return outFile.getAbsolutePath();
        } else {
            return null;
        }
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
}
