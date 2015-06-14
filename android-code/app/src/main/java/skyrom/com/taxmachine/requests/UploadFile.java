package skyrom.com.taxmachine.requests;

import android.content.Context;
import android.util.Base64;

import org.apache.http.client.methods.HttpPost;

import skyrom.com.taxmachine.Receipt;

public class UploadFile extends Request {

    byte[] imageBytes;
    Callback callback;
    Receipt receipt;

    public UploadFile(Context context, String authToken, byte[] imageBytes, Receipt receipt,
                      Callback callback) {
        super(context, authToken);
        this.imageBytes = imageBytes;
        method = HttpPost.METHOD_NAME;
        this.receipt = receipt;
        url = "http://172.16.177.128:8080/wserv/rs/v0/receipt/scan/android";
        this.callback = callback;
    }

    @Override
    protected void buildRequest() {
        urlParameters.put(FILE_FIELD, Base64.encodeToString(imageBytes, Base64.DEFAULT));
    }

    @Override
    protected void handleResponse() {
        callback.fileUploaded(this, receipt);
    }

    public interface Callback {
        public void fileUploaded(UploadFile request, Receipt receipt);
    }
}
