app.controller('joinPointController', function ($scope, multipartFormService, joinPointService) {
    $scope.joinPoints = [];

    joinPointService.get('/joinPoint', function (data) {
        console.log('getJoinPoints works...');
        $scope.joinPoints = data;
    });

    $scope.newJoinPoint = {};
    $scope.newJoinPoints = [];

    $scope.addJoinPoint = function () {
        console.log('addNewJoinPoint works...');
        var entity = angular.copy($scope.newJoinPoint);
        $scope.newJoinPoints.push(entity);
        console.log(entity);
    };

    $scope.deleteJoinPoint = function (kks) {
        console.log('deleteJoinPoint  works...' + kks);
        joinPointService.delete('/joinPoint/' + kks)
    };

    $scope.sendNewEntriesToDatabase = function () {
        console.log('sendNewEntriesToDatabase works...');
        joinPointService.createOrUpdate('/joinPoint', $scope.newJoinPoints);
    };


    $scope.uploadedFile = {};

    $scope.submit = function () {
        console.log('submit file for parsing works... ');
        multipartFormService.post('/functionality/parse/joinPoints', $scope.uploadedFile, function (result) {
            $scope.newJoinPoints = result;
        })
    };
});