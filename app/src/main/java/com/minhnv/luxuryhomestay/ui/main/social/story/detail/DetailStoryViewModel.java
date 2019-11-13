package com.minhnv.luxuryhomestay.ui.main.social.story.detail;

import android.content.Context;
import android.os.Environment;

import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.minhnv.luxuryhomestay.data.DataManager;
import com.minhnv.luxuryhomestay.ui.base.BaseViewModel;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DetailStoryViewModel extends BaseViewModel<DetailStoryNavigator> {
    private static final String TAG = "DetailStoryViewModel";
    public DetailStoryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void downLoad(String url, String dirPath, String fileName, Context context){
        Rx2AndroidNetworking.download(url,dirPath,fileName)
                .setTag("downLoad")
                .setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener((timeTakenInMillis, bytesSent, bytesReceived, isFromCache) -> {
                    AppLogger.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                    AppLogger.d(TAG, " bytesSent : " + bytesSent);
                    AppLogger.d(TAG, " bytesReceived : " + bytesReceived);
                    AppLogger.d(TAG, " isFromCache : " + isFromCache);
                })
                .setDownloadProgressListener((bytesDownloaded, totalBytes) -> {

                })
                .startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        File wallpaperDirectory = new File(Environment.getDataDirectory() + dirPath);
                        if (!wallpaperDirectory.exists()) {
                            wallpaperDirectory.mkdirs();
                        }
                        String isSaveDir = wallpaperDirectory.toString();
                        File file = new File(isSaveDir,fileName);
                        try {
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            fileOutputStream.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    public void DownLoad(){
        getNavigator().ActionUserListenerDownLoadFile();
    }
}
