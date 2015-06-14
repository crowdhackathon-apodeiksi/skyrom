/*
 * 
 * @dependencies authSrvMod, ngCookies
 */
var authSrvMod = angular.module('authSrvMod', ['ngCookies']);

authSrvMod.service('AuthSrv', function ($http, $cookieStore, $location) {
    
    /*
     * User login
     */    
    this.doLogin = function (uid, pass, onSuccess) {
        $http.TaxMachina.xhrCall(TaxMachina.helper.apiCall.auth.login, 'POST', {'email': uid, 'password': pass}, 
            function (response) {
                if(response.code === 0) {
                    var globals = {
                        SessionID: 'logged_in'
                    };
                    $cookieStore.put('globals', globals);
                }
                if(onSuccess) onSuccess(response);
            }, function (response) {
                response = {};
                response.status = -998;
                response.message = 'There was a problem on login. Please verify your credentials!';
                if(onSuccess) onSuccess(response);
            });
    };
    
    /*
     * User logout
     */
    this.doLogout = function (onSuccess) {
        $http.TaxMachina.xhrCall(TaxMachina.helper.apiCall.auth.logout, 'POST', {}, 
            function (response) {
                if(response.code === 0) {
                    $cookieStore.put('globals', {});
                }
                if(onSuccess) onSuccess(response);
            }, function (response) {
                response = {};
                response.status = -999;
                response.message = 'There was a problem on logout. Please try again!';
                if(onSuccess) onSuccess(response);
            });
    };
    
});