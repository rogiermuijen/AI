package com.microsoft.bot.builder.solutions.virtualassistant.activities.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;

/**
 * Utility file to handle dialog / messaging with user
 */

public class InteractionUtils {

    // interface with default (empty) code so that each method doesn't need to be implemented
    public interface InteractionCallback{
        default void dialogClickOK(){/*empty*/}
        default void dialogClickCancel(){/*empty*/}
    }

    public static void showMessageDialogOk(Context context, String title, String message, InteractionCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);

        String positiveText = context.getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                (dialog, which) -> {
                    // positive button
                    callback.dialogClickOK();
                });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    public static void showMessageDialogOkCancel(Context context, String title, String message, InteractionCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);

        String positiveText = context.getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                (dialog, which) -> {
                    // positive button
                    callback.dialogClickOK();
                });

        String negativeText = context.getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                (dialog, which) -> {
                    // negative button logic
                    callback.dialogClickCancel();
                });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    public static void showMessageDialogCustom(Context context, String title, String message, String posButton, InteractionCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);

        builder.setPositiveButton(posButton,
                (dialog, which) -> {
                    // positive button
                    callback.dialogClickOK();
                });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    public static void showMessageDialogCustom(Context context, String title, String message, String posButton, String negButton, InteractionCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton(posButton,
                (dialog, which) -> {
                    // positive button
                    callback.dialogClickOK();
                });

        builder.setNegativeButton(negButton,
                (dialog, which) -> {
                    // negative button logic
                    callback.dialogClickCancel();
                });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    /**
     * @param context
     * @param message
     * @param clickListener
     * @return
     */
    public static android.app.AlertDialog showDialog(Activity context, String message, DialogInterface.OnClickListener clickListener)
    {
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "Ok", clickListener);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();

        return alertDialog;
    }

    /**
     * Utility method to show snackbar
     * note: snackbar can also be dismissed
     */
    private Snackbar showSnackBar(@NonNull CoordinatorLayout coordinatorLayout, @NonNull String msg, int showMsgLength) {
        Snackbar snackBar;
        if (showMsgLength == Snackbar.LENGTH_INDEFINITE) {
            snackBar = Snackbar.make(coordinatorLayout, msg, showMsgLength);
            snackBar.setAction("OK", v -> snackBar.dismiss());
            snackBar.show();
        } else {
            snackBar = Snackbar.make(coordinatorLayout, msg, showMsgLength);
            snackBar.show();
        }
        return snackBar;
    }
}
