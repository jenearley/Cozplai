package edu.sjsu.jen.cozplai;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by jen0e on 12/5/2017.
 */

public class CharacterListActivity extends AppCompatActivity {

    private static final String LOG_TAG = "CharacterListActivity";
    public static CharacterListActivity instance;
    final Activity _this = this;
    private static final int ADD_PHOTO_REQUEST_CODE = 400;
    private static final int REQUEST_IMAGE_CAPTURE = 401;

    private AlertDialog currentDialog;
    DataHelper dataHelper = new DataHelper(_this);
    private RecyclerView recyclerView;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_signout:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!SignInActivity.mGoogleApiClient.isConnected()) {
                            SignInActivity.mGoogleApiClient.blockingConnect();
                        }

                        Auth.GoogleSignInApi.signOut(SignInActivity.mGoogleApiClient)
                                .setResultCallback(new ResultCallback<Status>() {
                                    @Override
                                    public void onResult(@NonNull Status status) {
                                        Intent intent = new Intent(_this, SignInActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                    }
                }).start();
                break;

            case R.id.menu_conventions:
                Intent intent = new Intent(_this, ConventionsActivity.class);
                startActivity(intent);
                break;

            case R.id.menu_camera:
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
                break;
            case R.id.menu_photos:
                Intent showGalleryIntent = new Intent(this, PhotoGalleryActivity.class);
                startActivity(showGalleryIntent);
                break;

        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_PHOTO_REQUEST_CODE) {
            if(resultCode == RESULT_OK){
                Uri imageUri = data.getData();
                if (currentDialog != null) {
                    ImageView imageView = currentDialog.findViewById(R.id.character_picture);
                    imageView.setImageURI(imageUri);
                }
            }
        } else if(requestCode == REQUEST_IMAGE_CAPTURE){
            if(resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                saveToInternalStorage(Long.toString(System.currentTimeMillis()), "captures", imageBitmap);
            }
        }

    }

    private String saveToInternalStorage(String fileName, String dirName, Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir(dirName, Context.MODE_PRIVATE);
        // Create imageDir
        File photoPath = new File(directory,fileName + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(photoPath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return photoPath.getAbsolutePath();
    }

    public static void updateList() {
        if (instance != null) {
            instance.recyclerView.getAdapter().notifyDataSetChanged();
            instance.recyclerView.requestLayout();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_page);
        instance = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.character_page_toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.character_recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        final CharacterAdapter characterAdapter = new CharacterAdapter(CharacterTracker.characters);
        recyclerView.setAdapter(characterAdapter);

        ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int index = viewHolder.getAdapterPosition();
                Character toRemove = CharacterTracker.characters.get(index);
                CharacterTracker.deleteCharacter(toRemove, dataHelper);
                recyclerView.requestLayout();
            }
        };
        ItemTouchHelper swipeHelper = new ItemTouchHelper(swipeCallback);
        swipeHelper.attachToRecyclerView(recyclerView);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.add_character_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(_this);
                builder.setTitle("Create new character");

                LayoutInflater inflater = _this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.new_character_dialog, null);
                builder.setView(dialogView);
                builder.setCancelable(false);

                final EditText characterName = (EditText) dialogView.findViewById(R.id.character_name_edittext);
                final EditText characterSource = (EditText) dialogView.findViewById(R.id.character_source_edittext);
                final ImageView characterPhoto = (ImageView) dialogView.findViewById(R.id.character_picture);
                characterPhoto.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, ADD_PHOTO_REQUEST_CODE);
                    }


                });

                builder.setPositiveButton("CREATE", null);
                builder.setNegativeButton("CANCEL", null);

                currentDialog = builder.create();

                currentDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(final DialogInterface dialogInterface) {
                        Button positiveButton = currentDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.i(LOG_TAG, "onClick: CREATE");

                                String name = (String) characterName.getText().toString();
                                Log.i(LOG_TAG, "characterName.getText() = " + name);

                                if(name.equals("")){
                                    Toast.makeText(getApplicationContext(), "You must enter a character name", Toast.LENGTH_SHORT).show();
                                } else {
                                    Character character = new Character(name);
                                    // get bitmap from image view
                                    Bitmap imageBitmap = ((BitmapDrawable)characterPhoto.getDrawable()).getBitmap();
                                    // downscale image if necessary (height or width > some number)
                                    // save bitmap to internal storage
                                    String filePath = saveToInternalStorage(name, "icons", imageBitmap);
                                    character.setPhotoUri(filePath);
                                    String source = (String) characterSource.getText().toString();
                                    if(source.equals("")){
                                        character.setSource("???");
                                    } else {
                                        character.setSource(source);
                                    }

                                    CharacterTracker.addCharacter(character, dataHelper);

                                    currentDialog.dismiss();
                                    recyclerView.requestLayout();
                                }
                            }
                        });

                        Button negativeButton = currentDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                        negativeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                currentDialog.dismiss();
                            }
                        });
                    }
                });

                currentDialog.show();
            }
        });

    }
}
