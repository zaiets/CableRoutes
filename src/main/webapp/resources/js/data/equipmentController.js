app.controller('equipmentController', function ($scope, entityService, multipartFormService, equipmentService) {
    $scope.equipments = [];

    entityService.get('/equipment', function (data) {
        console.log('getEquipments works...');
        $scope.equipments = data;
    });

    $scope.newEquipment = {};
    $scope.newEquipments = [];

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
            $scope.newEquipments = rejected;
        });
    };

    $scope.uploadedFile = {};

    $scope.submitEquip = function () {
        console.log('submitEquip file for parsing works...');
        multipartFormService.post('/functionality/parse/equipments', $scope.uploadedFile, function (result) {
            $scope.newEquipments = result;
        })
    };

    $scope.deleteEquipment = function (equipName) {
        console.log('deleteEquipment  works...' + equipName);
        entityService.delete('/equipment/' + equipName)
    };
});