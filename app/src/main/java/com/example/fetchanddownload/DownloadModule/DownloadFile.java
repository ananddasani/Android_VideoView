package com.example.fetchanddownload.DownloadModule;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;

public class DownloadFile {

    public static void downloadUsingDownloadManager(String givenUrl, String fileName, Context context) {

        Uri uri = Uri.parse(givenUrl);

        try {
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(uri);

            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
                    .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI)
                    .setTitle(fileName)
                    .setDescription("Downloading " + fileName)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);

            downloadManager.enqueue(request);

            Toast.makeText(context, "Downloading", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Unable to download file", Toast.LENGTH_SHORT).show();
        }
    }

    public static void downloadUsingPRDownloader(String givenUrl, String fileName, Context context, View view) {

        File dirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        //set the progress bar
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Downloading " + fileName);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        PRDownloader.download(givenUrl, String.valueOf(dirPath), fileName)
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {

                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {

                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {

                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                        //calculate the percentage and show on the dialog
                        long percent = progress.currentBytes * 100 / progress.totalBytes;
                        progressDialog.setMessage("Downloaded " + percent + "%");
                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        progressDialog.dismiss();

                        Snackbar.make(view, "Saved to Downloads Folder :)", Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Error error) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Unable to Download File", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
