app.controller('routeTypeController', function ($scope, entityService) {
    $scope.routeTypes = [];

    entityService.get('/routeType', function (data) {
        console.log('get all items works...');
        $scope.routeTypes = data;
    });

    $scope.newRouteType = {
        marker: undefined,
        name: undefined
    };
    $scope.newRouteTypes = [];

    $scope.getAndShow = function () {
        console.log('getAndShow works...' + $scope.newRouteType.marker);
        entityService.get('/routeType/' + $scope.newRouteType.marker, function (data) {
            console.log(data);
            if (data) {
                $scope.newRouteType = data;
            }
        })
    };

    $scope.addNewToTemp = function () {
        console.log('addNewItem to temp collection works...');
        var entity = angular.copy($scope.newRouteType);
        $scope.newRouteTypes.push(entity);
        console.log(entity);
    };

    $scope.sendNewEntriesToDatabase = function () {
        console.log('sendNewEntriesToDatabase works...');
        entityService.createOrUpdate('/routeType', $scope.newRouteTypes)
    };

    $scope.deleteFromDB = function (marker) {
        console.log('deleteFromDB  works...' + marker);
        entityService.delete('/routeType/' + marker)
    };
});