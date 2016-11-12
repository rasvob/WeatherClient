package com.diamonddesign.rasvo.weatherclient.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.diamonddesign.rasvo.weatherclient.R;

/**
 * Created by rasvo on 12.11.2016.
 */

public class DialogBuilders {
    private Context context;
    private DialogInterface.OnClickListener positiveListener;
    private DialogInterface.OnClickListener negativeListener;

    public DialogBuilders(Context context, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        this.context = context;
        this.positiveListener = positiveListener;
        this.negativeListener = negativeListener;
    }

    public AlertDialog createWifiDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        buildGenericDialog(builder, context.getString(R.string.dialog_wifi_title), context.getString(R.string.dialog_wifi_text));
        builder.setPositiveButton(context.getString(R.string.open_settings), positiveListener);
        builder.setNegativeButton(context.getString(R.string.cancel), negativeListener);

        return builder.create();
    }

    public AlertDialog createLocationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        buildGenericDialog(builder, context.getString(R.string.dialog_gps_title), context.getString(R.string.dialog_gps_text));
        builder.setPositiveButton(context.getString(R.string.open_settings), positiveListener);
        builder.setNegativeButton(context.getString(R.string.cancel), negativeListener);

        return builder.create();
    }

    private void buildGenericDialog(AlertDialog.Builder builder, String title, String message) {
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
    }
}
