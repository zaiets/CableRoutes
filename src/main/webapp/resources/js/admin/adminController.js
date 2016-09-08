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
        adminService.addNewUser(newUser);
    };

    $scope.userForUpdate = {
        login: undefined,
        email: undefined,
        password: undefined,
        matchingPassword: undefined,
        firstName: undefined,
        lastName: undefined,
        patronymic: undefined,
        role: undefined
    };

    $scope.getAndShowUser = function () {
        console.log('adminController works... -> getAndShowUser: ' + $scope.userForUpdate.login);
        adminService.getUser($scope.userForUpdate.login, function (data) {
            console.log(data);
            if (data) {
                $scope.userForUpdate = data;
                $scope.userForUpdate.password = undefined;
                $scope.userForUpdate.matchingPassword = undefined;
            }
        })
    };

    $scope.updateUser = function () {
        console.log('adminController works... -> updateUser');
        var updatedUser = angular.copy($scope.userForUpdate);
        adminService.updateUser(updatedUser)
    };

    $scope.delete = function (loginToDelete) {
        console.log('adminController works... -> delete user: ' + loginToDelete);
        adminService.delete(loginToDelete);
    };
});