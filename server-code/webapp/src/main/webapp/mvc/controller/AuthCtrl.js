var authCtrlMod = angular.module('authCtrlMod', ['authSrvMod']);

authCtrlMod.controller('AuthCtrl', function ($scope, $location, AuthSrv, toaster) {
    $scope.login = function () {
        AuthSrv.doLogin($scope.username, $scope.password, function (response) {
            if(response.code === 0) {
                $location.path('/');
            } else {
                toaster.pop('error', "Oups!", response.message, 5000, 'trustedHtml');
            }
        });
    };
    
    $scope.logout = function () {
        AuthSrv.doLogout(function (response) {
            if(response.code === 0) {
                $location.path('/login');
            } else {
                toaster.pop('error', "Oups!", response.message, 5000, 'trustedHtml');
            }
        });
    };
    
});