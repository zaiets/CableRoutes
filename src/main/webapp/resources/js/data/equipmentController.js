app.controller('equipmentController', function ($scope, multipartFormService, equipmentService) {
    $scope.equipments = [];

    equipmentService.getEquipments(function (data) {
        console.log('EquipmentController works... -> getEquipments');
        $scope.equipments = data;
    });

    $scope.newEquipment = {};
    $scope.newEquipments = [];

    $scope.addEquipment = function () {
        console.log('EquipmentController works... -> addNewEquipment');
        var entity = angular.copy($scope.newEquipment);
        $scope.newEquipments.push(entity);
        console.log(entity);
    };

    $scope.sendNewEntriesToDatabase = function () {
        console.log('EquipmentController works... -> sendNewEntriesToDatabase');
        var uploadUrl = '/equipment';
        equipmentService.createOrUpdate(uploadUrl, $scope.newEquipments, function(rejected) {
            $scope.newEquipments = rejected;
        });
    };


    $scope.uploadedFile = {};

    $scope.submit = function () {
        console.log('EquipmentController works... -> submit file for parsing');
        var uploadUrl = '/functionality/parse/equipments';
        multipartFormService.post(uploadUrl, $scope.uploadedFile, function (result) {
            $scope.newEquipments = result;
        })
    };
});