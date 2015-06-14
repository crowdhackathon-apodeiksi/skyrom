var TaxMachinaApp = angular.module('TaxMachinaApp', ['ngRoute', 'ngCookies', 'ngAnimate', 'ui.bootstrap', 'blockUI', 'ngFileUpload',
    'authCtrlMod', 'toaster', 'competitionFormCtrlMod',
    'dashboardCtrlMod', 'receiptUploadCtrlMod', 'ocrCtrlMod']);

TaxMachinaApp.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.
            when('/login', {
                title: 'User Login',
                templateUrl: 'mvc/view/login.html',
                controller: 'AuthCtrl',
                publicAccess : true
            }).
            when('/', {
                title: 'Dashboard',
                templateUrl: 'mvc/view/dashboard.html',
                controller: 'DashboardCtrl',
                active: ['dashboard']
            }).
            when('/nav/dashboard', {
                title: 'Dashboard',
                templateUrl: 'mvc/view/dashboard.html',
                controller: 'DashboardCtrl',
                active: ['dashboard']
            }).
            when('/nav/receipt/upload', {
                title: 'Upload',
                templateUrl: 'mvc/view/receipt_upload.html',
                controller: 'ReceiptUploadCtrl',
                active: ['receipt']
            }).
            when('/nav/ocr', {
                title: 'Ocr',
                templateUrl: 'mvc/view/ocr.html',
                controller: 'OcrCtrl',
                active: ['receipt']
            }).
            when('/nav/compform', {
                title: 'Competition Form',
                templateUrl: 'mvc/view/comp_form.html',
                controller: 'CompetitionFormCtrl',
                active: ['shop']
            }).
            when('/404', {
                title: '404',
                templateUrl: 'mvc/view/404.html',
                publicAccess : true
            }).
            otherwise({
                redirectTo: '/404'
            });
    }
]);

TaxMachinaApp.run(['$http', '$location', '$rootScope', '$cookieStore', function ($http, $location, $rootScope, $cookieStore) {

    $rootScope.checkSession = function () {
        $rootScope.globals = $cookieStore.get('globals') || {};
        if (!$rootScope.globals.SessionID) {
            $location.path('/login');
        }
    };

    $http.TaxMachina = {
        xhrCall: function (url, method, data, onSuccess, onError) {
            $rootScope.checkSession();
            $http({
                url: url,
                method: method,
                params: data,
                headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8;'}
            }).success(function (response, status, headers, config) {
                if (response.status === 401 || response.exception === 'Authenticated access required') {
                    $cookieStore.put('globals', {});
                    $rootScope.checkSession();
                } else if (onSuccess) {
                    onSuccess(response);
                }
            }).error(function (response, status, headers, config) {
                if (onError) onError(response);
            });
        }
    };
    
    $http.objectToUri = function (o) {
       for(var i in o)o[i] = encodeURIComponent(o[i]);
       return o;
    }; 

    $rootScope.$on("$locationChangeStart", function (event, next, current) {
        
        var access = next.publicAccess || false;
        if (!access) {
            $rootScope.checkSession();   
        }
        
    });
    
    $rootScope.$on("$routeChangeStart", function (event, next, current) {
                
    });

    $rootScope.$on('$routeChangeSuccess', function (event, current, previous) {

        if (current.hasOwnProperty('$$route')) {
            $rootScope.title = current.$$route.title;
        }

        if (current.hasOwnProperty('$$route')) {
            $rootScope.active = current.$$route.active;
        }

    });

}]);