package com.example.marmoushadminapp;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.media.audiofx.DynamicsProcessing;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;

import com.examples.marmoush.data.Showitem;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class uploadFregmant extends Fragment  {
    Spinner TitleSpinner;
    EditText etDescription, etCode,etDescAr;
    ImageView imageTake;
    Button takeButton, saveButton;
    Bitmap b;

    TextView tvTitleAr;
    List<String>Titles= Arrays.asList("Select Title","Receptions","Corridors",
            "Bedrooms", "Kitchens","Bathrooms","UnitsAndLibrarys","Livingrooms","News");

    public uploadFregmant() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_upload_fregmant, container, false);
        TitleSpinner = view.findViewById(R.id.titleSpinner);
        etCode = view.findViewById(R.id.etCode);
        etDescription = view.findViewById(R.id.etDescription);
        imageTake = view.findViewById(R.id.imagetaken);
        takeButton = view.findViewById(R.id.takeButton);
        saveButton = view.findViewById(R.id.saveButton);
        etDescAr=view.findViewById(R.id.etDescAr);
        tvTitleAr=view.findViewById(R.id.tvTitleAr);

        ArrayAdapter adapter=new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,Titles);
        TitleSpinner.setAdapter(adapter);

        TitleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==1)tvTitleAr.setText("غرف استقبال");
                else if (position==2)tvTitleAr.setText("طرقات");
                else if (position==3)tvTitleAr.setText("غرف نوم");
                else if (position==4)tvTitleAr.setText("مطابخ");
                else if (position==5)tvTitleAr.setText("حمامات");
                else if (position==6)tvTitleAr.setText("مكتبات");
                else if (position==7)tvTitleAr.setText("غرف معيشه");
                if (position==8) {
                    etDescription.setEnabled(false);
                    tvTitleAr.setEnabled(false);
                    etDescAr.setEnabled(false);
                }
                else{
                    etDescription.setEnabled(true);
                    tvTitleAr.setEnabled(true);
                    etDescAr.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        takeButton.setOnClickListener(v -> PickImageDialog.build(new PickSetup())
                .setOnPickResult(r -> {
                    b=r.getBitmap();
                    imageTake.setImageBitmap(b);
                    saveButton.setEnabled(true);
                })
                .setOnPickCancel(new IPickCancel() {
                    @Override
                    public void onCancelClick() {
                        //TODO: do what you have to if user clicked cancel
                    }
                }).show(getActivity()));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TitleSpinner.getSelectedItem().toString().equals("News")){
                    if (etCode.getText().length() == 0){
                        Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                    }else {
                        Backendless.Files.Android.upload(b, Bitmap.CompressFormat.WEBP, 60, etCode.getText().toString(), "images", new AsyncCallback<BackendlessFile>() {
                            @Override
                            public void handleResponse(BackendlessFile response) {

                                News news= new News();
                                news.setCode(Integer.parseInt(etCode.getText().toString()));
                                news.setImageUrl(response.getFileURL());
                                Backendless.Data.of(News.class).save(news, new AsyncCallback<News>() {
                                    @Override
                                    public void handleResponse(News response) {
                                        imageTake.setImageBitmap(null);
                                        etCode.setText("");
                                        etDescription.setText("");
                                        etDescAr.setText("");
                                        TitleSpinner.setSelection(0);
                                        Toast.makeText(getActivity(), "Item Uploaded", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void handleFault(BackendlessFault fault) {

                                    }
                                });
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {

                            }
                        });

                        }

                }else {
                    if (etCode.getText().length() == 0 || etDescription.getText().toString().equals("")||etDescAr.getText().toString().equals("")||TitleSpinner.getSelectedItem().toString().equals("Select Title")) {
                        Toast.makeText(getActivity(), "Please fill all Data", Toast.LENGTH_SHORT).show();
                    } else{
                        Backendless.Files.Android.upload(b, Bitmap.CompressFormat.WEBP, 60, etCode.getText().toString(), "images", new AsyncCallback<BackendlessFile>() {
                            @Override
                            public void handleResponse(BackendlessFile response) {

                                Showitem showitem=new Showitem();
                                showitem.setCode(Integer.parseInt(etCode.getText().toString()));
                                showitem.setDesc(etDescription.getText().toString());
                                showitem.setTitle(TitleSpinner.getSelectedItem().toString());
                                showitem.setImageUrl(response.getFileURL());
                                showitem.setDescAr(etDescAr.getText().toString());
                                showitem.setTitleAr(tvTitleAr.getText().toString());

                                Backendless.Data.of(Showitem.class).save(showitem, new AsyncCallback<com.examples.marmoush.data.Showitem>() {
                                    @Override
                                    public void handleResponse(Showitem response) {
                                        imageTake.setImageBitmap(null);
                                        etCode.setText("");
                                        etDescription.setText("");
                                        tvTitleAr.setText("العنوان");
                                        etDescAr.setText("");
                                        TitleSpinner.setSelection(0);
                                        Toast.makeText(getActivity(), "Item Uploaded", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void handleFault(BackendlessFault fault) {

                                    }
                                });

                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {

                            }
                        });
                    }
                }

            }
        });


        return view;
    }


}
