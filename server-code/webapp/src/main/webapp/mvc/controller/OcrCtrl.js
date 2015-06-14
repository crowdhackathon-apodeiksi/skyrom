var ocrCtrlMod = angular.module('ocrCtrlMod', ['fileSrvMod']);
/*
 * 
 */

ocrCtrlMod.controller('OcrCtrl', function ($scope, $rootScope, $cookieStore, FileSrv, Upload, toaster) {

    $scope.imgSrc = '/wserv/rs/v0/file/preview/';
    $scope.uploadedImage = 's';
    
    $scope.price;
    $scope.afm;
    $scope.aa;
    $scope.radioValue = 'afm';
    
    $scope.$watch('file', function () {
        $scope.uploadFile();
    });

    $scope.__init__ = function () {
    };

    $scope.uploadFile = function () {
        if (!$scope.file)
            return;
        Upload.upload({
            url: TaxMachina.helper.apiCall.file.ocrUpload,
            fields: {},
            file: $scope.file
        }).progress(function (evt) {
            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
        }).success(function (response, status, headers, config) {
            if (response.code === 401) {
                $cookieStore.put('globals', {});
                $rootScope.checkSession();
            } else if (response.code === 0) {
                $scope.uploadedImage = response.data.path;
                toaster.pop('success', "Ok!", response.message, 5000, 'trustedHtml');
            } else {
                toaster.pop('error', "Oups!", response.message, 5000, 'trustedHtml');
            }
        });
    };

    $scope.selected = function (x) {
        if($scope.radioValue === 'afm') {
            $scope.afm = x;
        } else if($scope.radioValue === 'price') {
            $scope.price = x;
        } else if($scope.radioValue === 'aa') {
            $scope.aa = x;
        }
        console.log("selected", x);
    };
    
    $scope.uploadData = function () {
        FileSrv.upload2Ocr($scope.afm, $scope.price, $scope.aa, $scope.uploadedImage , function (response) {
             if (response.code === 0) {
                toaster.pop('success', "Ok!", response.message, 5000, 'trustedHtml');
            } else {
                toaster.pop('error', "Oups!", response.message, 5000, 'trustedHtml');
            }
        });
    };

    $scope.__init__();
});