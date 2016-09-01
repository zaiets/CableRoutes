app.controller('joinPointController' , function ($scope, joinPointService) {

    console.log('joinPointController works...');
    $scope.joinPoints = [];

    joinPointService.getJoinPoints(function (data) {
        $scope.joinPoints = data;
    });

    $scope.newJoinPoint = {};

    $scope.addJoinPoint = function () {
        console.log(newJoinPoint);
        var joinPoint = angular.copy($scope.newJoinPoint);
        $scope.joinPoints.push(joinPoint)
    }
});