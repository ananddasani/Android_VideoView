# Android_VideoView
Showing Video by Fetching from URL

This topic is a part of [My Complete Andorid Course](https://github.com/ananddasani/Android_Apps)

### VideoFragment.java
```
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
}
```

- [Full Example](https://github.com/ananddasani/Android_Fetch_and_Download)


# App Highlight

<img src="app_images/Fetch and Download Code.png" /><br>

<img src="app_images/Fetch and Download App1.png" width="300" /> <img src="app_images/Fetch and Download App2.png" width="300" /> <img src="app_images/Fetch and Download App3.png" width="300" />
