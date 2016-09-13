app.controller('routeController', function ($scope, entityService, multipartFormService, routeService) {
    $scope.routes = [];

    entityService.get('/route', function (data) {
        console.log('get all items works...');
        $scope.routes = data;
    });

    $scope.newRoute = {
        kksName: undefined,
        routeType: undefined,
        routeTypeMarker: this.routeType? this.routeType.marker : undefined,
        length: undefined,
        height: undefined,
        shelvesCount: undefined,
        joinPointFirst: undefined,
        joinPointFirstKks: this.joinPointFirst? this.joinPointFirst.kksName : undefined,
        joinPointSecond: undefined,
        joinPointSecondKks: this.joinPointSecond? this.joinPointSecond.kksName : undefined,
    };
    $scope.newRoutes = [];

    $scope.getAndShow = function () {
        console.log('getAndShow works...' + $scope.newRoute.kksName);
        entityService.get('/route/' + $scope.newRoute.kksName, function (data) {
            console.log(data);
            if (data) {
                $scope.newEquipment = data;
            }
        })
    };

    $scope.addNewToTemp = function () {
        console.log('addNewItem to temp collection works...');
        var entity = angular.copy($scope.newRoute);
        $scope.newRoutes.push(entity);
        console.log(entity);
    };

    $scope.sendNewEntriesToDatabase = function () {
        console.log('sendNewEntriesToDatabase works...');
        var uploadUrl = '/route';
        routeService.createOrUpdate(uploadUrl, $scope.newRoutes, function(rejected) {
            console.log('DB rejected: ' + rejected);
            $scope.newRoutes = rejected;
            entityService.get('/route', function (data) {
                console.log('getAll works...');
                $scope.routes = data;
            });
        });
    };

    $scope.uploadedFile = {};

    $scope.submit = function () {
        console.log('submit file for parsing works...');
        multipartFormService.post('/functionality/parse/routes', $scope.uploadedFile, function (result) {
            $scope.newRoutes = result;
        })
    };

    $scope.deleteFromDB = function (kks) {
        console.log('deleteFromDB  works...' + kks);
        entityService.delete('/route/' + kks)
    };
});