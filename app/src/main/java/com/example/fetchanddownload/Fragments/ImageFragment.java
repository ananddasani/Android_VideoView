package com.example.fetchanddownload.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.fetchanddownload.DownloadModule.DownloadFile;
import com.example.fetchanddownload.R;

public class ImageFragment extends Fragment {

    ImageView imageView;
    EditText editText;
    Button getImageButton, defaultDownloader, fastDownloader;

    boolean loaded = false;
    String editTextUrl = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image, container, false);

        imageView = view.findViewById(R.id.imageView);
        editText = view.findViewById(R.id.editTextUrl);
        getImageButton = view.findViewById(R.id.getImageButton);
        defaultDownloader = view.findViewById(R.id.defaultDownloader);
        fastDownloader = view.findViewById(R.id.fastDownloader);

        getImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fetch the url given
                editTextUrl = editText.getText().toString();

                if (!editTextUrl.equals("")) {

                    Toast.makeText(view.getContext(), "Fetching Image...", Toast.LENGTH_LONG).show();

                    //load the image from the url
                    Uri uri = Uri.parse(editTextUrl);
                    Glide.with(view.getContext()).load(uri).into(imageView);
                    loaded = true;

                } else
                    Toast.makeText(getContext(), "Please Enter URL", Toast.LENGTH_SHORT).show();
            }
        });

        //use DownloaderManager to download the file
        defaultDownloader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loaded)
                    DownloadFile.downloadUsingDownloadManager(editTextUrl, URLUtil.guessFileName(editTextUrl, null, null), getContext());
                else
                    Toast.makeText(getContext(), "Image not loaded yet!", Toast.LENGTH_SHORT).show();
            }
        });

        //use PRDownloader to download file
        fastDownloader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loaded)
                    DownloadFile.downloadUsingPRDownloader(editTextUrl, URLUtil.guessFileName(editTextUrl, null, null), getContext(), v);
                else
                    Toast.makeText(getContext(), "Image not loaded yet!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
