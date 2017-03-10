package com.quang.tracnghiemtoan.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.quang.tracnghiemtoan.models.Variables;

/**
 * Created by QUANG on 1/14/2017.
 */

public class VideoFragment extends YouTubePlayerFragment implements YouTubePlayer.OnInitializedListener {
    private YouTubePlayer player;
    private String videoId;

    public static VideoFragment newInstance() {
        return new VideoFragment();
    }

    public VideoFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onDestroy() {
        if (this.player != null) {
            this.player.release();
        }
        super.onDestroy();
    }

    public void setVideoId(String videoId) {
        if (videoId != null && !videoId.equals(this.videoId)) {
            pause();
            initialize(Variables.API_KEY, this);
            this.videoId = videoId;
            if (this.player != null) {
                this.player.cueVideo(videoId);
            }
        }
    }

    public void pause() {
        if (this.player != null) {
            this.player.pause();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restored) {
        this.player = youTubePlayer;
        player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);
        if (!restored && this.videoId != null) {
            player.cueVideo(this.videoId);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        this.player = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Vui lòng cài đặt Youtube");
        builder.setPositiveButton("Cài ngay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.youtube")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.youtube")));
                }
            }
        });
        builder.setNegativeButton("Quay lại", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create();
        if (!getActivity().isFinishing()) builder.show();
    }
}