package com.example.translatorapp.view.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import static com.example.translatorapp.view.BundleConstants.MESSAGE_EXTRA;
import static com.example.translatorapp.view.BundleConstants.TITLE_EXTRA;

public class AlertDialogFragment extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        Bundle args = getArguments();
        if (args != null) {
            String title = args.getString(TITLE_EXTRA);
            String message = args.getString(MESSAGE_EXTRA);
            alertDialog = new AlertDialog.Builder(context).setTitle(title).setMessage(message).create();
        }
        return alertDialog;
    }

    public static AlertDialogFragment newInstance(String title, String message) {
        AlertDialogFragment dialogFragment = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putString(TITLE_EXTRA, title);
        args.putString(MESSAGE_EXTRA, message);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }
}