package com.microsoft.bot.builder.solutions.virtualassistant.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.microsoft.bot.builder.solutions.virtualassistant.R;
import com.microsoft.bot.builder.solutions.virtualassistant.activities.utils.InteractionUtils;

/**
 * This base class provides functionality that is reusable in Activities of this app
 */
@SuppressLint("Registered")
public abstract class BaseActivity extends AppCompatActivity {

    // Constants
    private static final Integer PERMISSION_REQUEST_RECORD_AUDIO = 101;
    private static final Integer PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 102;

    protected void showSnackbar(View view, String message){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setAction("Action", null)
                .show();
    }

    /**
     * Requests the RECORD_AUDIO and WRITE_EXTERNAL_STORAGE permissions as they are "Dangerous"
     * If the permission has been denied previously, a dialog with extra rationale info will prompt
     * the user to grant the permission, otherwise it is requested directly.
     */
    protected void requestDangerousPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.permission_record_audio_title))
                    .setMessage(getString(R.string.permission_record_audio_rationale))
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        //re-request
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.RECORD_AUDIO},
                                PERMISSION_REQUEST_RECORD_AUDIO);
                    })
                    .show();
        } else {
            // RECORD_AUDIO permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    PERMISSION_REQUEST_RECORD_AUDIO);
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.permission_write_external_storage_title))
                    .setMessage(getString(R.string.permission_write_external_storage_rationale))
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        //re-request
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
                    })
                    .show();
        } else {
            // WRITE_EXTERNAL_STORAGE permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        // Received permission result for RECORD_AUDIO permission");
        if (requestCode == PERMISSION_REQUEST_RECORD_AUDIO) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // RECORD_AUDIO permission has been granted
                // nothing else needs to be done
            } else {
                InteractionUtils.showMessageDialogOk(
                        this,
                        getString(R.string.permission_record_audio_title),
                        getString(R.string.permission_record_audio_denied),
                        new InteractionUtils.InteractionCallback(){
                            @Override
                            public void dialogClickOK() {
                                // empty
                            }
                        });
            }
        }

        // Received permission result for WRITE_EXTERNAL_STORAGE permission");
        if (requestCode == PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // WRITE_EXTERNAL_STORAGE permission has been granted
                // nothing else needs to be done
            } else {
                InteractionUtils.showMessageDialogOk(
                        this,
                        getString(R.string.permission_write_external_storage_title),
                        getString(R.string.permission_write_external_storage_denied),
                        new InteractionUtils.InteractionCallback(){
                            @Override
                            public void dialogClickOK() {
                                // empty
                            }
                        });
            }
        }
    }

}
