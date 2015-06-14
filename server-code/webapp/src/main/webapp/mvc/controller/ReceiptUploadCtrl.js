var receiptUploadCtrlMod = angular.module('receiptUploadCtrlMod', ['fileSrvMod']);
/*
 * 
 */

receiptUploadCtrlMod.controller('ReceiptUploadCtrl', function ($scope, $rootScope, $cookieStore, FileSrv, Upload, toaster) {

    $scope.$watch('file', function () {
        $scope.uploadFile();
    });

    $scope.__init__ = function () {
    };

    $scope.uploadFile = function () {
        if(!$scope.file) return ;
        Upload.upload({
            url: TaxMachina.helper.apiCall.file.singleUpload,
            fields: {},
            file: $scope.file
        }).progress(function (evt) {
            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
        }).success(function (response, status, headers, config) {
            if (response.code === 401) {
                $cookieStore.put('globals', {});
                $rootScope.checkSession();
            }else if(response.code === 0) {
                toaster.pop('success', "Ok!", response.message, 5000, 'trustedHtml');
            } else {
                toaster.pop('error', "Oups!", response.message, 5000, 'trustedHtml');
            }
        });
    };

    $scope.__init__();
});