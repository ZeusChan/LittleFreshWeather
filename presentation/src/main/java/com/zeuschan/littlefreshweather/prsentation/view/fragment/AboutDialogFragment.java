package com.zeuschan.littlefreshweather.prsentation.view.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.zeuschan.littlefreshweather.prsentation.R;

/**
 * Created by chenxiong on 2016/7/11.
 */
public class AboutDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.about)
                .setMessage(R.string.about_content);
        return builder.create();
    }
}
