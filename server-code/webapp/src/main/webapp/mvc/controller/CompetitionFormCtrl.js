var competitionFormCtrlMod = angular.module('competitionFormCtrlMod', []);
/*
 * Dashboard
 */

competitionFormCtrlMod.controller('CompetitionFormCtrl', function ($scope, $location) {
    
    $scope.opened = [];
    $scope.formats = ['dd/MM/yyyy', 'dd-MM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
    $scope.format = $scope.formats[1];

    $scope.__init__ = function () {
        apixisi();
    };
    
    $scope.saveComp = function () {
        $location.path('/');
    };
    
    $scope.open = function ($event, e) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.opened[e] = true;
    };
    
    $scope.__init__();
});