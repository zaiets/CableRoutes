app.controller('equipmentController', function ($scope, entityService, multipartFormService, equipmentService) {
    $scope.equipments = [];

    entityService.get('/equipment', function (data) {
        console.log('get all items works...');
        $scope.equipments = data;
    });

    $scope.newRoute = {
        fullName: undefined,
        commonKks: undefined,
        x: undefined,
        y: undefined,
        z: undefined,
        joinPoint: undefined,
        joinPointKks: this.joinPoint? this.joinPoint.kksName : undefined,
        cableConnectionAddLength: 0
    };
    $scope.newRoutes = [];

    $scope.getAndShow = function () {
        console.log('getAndShow works...' + $scope.newRoute.fullName);
        entityService.get('/equipment/' + $scope.newRoute.fullName, function (data) {
            console.log(data);
            if (data) {
                $scope.newRoute = data;
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
        var uploadUrl = '/equipment';
        equipmentService.createOrUpdate(uploadUrl, $scope.newRoutes, function(rejected) {
            console.log('DB rejected: ' + rejected);
            $scope.newRoutes = rejected;
            entityService.get('/equipment', function (data) {
                console.log('getEquipments works...');
                $scope.equipments = data;
            });
        });
    };

    $scope.uploadedFile = {};

    $scope.submit = function () {
        console.log('submit file for parsing works...');
        multipartFormService.post('/functionality/parse/equipments', $scope.uploadedFile, function (result) {
            $scope.newRoutes = result;
        })
    };

    $scope.deleteFromDB = function (equipName) {
        console.log('deleteFromDB  works...' + equipName);
        entityService.delete('/equipment/' + equipName)
    };
});