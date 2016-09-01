app.controller('joinPointController', function ($scope, multipartForm, joinPointService) {
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
    };

    $scope.uploadedFile = {};

    $scope.submit = function(){
        uploadUrl = 'http://localhost:8080/functionality/parse/joinPoints';
        multipartForm.post(uploadUrl, $scope.uploadedFile);
    }

});