app.controller('joinPointController' , function ($scope, joinPointService) {

    $scope.joinPoints = [];

    joinPointService.getJoinPoints(function (data) {
        console.log('joinPointController works... -> getJoinPoints');
        $scope.joinPoints = data;
    });

    $scope.newJoinPoint = {};

    $scope.addJoinPoint = function () {
        console.log('joinPointController works... -> addJoinPoint');
        var joinPoint = angular.copy($scope.newJoinPoint);
        console.log(newJoinPoint.x);
        $scope.joinPoints.push(joinPoint)
    }
});