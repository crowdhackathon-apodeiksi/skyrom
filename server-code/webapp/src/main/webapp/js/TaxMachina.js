/*
 * 
 */
var TaxMachina = function () {
    'use strict';
    var _core_ = {
        _wsPath: '/wserv',
        helper: {
            apiCall: {
                auth: {
                    login: '/wserv/rs/login',
                    logout: '/wserv/rs/logout'
                },
                file: {
                    singleUpload : '/wserv/rs/v0/receipt/scan',
                    ocrUpload : '/wserv/rs/v0/file/upload'
                },
                ocrUpload: '/wserv/rs/v0/receipt/scanOCR'
            }
        },
        utils: {
            ajaxCall: function (url, method, data, retType, onSuccess, onError) {
                $.ajax({
                    type: method,
                    url: url,
                    data: data,
                    dataType: retType,
                    success: function (data) {
                        if (onSuccess)
                            onSuccess(data);
                    },
                    error: function (err) {
                        if (onError)
                            onError(err);
                    }
                });
            }
        }
    };
    return _core_;
}();