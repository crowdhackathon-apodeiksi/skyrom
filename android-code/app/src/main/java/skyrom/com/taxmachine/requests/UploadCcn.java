package skyrom.com.taxmachine.requests;

import android.content.Context;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;

import skyrom.com.taxmachine.Receipt;

public class UploadCcn extends Request {

    String ccn;
    Callback callback;
    Receipt receipt;

    public UploadCcn(Context context, String authToken, String ccn, Callback callback) {
        super(context, authToken);
        this.ccn = ccn;
        this.callback = callback;
        url = "http://172.16.177.128:8080/wserv/rs/v0/receipt/qrjoin";
        method = HttpPost.METHOD_NAME;
    }

    @Override
    protected void buildRequest() {
        urlParameters.put("ccn", ccn);
    }

    @Override
    protected void handleResponse() {
        callback.ccnUploaded(this, receipt);
    }

    @Override
    protected void parseResponse() {
        super.parseResponse();
        if (!hasError()) {
            try {
                receipt = new Receipt();
                JSONObject object = response.getJSONObject("data");
                receipt.setAfm(object.getString("tin"));
                receipt.setUploaded(false);
                receipt.setBitmap(null);
                receipt.setThumbnail(null);
                receipt.setPrice(object.getString("price"));
                receipt.setDate(object.getString("date"));
            } catch (JSONException e) {
                errorCode = ERROR_PARSE_RESPONSE;
            }
        }
    }

    public interface Callback {
        public void ccnUploaded(UploadCcn request, Receipt receipt);
    }
}
