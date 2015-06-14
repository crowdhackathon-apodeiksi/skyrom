var fileSrvMod = angular.module('fileSrvMod', []);

fileSrvMod.service('FileSrv', function ($http, $cookieStore) {

    this.upload2Ocr = function (afm, price, aa, path, onSuccess, onError) {
         $http.TaxMachina.xhrCall(TaxMachina.helper.apiCall.ocrUpload, 'POST', 
         {'tin': afm, 'price': price, 'serial': aa, 'path': path}, 
            function (response) {
                if(onSuccess) onSuccess(response);
            }, function (response) {
                if(onError) onError(response);
            });
    };

});