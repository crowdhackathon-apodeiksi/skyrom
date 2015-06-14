package skyrom.com.taxmachine.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

import skyrom.com.taxmachine.R;

public class DialogUtils {

    /**
     * Displays an error dialog with a title and a message. If the error is empty, a generic error
     * message is displayed.
     * @param context Application context.
     * @param titleId Title resource Id.
     * @param error Message String Id.
     */
    public static void showErrorAlertDialog(final Context context, int titleId, String error) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(titleId);
        if (error == null || error.isEmpty()) {
            error = context.getResources().getString(R.string.unexpected_error);
        }
        alertDialog.setMessage(error);
        alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    /**
     * Displays a dialog with a title and a message.
     * @param context Application context.
     * @param titleId Title resource Id.
     * @param titleId Message resource Id.
     */
    public static void showAlertDialog(final Context context, int titleId, int messageId) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(titleId);
        alertDialog.setMessage(messageId);
        alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    /**
     * Displays a dialog with a title and no message.
     * @param context Application context.
     * @param titleId Title resource Id.
     */
    public static void showAlertDialogOnlyTitle(final Context context, int titleId) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(titleId);
        alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    /**
     * Displays a dialog when there is no internet connection. Prompts the user to enable any
     * network connection.
     * @param context Application context.
     */
    public static void showNetworkAlertDialog(final Context context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(R.string.no_internet);
        alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });
        alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
}