package skyrom.com.taxmachine;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    public final static String SERVER_KEY = "server";
    private static SharedPreferences sharedpreferences;
    private static SharedPreferences.Editor editor;
    private static SessionManager sm;

    private final static String KUNA_PREFERENCES = "com.mobics.kuna";
    private final static String REGISTRATION_KEY = "registrationId";
    private final static String AUTH_TOKEN_KEY = "authToken";
    private final static String USER_ID_KEY = "userID";
    private final static String VERSION_KEY = "version";
    private final static String PASSWORD_KEY = "password";
    private final static String AUTH_LAST_UPDATED_KEY = "lastUpdatedAuth";

//    private static User user;

    private SessionManager(Context context) {
        sharedpreferences = context.getSharedPreferences(KUNA_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    public static SessionManager getInstance(Context context) {
        if (sm == null) {
            sm = new SessionManager(context);
        }
        return sm;
    }

    public void setUserEmail(String userID) {
        editor.putString(USER_ID_KEY, userID);
        editor.commit();
    }

    public void setPassword(String password) {
        editor.putString(PASSWORD_KEY, password);
        editor.commit();
    }

    public String getPassword() {
        return sharedpreferences.getString(PASSWORD_KEY, "");
    }

    public void setServer(String server) {
        editor.putString(SERVER_KEY, server);
        editor.commit();
    }

    public String getServer() {
        return sharedpreferences.getString(SERVER_KEY, null);
    }

    public String getUserEmail() {
        return sharedpreferences.getString(USER_ID_KEY, null);
    }

    public void setAuthToken(String authToken) {
        editor.putString(AUTH_TOKEN_KEY, authToken);
        editor.commit();
    }

    public void setRegistrationId(String registrationId) {
        editor.putString(REGISTRATION_KEY, registrationId);
        editor.commit();
    }

    public String getRegistrationId() {
        String registrationId = sharedpreferences.getString(REGISTRATION_KEY, null);
        return registrationId != null ? registrationId : "";
    }

    public int getAppVersion() {
        return sharedpreferences.getInt(VERSION_KEY, Integer.MIN_VALUE);
    }

    public String getAuthToken() {
        return sharedpreferences.getString(AUTH_TOKEN_KEY, null);
    }

    public void setAuthLastUpdated(long date) {
        editor.putLong(AUTH_LAST_UPDATED_KEY, date);
        editor.commit();
    }

    public long getAuthLastUpdated() {
        return sharedpreferences.getLong(AUTH_LAST_UPDATED_KEY, 0);
    }

    public boolean isLoggedIn() {
        return sharedpreferences.getString(AUTH_TOKEN_KEY, null) != null;
    }

    public void logOut() {
        editor.remove(AUTH_TOKEN_KEY);
        editor.remove(USER_ID_KEY);
        editor.remove(PASSWORD_KEY);
        editor.remove(AUTH_LAST_UPDATED_KEY);
        editor.commit();
//        user = null;
    }

//    public static User getUser(Context context) {
//        if (user == null) {
//            user = UserTable.getInstance(context).getUser();
//        }
//        return user;
//    }
//
//    public static void setUser(User user) {
//        SessionManager.user = user;
//    }
}
