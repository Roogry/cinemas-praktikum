package id.roogry.cinemas.helper;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import id.roogry.cinemas.R;
import id.roogry.cinemas.databinding.DialogConfirmSaveBinding;
import id.roogry.cinemas.databinding.DialogLoadingBinding;

public class DialogLoading {
    private final AlertDialog dialog;

    public DialogLoading(Activity activity){
        dialog = new AlertDialog.Builder(activity, R.style.DialogSecondary)
                .create();
        DialogLoadingBinding dialogBinding = DialogLoadingBinding
                .inflate(LayoutInflater.from(activity));

        dialog.setView(dialogBinding.getRoot());
        dialog.setCanceledOnTouchOutside(false);
    }

    public void showLoading(){
        dialog.show();
    }

    public void hideLoading(){
        dialog.dismiss();
    }
}
