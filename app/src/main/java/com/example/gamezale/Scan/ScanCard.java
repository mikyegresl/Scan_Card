package com.example.gamezale.Scan;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gamezale.AddContactActivity;
import com.example.gamezale.Database.Address;
import com.example.gamezale.Database.Contact;
import com.example.gamezale.Database.ContactsViewModel;
import com.example.gamezale.MainActivity;
import com.example.gamezale.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScanCard extends AppCompatActivity {

    private ImageView scannedBitmapImageView;
//    private EditText addPhoneEditText;
//    private EditText addNameEditText;
//    private EditText addEmailEditText;
//    private EditText addGroupEditText;
//    private EditText addCompanyEditText;
//    private EditText addPositionEditText;
//    private EditText addStreetEditText;
//    private EditText addDistrictEditText;
//    private EditText addCityEditText;
//    private EditText addWebsiteEditText;
//    private Button addBtn;
//
//    private GraphicOverlay mOverlay;
    // Max width (portrait mode)
    private Integer mImageMaxWidth;
    // Max height (portrait mode)
    private Integer mImageMaxHeight;
    private Bitmap photo;
    private String imageFilePath;

    private static final int CAMERA_REQUEST = 1888;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int WRITE_PERMISSION_CODE = 200;
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 90);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    /**
     * Dimensions of inputs.
     */
    private static final int DIM_BATCH_SIZE = 1;
    private static final int DIM_PIXEL_SIZE = 3;
    private static final int DIM_IMG_SIZE_X = 224;
    private static final int DIM_IMG_SIZE_Y = 224;

    /* Preallocated buffers for storing image data. */
    private final int[] intValues = new int[DIM_IMG_SIZE_X * DIM_IMG_SIZE_Y];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_card);

        initLayout();

        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
        else {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (cameraIntent.resolveActivity(getPackageManager()) != null) {

                File photoFile = null;
                try {
                    photoFile = createImageFile();
                    Log.v("HELLO","success");
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                if (photoFile != null) {
                    Uri uri = FileProvider.getUriForFile(this, this.getPackageName(), photoFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        }

//        addBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String name = addNameEditText.getText().toString();
//                String phone = addPhoneEditText.getText().toString();
//                String email = addEmailEditText.getText().toString();
//                String group = addGroupEditText.getText().toString();
//                String company = addCompanyEditText.getText().toString();
//                String position = addPositionEditText.getText().toString();
//                String street = addStreetEditText.getText().toString();
//                String city = addCityEditText.getText().toString();
//                String website = addWebsiteEditText.getText().toString();
//
//                if (name.isEmpty()
//                        || phone.isEmpty()
//                        || email.isEmpty()
//                        || group.isEmpty()
//                        || company.isEmpty()
//                        || position.isEmpty()
//                        || street.isEmpty()
//                        || city.isEmpty()
//                        || website.isEmpty()) {
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(ScanCard.this);
//                    builder.setTitle("Empty Fields!");
//                    builder.setMessage("Please fill all fields to proceed");
//
//                    builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            dialog.dismiss();
//                        }
//                    });
//
//                    builder.show();
//                }
//                else {
//
//                    //save data
//                    Contact contact = new Contact(phone);
//                    contact.setFull_name(name);
//                    contact.setEmail(email);
//                    contact.setCompany(company);
//                    contact.setPosition(position);
//                    contact.setAddress(new Address(street, city));
//                    contact.setGroup(group);
////                    contact.setWork_phone();
////                    contact.setHome_phone();
//                    contact.setWebsite(website);
//                    ContactsViewModel contactsViewModel = new ContactsViewModel(getApplication());
//                    contactsViewModel.insert(contact);
//                    finish();
//                }
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            File file = new File(imageFilePath);
//            photo = (Bitmap)data.getExtras().get("data");
            try {
                photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photo != null) {
                Log.v("ImageParametersWidth", Integer.toString(photo.getWidth()));
                Log.v("ImageParametersHeight", Integer.toString(photo.getHeight()));
                // Get the dimensions of the View
                Pair<Integer, Integer> targetedSize = getTargetedWidthHeight();

                int targetWidth = targetedSize.first;
                int maxHeight = targetedSize.second;
                Log.v("ImageParametersTargetWidth", Integer.toString(targetWidth));
                Log.v("ImageParametersMaxHeight", Integer.toString(maxHeight));
                // Determine how much to scale down the image
                float scaleFactor =
                        Math.max(
                                (float) photo.getWidth() / (float) 970,
                                (float) photo.getHeight() / (float) 633);
                Log.v("ImageParametersScaleFactor", Float.toString(scaleFactor));
                Bitmap resizedBitmap =
                        Bitmap.createScaledBitmap(
                                photo,
                                (int) (photo.getWidth() / scaleFactor),
                                (int) (photo.getHeight() / scaleFactor),
                                true);
                photo = resizedBitmap;
//                scannedBitmapImageView.setImageBitmap(photo);
                runTextRecognition(photo);
            }
            //Firebase ML Kit
        }
        else {
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), CAMERA_REQUEST);
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {

                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                        Log.v("HELLO","success");
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (photoFile != null) {
                        Uri uri = FileProvider.getUriForFile(this, this.getPackageName(), photoFile);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                }
            }
            else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
        else if (requestCode == WRITE_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), CAMERA_REQUEST);
            }
            else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
        else {
            finish();
        }
    }

    private File createImageFile() throws IOException
    {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageName = "IMG_" + timestamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageName,
                ".jpg",
                storageDir
        );
        imageFilePath = image.getAbsolutePath();
        return image;
    }

    //Firebase ML Kit methods
    private void runTextRecognition(final Bitmap bitmap)
    {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionTextRecognizer recognizer = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
        recognizer.processImage(image)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText firebaseVisionText) {
                        processTextRecognitionResults(firebaseVisionText);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    private void processTextRecognitionResults(FirebaseVisionText firebaseVisionText)
    {
        List<FirebaseVisionText.TextBlock> blocks = firebaseVisionText.getTextBlocks();
        List<FirebaseVisionText.Line> lines;
        if (blocks.size() == 0) {
            showToast("No text found");
            return;
        }
//        mOverlay.clear();
        Pattern mobilePhonePattern = Pattern.compile("(Mob.:)?\\+[\\d\\s]+");   //^\+\d{3}\s?\d{2}\s?\d{3}\s?\d{2}\s?\d{2}
        Pattern workPhonePattern = Pattern.compile("(Tel.:)?\\s?\\+998\\s?71\\s?[\\d\\s]+");
        Pattern namePattern = Pattern.compile("(Kim\\s?Sergey\\s?Robertovich)||(Vyacheslav\\s?Kan)");
        Pattern positionPattern = Pattern.compile("(General\\s?Director)||(Deputy\\s?Director\\s?for\\s?Foreign)");
        Pattern companyPattern = Pattern.compile("[A-Z\\s]+");    //[A-Z]{4,}\s*[A-Z]{4,}\s*[A-Z]{4,}
        Pattern emailPattern = Pattern.compile("[a-z]+(@[a-z]+)?(.uz|.ru|.com)?$");
        Pattern webPattern = Pattern.compile("^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/|w\\.|www\\.)+[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$");

        Log.v("TextRecognition", firebaseVisionText.getText());

        Intent intent = new Intent(this, AddContactActivity.class);
        intent.putExtra("scanData", "ScanCard");

        for (int i = 0; i < blocks.size(); i++) {
            lines = blocks.get(i).getLines();
            for (int j = 0; j < lines.size(); j++) {
                List<FirebaseVisionText.Element> elements = lines.get(j).getElements();
//                GraphicOverlay.Graphic textGraphic = new TextGraphic(mOverlay, lines.get(j));
//                mOverlay.add(textGraphic);

                Matcher mobilePhoneMatcher = mobilePhonePattern.matcher(lines.get(j).getText());
                Matcher workPhoneMatcher = workPhonePattern.matcher(lines.get(j).getText());
                Matcher companyMatcher = companyPattern.matcher(lines.get(j).getText());
                Matcher nameMatcher = namePattern.matcher(lines.get(j).getText());
                Matcher positionMatcher = positionPattern.matcher(lines.get(j).getText());
                Matcher emailMatcher = emailPattern.matcher(lines.get(j).getText());
                Matcher webMatcher = webPattern.matcher(lines.get(j).getText());
                if (mobilePhoneMatcher.matches()) {
                    String phone = lines.get(j).getText();
                    if (phone.contains("Mob.:")) {
                        phone = phone.substring("Mob.:".length());
                    }
                    Log.v("TextRecognitionPhone", phone);
//                    addPhoneEditText.setText(phone);
                    intent.putExtra(MainActivity.SHARE_PHONE_MOBILE, phone);
                }
                if (workPhoneMatcher.matches()) {
                    String workPhone = lines.get(j).getText();
                    if (workPhone.contains("Tel.:")) {
                        workPhone = workPhone.substring("Tel.:".length());
                    }
//                    Log.v("TextRecognitionPhone", workPhone);
//                    addPhoneEditText.setText(workPhone);
                    intent.putExtra(MainActivity.SHARE_PHONE_WORK, workPhone);
                }
                if (nameMatcher.matches()) {
                    String name = lines.get(j).getText();
                    Log.v("TextRecognitionName", lines.get(j).getText());
//                    addNameEditText.setText(lines.get(j).getText());
                    intent.putExtra(MainActivity.SHARE_FULLNAME, name);
                }
                if (companyMatcher.matches()) {
                    String company = lines.get(j).getText();
                    if (company.equals("GENIUS")) {
                        company = company.concat(" BROKER'S");
                    }
                    Log.v("TextRecognitionCompany", company);
//                    addCompanyEditText.setText(company);
                    intent.putExtra(MainActivity.SHARE_COMPANY, company);
                }
                if (positionMatcher.matches()) {
                    String position = lines.get(j).getText();
                    if (position.equals("Deputy Director for Foreign")) {
                        position = position.concat(" Economic Activity");
                    }
                    Log.v("TextRecognitionPosition", position);
//                    addPositionEditText.setText(position);
                    intent.putExtra(MainActivity.SHARE_POSITION, position);
                }
                if (emailMatcher.matches()) {
                    Log.v("TextRecognitionEmail", lines.get(j).getText());
//                    addEmailEditText.setText(lines.get(j).getText());
                    String email = lines.get(j).getText();
                    intent.putExtra(MainActivity.SHARE_EMAIL, email);
                }
                if (webMatcher.matches()) {
                    Log.v("TextRecognitionWebsite", lines.get(j).getText());
//                    addWebsiteEditText.setText(lines.get(j).getText());
                    String website = lines.get(j).getText();
                    intent.putExtra(MainActivity.SHARE_WEBSITE, website);
                }
            }
        }
        //save scanned card contacts to database
        finish();
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void initLayout() {

        scannedBitmapImageView = findViewById(R.id.scannedBitmap);
//        mOverlay = findViewById(R.id.graphicOverlay);
//        addPhoneEditText = findViewById(R.id.addPhoneEditText);
//        addNameEditText = findViewById(R.id.addNameEditText);
//        addEmailEditText = findViewById(R.id.addEmailEditText);
//        addGroupEditText = findViewById(R.id.addGroupEditText);
//        addCompanyEditText = findViewById(R.id.addCompanyEditText);
//        addPositionEditText = findViewById(R.id.addPositionEditText);
//        addStreetEditText = findViewById(R.id.addStreetEditText);
//        addDistrictEditText = findViewById(R.id.addDistrictEditText);
//        addCityEditText = findViewById(R.id.addCityEditText);
//        addWebsiteEditText = findViewById(R.id.addWebsiteEditText);
//        addBtn = findViewById(R.id.addBtn);
    }

    private synchronized ByteBuffer convertBitmapToByteBuffer(
            Bitmap bitmap, int width, int height) {
        ByteBuffer imgData =
                ByteBuffer.allocateDirect(
                        DIM_BATCH_SIZE * DIM_IMG_SIZE_X * DIM_IMG_SIZE_Y * DIM_PIXEL_SIZE);
        imgData.order(ByteOrder.nativeOrder());
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, DIM_IMG_SIZE_X, DIM_IMG_SIZE_Y,
                true);
        imgData.rewind();
        scaledBitmap.getPixels(intValues, 0, scaledBitmap.getWidth(), 0, 0,
                scaledBitmap.getWidth(), scaledBitmap.getHeight());
        // Convert the image to int points.
        int pixel = 0;
        for (int i = 0; i < DIM_IMG_SIZE_X; ++i) {
            for (int j = 0; j < DIM_IMG_SIZE_Y; ++j) {
                final int val = intValues[pixel++];
                imgData.put((byte) ((val >> 16) & 0xFF));
                imgData.put((byte) ((val >> 8) & 0xFF));
                imgData.put((byte) (val & 0xFF));
            }
        }
        return imgData;
    }

    // Functions for loading images from app assets.

    // Returns max image width, always for portrait mode. Caller needs to swap width / height for
    // landscape mode.
    private Integer getImageMaxWidth() {
        if (mImageMaxWidth == null) {
            // Calculate the max width in portrait mode. This is done lazily since we need to
            // wait for
            // a UI layout pass to get the right values. So delay it to first time image
            // rendering time.
            mImageMaxWidth = scannedBitmapImageView.getWidth();
        }

        return mImageMaxWidth;
    }

    // Returns max image height, always for portrait mode. Caller needs to swap width / height for
    // landscape mode.
    private Integer getImageMaxHeight() {
        if (mImageMaxHeight == null) {
            // Calculate the max width in portrait mode. This is done lazily since we need to
            // wait for
            // a UI layout pass to get the right values. So delay it to first time image
            // rendering time.
            mImageMaxHeight = scannedBitmapImageView.getHeight();
        }

        return mImageMaxHeight;
    }

    private Pair<Integer, Integer> getTargetedWidthHeight() {
        int targetWidth;
        int targetHeight;
        int maxWidthForPortraitMode = getImageMaxWidth();
        int maxHeightForPortraitMode = getImageMaxHeight();
        targetWidth = maxWidthForPortraitMode;
        targetHeight = maxHeightForPortraitMode;
        return new Pair<>(targetWidth, targetHeight);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private int getRotationCompensation(String cameraID, Activity activity, Context context) throws CameraAccessException
    {

        int deviceRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int rotationCompensation = ORIENTATIONS.get(deviceRotation);

        CameraManager cameraManager = (CameraManager)context.getSystemService(CAMERA_SERVICE);
        int sensorOrientation = cameraManager
                .getCameraCharacteristics(cameraID)
                .get(CameraCharacteristics.SENSOR_ORIENTATION);
        rotationCompensation = (rotationCompensation + sensorOrientation + 270) % 360;

        int result;

        switch (rotationCompensation) {

            case 0:
            {
                result = FirebaseVisionImageMetadata.ROTATION_0;
                break;
            }
            case 90:
            {
                result = FirebaseVisionImageMetadata.ROTATION_90;
                break;
            }
            case 180:
            {
                result = FirebaseVisionImageMetadata.ROTATION_180;
                break;
            }
            case 270:
            {
                result = FirebaseVisionImageMetadata.ROTATION_270;
                break;
            }
            default:
            {
                result = FirebaseVisionImageMetadata.ROTATION_0;
                Log.e("ScanCard:Rotation", "Wrong Rotation Value: " + rotationCompensation);
                break;
            }
        }

        return result;
    }
}

