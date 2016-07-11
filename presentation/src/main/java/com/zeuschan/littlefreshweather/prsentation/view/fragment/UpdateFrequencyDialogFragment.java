package com.zeuschan.littlefreshweather.prsentation.view.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.zeuschan.littlefreshweather.prsentation.R;

/**
 * Created by chenxiong on 2016/7/11.
 */
public class UpdateFrequencyDialogFragment extends DialogFragment {
    private UpdateFrequencyDialogListener mListener;

    public interface UpdateFrequencyDialogListener {
        void onDialogItemClick(int which);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (UpdateFrequencyDialogListener)activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.auto_update_frequency)
                .setItems(R.array.update_frequency_string, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mListener != null)
                    mListener.onDialogItemClick(which);
            }
        });
        return builder.create();
    }
}
