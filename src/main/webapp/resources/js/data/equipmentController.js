app.controller('equipmentController', function ($scope, entityService, multipartFormService, equipmentService) {
    $scope.equipments = [];

    entityService.get('/equipment', function (data) {
        console.log('getEquipments works...');
        $scope.equipments = data;
    });

    $scope.newEquipment = {
        fullName: undefined,
        commonKks: undefined,
        x: undefined,
        y: undefined,
        z: undefined,
        joinPoint: undefined,
        joinPointKks: this.joinPoint? this.joinPoint.kksName : undefined,
        cableConnectionAddLength: 0
    };
    $scope.newEquipments = [];

    $scope.getAndShow = function () {
        console.log('getAndShow works...' + $scope.newEquipment.fullName);
        entityService.get('/equipment/' + $scope.newEquipment.fullName, function (data) {
            console.log(data);
            if (data) {
                $scope.newEquipment = data;
            }
        })
    };

    $scope.addEquipment = function () {
        console.log('addNewEquipment works...');
        var entity = angular.copy($scope.newEquipment);
        $scope.newEquipments.push(entity);
        console.log(entity);
    };

    $scope.sendNewEntriesToDatabase = function () {
        console.log('sendNewEntriesToDatabase works...');
        var uploadUrl = '/equipment';
        equipmentService.createOrUpdate(uploadUrl, $scope.newEquipments, function(rejected) {
            console.log('DB rejected: ' + rejected);
            $scope.newEquipments = rejected;
            entityService.get('/equipment', function (data) {
                console.log('getEquipments works...');
                $scope.equipments = data;
            });
        });
    };

    $scope.uploadedFile = {};

    $scope.submitEquip = function () {
        console.log('submitEquip file for parsing works...');
        multipartFormService.post('/functionality/parse/equipments', $scope.uploadedFile, function (result) {
            $scope.newEquipments = result;
        })
    };

    $scope.deleteFromDB = function (equipName) {
        console.log('deleteEquipment  works...' + equipName);
        entityService.delete('/equipment/' + equipName)
    };
});