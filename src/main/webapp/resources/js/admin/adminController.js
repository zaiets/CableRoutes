app.controller('adminController', function ($scope, adminService) {
    console.log('core adminController initialized');

    $scope.newUser = {
        login: 'login',
        email: 'a@a.com',
        password: '*****',
        firstName: 'FirstName',
        lastName: 'LastName',
        patronymic: 'Patronymic',
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
            .success(users.push(newUser));
        console.log(newUser);
    };
});