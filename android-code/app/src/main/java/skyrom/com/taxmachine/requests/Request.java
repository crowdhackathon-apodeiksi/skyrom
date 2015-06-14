package skyrom.com.taxmachine.requests;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import skyrom.com.taxmachine.LoginActivity;
import skyrom.com.taxmachine.SessionManager;
import skyrom.com.taxmachine.utils.DialogUtils;
import skyrom.com.taxmachine.utils.Utils;

/**
 * Abstract class used to group the common functionality of all webservice requests.
 * Subclasses should at least extend buildRequest(), handleResponse().
 * Although the function parseResponse() has a default implementation, usually subclasses should
 * have their own parseResponse() functionality. Uses an AsyncTask pool to execute tasks on
 * parallel. Subclasses need to implement Serializable if there is a need to serialize instances.
 * Subclasses must set {@link #action} inside the construct function.
 * The action is used as a key which value must be then defined in the /assets/config.properties file.
 */
@SuppressWarnings( "deprecation" )
abstract public class Request extends AsyncTask<HttpResponse, String, HttpResponse> implements Serializable {

    protected transient Map<String, Object> urlParameters = new HashMap();
    protected transient Context context;
    protected String url;
    protected String authToken;
    protected String action;
    protected String result;
    protected int errorCode;
    protected String server;
    protected String errorMessage = "";
    protected transient JSONObject response;
    protected String method;
    protected String resource;
    protected SessionManager sessionManager;

    public final static String PROPERTIES = "config.properties";
    public final static int TIMEOUT = 15000;

    public final static String AUTH_TOKEN_LEGACY_FIELD = "authtoken";
    public final static String AUTH_TOKEN_FIELD = "token";
    final static String EMAIL_FIELD = "email";
    final static String FIRSTNAME_FIELD = "first_name";
    final static String LASTNAME_FIELD = "last_name";
    final static String PROFILE_FIELD = "profile";
    final static String PASSWORD_FIELD = "password";
    final static String URL_FIELD = "url";
    final static String RECORDINGS_URL_FIELD = "recordings_url";
    final static String TOKEN_FIELD = "token";
    final static String LABEL_FIELD = "label";
    final static String OLD_PASSWORD_FIELD = "old_password";
    final static String NEW_PASSWORD_FIELD_1 = "new_password1";
    final static String NEW_PASSWORD_FIELD_2 = "new_password2";
    final static String RESULTS_FIELD = "results";
    final static String FILE_FIELD = "file";

    final static String PROPERTY_PORT = "port";
    final static String PROPERTY_API_VERSION = "apiVersion";

    final static int REQUEST_FAILED = -1;
    final static int ERROR_SERVICE_UNAVAILABLE = -2;
    final static int ERROR_NO_INTERNET_CONNECTION = -3;
    final static int ERROR_FAILED_TO_SEND_REQUEST = -4;
    final static int ERROR_PARSE_RESPONSE = -5;
    final static int ERROR_REQUEST_FAILED = -6;
    final static int ERROR_PERMISSIONS_ERROR = -7;
    final static int ERROR_UNAUTHORIZED = -8;

    final static String AUTHENTICATION_TOKEN_EXPIRED_MESSAGE = "Token has expired";

    final static int REQUEST_SUCCESS = 0;

    final static int VERIFICATION_FAILED = 2;

    /* Use it to get the live state in specific services */
    public final static String LIVE_ENABLED = "1";

    /* Use it to get the cached state in specific services */
    public final static String LIVE_DISABLED = "0";

    final static String RESOURCE_DELIMITER = "$";
    final static String PROPERTY_REST_SCHEME = "restScheme";
    static Properties properties;
    protected final static int[] validStatusCodes = new int[]{HttpStatus.SC_OK, HttpStatus.SC_CREATED,
            HttpStatus.SC_ACCEPTED, HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION, HttpStatus.SC_NO_CONTENT};
    protected int responseStatusCode;

    public Request() {
    }

    public Request(Context context, String authToken) {
        this.context = context;
        this.authToken = authToken;
        sessionManager = SessionManager.getInstance(context);
        errorCode = REQUEST_SUCCESS;
        method = HttpGet.METHOD_NAME;
    }

    /**
     * Should set the urlParameters.
     */
    abstract protected void buildRequest();

    /**
     * Set the webservice response or the errorMessage. Subclasses can call this
     * method and do further processing using the response object.
     */
    protected void parseResponse() {
        try {
            response = new JSONObject(result);
            if (hasError()) {
                while (response.keys().hasNext()) {
                    String key = response.keys().next();
                    /* Do not set errorMessage if key is url, it could be an actual successful json response */
                    if (key.equals(URL_FIELD)) {
                        break;
                    }
                    Object value = response.get(key);
                    if (value instanceof String) {
                        errorMessage = (String) value;
                        break;
                    } else if (value instanceof JSONObject) {
                        response = (JSONObject) value;
                    } else if (value instanceof JSONArray) {
                        JSONArray array = (JSONArray) value;
                        if (array.length() > 0) {
                            errorMessage = array.getString(0);
                        }
                        break;
                    }
                    else {
                        break;
                    }
                }
                if (errorCode == ERROR_UNAUTHORIZED) {
                    context.startActivity(new Intent(context, LoginActivity.class));
                    if (context instanceof Activity) {
                        ((Activity) context).finish();
                    }
                }
            }
        } catch (JSONException e) {
            errorCode = ERROR_PARSE_RESPONSE;
        }
    }

    /**
     * Called after the response has been retrieved.
     * Subclasses should define a callback and call every function needed inside this function
     * to update the UI.
     */
    abstract protected void handleResponse();

    /**
     * Executes the webservice request using AsyncTask.
     * Multiple tasks are executed in parallel using AsyncTask.THREAD_POOL_EXECUTOR.
     * As it's async nature obscures, this function does not return any result and subclasses
     * should register a callback and call the respective callback functions in
     * {@link #handleResponse()} to update the UI if needed.
     * @see {@link #doInBackground(org.apache.http.HttpResponse...)}
     */
    public void perform() {
        buildRequest();
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * Construct url if not already defined. Execute request using one of the available methods
     * by calling httpAction.
     * @return HTTP response object
     * @see #httpAction(String)
     */
    protected HttpResponse sendReq() {
        if (url == null) {
            String urlAction = properties.getProperty(action);
            String port = properties.getProperty(PROPERTY_PORT);
            String apiVersion = properties.getProperty(PROPERTY_API_VERSION);
            String scheme = properties.getProperty(PROPERTY_REST_SCHEME);
            this.url = scheme + "://" + server + ":" + port + apiVersion + urlAction;
            if (resource != null) {
                this.url = this.url.replace(RESOURCE_DELIMITER, resource);
            }
        }
        return httpAction(url);
    }

    /**
     * Create a String from the HTTP response.
     * @param httpResponse HTTP response object from response
     * @return String response
     * @throws IOException
     */
    protected String convertInputStreamToString(HttpResponse httpResponse) throws IOException {
        String result = "";
        if (httpResponse != null) {
            handleStatusCode(httpResponse);
            if (hasContent()) {
                InputStream inputStream = httpResponse.getEntity().getContent();
                Header[] headers = httpResponse.getHeaders("Set-Cookie");
                if (headers != null && headers.length > 0) {
                    sessionManager.setAuthToken(headers[0].getValue().split(";")[0].split("=")[1]);
                }
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                inputStream.close();
            }
        }
        return result;
    }

    /**
     * Set the errorCode depending on the response status code.
     * @param httpResponse HTTP response object from response
     */
    protected void handleStatusCode(HttpResponse httpResponse) {
        responseStatusCode = httpResponse.getStatusLine().getStatusCode();
        if (!isStatusCodeValid()) {
            switch (responseStatusCode) {
                case HttpStatus.SC_UNAUTHORIZED:
                    errorCode = ERROR_UNAUTHORIZED;
                    break;
                case HttpStatus.SC_FORBIDDEN:
                    errorCode = ERROR_PERMISSIONS_ERROR;
                    break;
                case HttpStatus.SC_SERVICE_UNAVAILABLE:
                    errorCode = ERROR_SERVICE_UNAVAILABLE;
                    break;
                default:
                    errorCode = REQUEST_FAILED;
            }
        }
    }

    private boolean isStatusCodeValid() {
        return true;
    }

    private boolean hasContent() {
        return responseStatusCode != HttpStatus.SC_NO_CONTENT;
    }

    @Override
    protected HttpResponse doInBackground(HttpResponse... httpResponse) {
        try {
            result = convertInputStreamToString(sendReq());
            if (!result.isEmpty()) {
                parseResponse();
            }
        } catch (IOException e) {
            errorCode = ERROR_FAILED_TO_SEND_REQUEST;
        } catch (NullPointerException e) {
            errorCode = ERROR_FAILED_TO_SEND_REQUEST;
        }
        return null;
    }

    /**
     * Redirect to the login view if the token has expired.
     * Show an error dialog if there the network is not reachable and let the callback handle
     * the response.
     * In any other case, let the callback handle the response.
     * @param httpResponse HTTP response object from response
     */
    @Override
    protected void onPostExecute(HttpResponse httpResponse) {
        if (hasNetworkError()) {
            /* If this is an activity context, show network alert dialog */
            if (context instanceof Activity) {
                DialogUtils.showNetworkAlertDialog(context);
            }
            handleResponse();
        } else {
            handleResponse();
        }

    }

    protected HttpResponse httpAction(String url) {
        HttpResponse httpResponse = null;
        try {
            final HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT);
            HttpClient httpClient = new DefaultHttpClient(httpParams);
            if (method.equals(HttpGet.METHOD_NAME)) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                if (urlParameters != null) {
                    Iterator<String> it = urlParameters.keySet().iterator();
                    while (it.hasNext()) {
                        String key = it.next();
                        String val = (String) urlParameters.get(key);
                        for (String value : val.split(",")) {
                            params.add(new BasicNameValuePair(key, value));
                        }
                    }
                }
                String paramsString = URLEncodedUtils.format(params, "UTF-8");
                if (!paramsString.isEmpty()) {
                    url +=  "?" + paramsString;
                }
                HttpGet httpRequest = new HttpGet(url);
                httpRequest.setHeader("Content-type", "application/json");
                if (authToken != null) {
                    httpRequest.setHeader("Authorization", "Token " + authToken);
                }
                httpResponse = httpClient.execute(httpRequest);
            } else {
                HttpPost httpRequest = new HttpPost(method);
                List<NameValuePair> params = new ArrayList();
                httpRequest.setHeader("Content-Type","application/x-www-form-urlencoded");
                httpRequest.setHeader("http.keepAlive", "false");
                if (urlParameters != null) {
                    Iterator<String> it = urlParameters.keySet().iterator();
                    while (it.hasNext()) {
                        String key = it.next();
                        String val = (String) urlParameters.get(key);
                        for (String value : val.split(",")) {
                            params.add(new BasicNameValuePair(key, value));
                        }
                    }
                }
                httpRequest = new HttpPost(url);
                httpRequest.setEntity(new UrlEncodedFormEntity(params));
                if (authToken != null && !authToken.isEmpty()) {
                    httpRequest.setHeader("Cookie", "JSESSIONID=" + authToken);
                }
                httpResponse = httpClient.execute(httpRequest);
            }
        } catch (Exception e) {
            errorCode = ERROR_FAILED_TO_SEND_REQUEST;
            if (!Utils.isNetworkAvailable(context)) {
                errorCode = ERROR_NO_INTERNET_CONNECTION;
            }

        }
        return httpResponse;
    }

    /**
     * Returns a boolean that can be used to determine if the request was successful.
     * @return a boolean to determine if the request was successful.
     */
    public final boolean hasError() {
        return errorCode != REQUEST_SUCCESS;
    }

    /**
     * Returns a boolean that can be used to determine if the request failed due to
     * network error. If the request failed due to a network error, an alert dialog is
     * created to indicate the network inaccessibility.
     * @see #httpAction(String)
     * @return a boolean to determine if the request was successful.
     */
    public final boolean hasNetworkError() {
        return errorCode == ERROR_NO_INTERNET_CONNECTION;
    }

    /**
     * Returns the error message as it is retrieved from the server response.
     * @return The error message returned from the server.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    public final boolean hasVerificationError() {
        return errorCode == VERIFICATION_FAILED;
    }

    public final boolean hasFailedToSendRequestError() {
        return errorCode == ERROR_FAILED_TO_SEND_REQUEST;
    }

    public final boolean hasServiceUnavailableError() {
        return errorCode == ERROR_SERVICE_UNAVAILABLE;
    }
}
