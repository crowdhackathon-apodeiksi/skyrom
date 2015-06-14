package skyrom.com.taxmachine.requests;

import android.content.Context;

import org.apache.http.client.methods.HttpPost;

public class Login extends Request {

    String email;
    String password;
    Callback callback;

    public Login(Context context, String email, String password, Callback callback) {
        super(context, "");
        this.email = email;
        this.password = password;
        this.callback = callback;
        method = HttpPost.METHOD_NAME;
        url = "http://172.16.177.128:8080/wserv/rs/login";
    }

    @Override
    protected void buildRequest() {
        urlParameters.put(EMAIL_FIELD, email);
        urlParameters.put(PASSWORD_FIELD, password);
    }

    @Override
    protected void handleResponse() {
        callback.loggedIn(this);
    }

    public interface Callback {
        public void loggedIn(Login request);
    }
}
