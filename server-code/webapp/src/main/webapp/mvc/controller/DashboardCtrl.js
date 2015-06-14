var dashboardCtrlMod = angular.module('dashboardCtrlMod', []);
/*
 * Dashboard
 */

dashboardCtrlMod.controller('DashboardCtrl', function ($scope) {
    

    $scope.__init__ = function () {
        initReceiptChart(); 
    };
    
    $scope.__init__();
});