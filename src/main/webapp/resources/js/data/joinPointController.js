app.controller('joinPointController', function ($scope, entityService, multipartFormService) {
    $scope.joinPoints = [];

    entityService.get('/joinPoint', function (data) {
        console.log('get all items works...');
        $scope.joinPoints = data;
    });

    $scope.newJoinPoint = {
        kksName: undefined,
        x: undefined,
        y: undefined,
        z: undefined
    };
    $scope.newJoinPoints = [];

    $scope.getAndShow = function () {
        console.log('getAndShow works...' + $scope.newJoinPoint.kksName);
        entityService.get('/joinPoint/' + $scope.newJoinPoint.kksName, function (data) {
            console.log(data);
            if (data) {
                $scope.newJoinPoint = data;
            }
        })
    };

    $scope.addNewToTemp = function () {
        console.log('addNewItem to temp collection works...');
        var entity = angular.copy($scope.newJoinPoint);
        $scope.newJoinPoints.push(entity);
        console.log(entity);
    };

    $scope.sendNewEntriesToDatabase = function () {
        console.log('sendNewEntriesToDatabase works...');
        entityService.createOrUpdate('/joinPoint', $scope.newJoinPoints)
    };


    $scope.uploadedFile = {};

    $scope.submit = function () {
        console.log('submit file for parsing works... ');
        multipartFormService.post('/functionality/parse/joinPoints', $scope.uploadedFile, function (result) {
            $scope.newJoinPoints = result;
        })
    };

    $scope.deleteFromDB = function (kks) {
        console.log('deleteFromDB  works...' + kks);
        entityService.delete('/joinPoint/' + kks)
    };
});