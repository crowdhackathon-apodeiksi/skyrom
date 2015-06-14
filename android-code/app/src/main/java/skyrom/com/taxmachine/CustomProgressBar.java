package skyrom.com.taxmachine;

import android.app.ProgressDialog;
import android.content.Context;


public class CustomProgressBar {

    Context context;
    ProgressDialog dialog;

    public CustomProgressBar(Context context) {
        this.context = context;
        dialog = new ProgressDialog(context);
    }

    public ProgressDialog show() {
        try {
            dialog.show();
        } catch (Exception e) {
        }
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_bar);
        return dialog;
    }

    public void cancel(){
        try {
            dialog.cancel();
        } catch (Exception e){
        }
    }
}
