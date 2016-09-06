app.controller('joinPointController', function ($scope, multipartFormService, joinPointService) {
    $scope.joinPoints = [];

    joinPointService.getJoinPoints(function (data) {
        console.log('joinPointController works... -> getJoinPoints');
        $scope.joinPoints = data;
    });

    $scope.newJoinPoint = {};
    $scope.newJoinPoints = [];

    $scope.addJoinPoint = function () {
        console.log('joinPointController works... -> addNewJoinPoint ');
        var joinPoint = angular.copy($scope.newJoinPoint);
        $scope.newJoinPoints.push(joinPoint);
        console.log(joinPoint);
    };

    $scope.sendNewEntriesToDatabase = function () {
        console.log('joinPointController works... -> sendNewEntriesToDatabase');
        var uploadUrl = '/joinPoint';
        joinPointService.createOrUpdate(uploadUrl, $scope.newJoinPoints, function (result) {
            var joinPointsList = angular.copy($scope.joinPoints);
            joinPointsList.concat(result);
            $scope.joinPoints = joinPointsList;
        });

    };


    $scope.uploadedFile = {};

    $scope.submit = function () {
        console.log('joinPointController works... -> submit file for parsing');
        var uploadUrl = '/functionality/parse/joinPoints';
        multipartFormService.post(uploadUrl, $scope.uploadedFile, function (result) {
            $scope.newJoinPoints = result;
        })
    };
});