app.controller('adminController', function ($scope, adminService) {
    console.log('core adminController initialized');

    $scope.newUser = {
        login: undefined,
        email: undefined,
        password: undefined,
        matchingPassword: undefined,
        firstName: undefined,
        lastName: undefined,
        patronymic: undefined,
        role: 'USER'
    };

    $scope.users = [];
    adminService.getUsers(function (data) {
        console.log('adminController works... -> getUsers');
        $scope.users = data;
    });

    $scope.addNewUser = function () {
        console.log('adminController works... -> addNewUser');
        var newUser = angular.copy($scope.newUser);
        adminService.addNewUser(newUser)
    };

    $scope.delete = function (loginToDelete) {
        console.log('adminController works... -> delete user: ' + loginToDelete);
        adminService.delete(loginToDelete)
    };
});