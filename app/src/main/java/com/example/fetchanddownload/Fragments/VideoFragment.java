package com.example.fetchanddownload.Fragments;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

import com.example.fetchanddownload.DownloadModule.DownloadFile;
import com.example.fetchanddownload.R;

public class VideoFragment extends Fragment {

    VideoView videoView;
    EditText editText;
    Button getVideoButton, defaultDownloadV, fastDownloadV;

    String editTextUrl = "";
    boolean loaded = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        videoView = view.findViewById(R.id.videoView);
        editText = view.findViewById(R.id.editTextUrlVideo);
        getVideoButton = view.findViewById(R.id.getVideoButton);
        defaultDownloadV = view.findViewById(R.id.defaultDownloadV);
        fastDownloadV = view.findViewById(R.id.fastDownloadV);

        getVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //fetch the url given
                editTextUrl = editText.getText().toString();

                if (!editTextUrl.equals("")) {

                    Toast.makeText(view.getContext(), "Fetching Video...", Toast.LENGTH_LONG).show();

                    //load the Video from the url
                    Uri uri = Uri.parse(editTextUrl);
                    videoView.setVideoURI(uri);

                    MediaController mediaController = new MediaController(getActivity());
                    mediaController.setAnchorView(videoView);

                    videoView.setMediaController(mediaController);
                    videoView.setZOrderOnTop(true);
                    videoView.setBackgroundColor(Color.TRANSPARENT);
                    videoView.start();

                    //listen when video is prepared to show
                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            Toast.makeText(view.getContext(), "Prepared :)", Toast.LENGTH_SHORT).show();

                            mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                            mp.setLooping(true);
                            mp.setScreenOnWhilePlaying(true);
                        }
                    });

                    loaded = true;
                    Toast.makeText(view.getContext(), "Please Wait\nFetching Time totally depends upon your internet speed...", Toast.LENGTH_LONG).show();

                } else
                    Toast.makeText(getContext(), "Please Enter URL", Toast.LENGTH_SHORT).show();
            }
        });

        defaultDownloadV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loaded)
                    DownloadFile.downloadUsingDownloadManager(editTextUrl, URLUtil.guessFileName(editTextUrl, null, null), getContext());
                else
                    Toast.makeText(getContext(), "Video not loaded yet!", Toast.LENGTH_SHORT).show();
            }
        });

        fastDownloadV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loaded)
                    DownloadFile.downloadUsingPRDownloader(editTextUrl, URLUtil.guessFileName(editTextUrl, null, null), getContext(), v);
                else
                    Toast.makeText(getContext(), "Video not loaded yet!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
