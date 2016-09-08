app.controller('basicController', function ($scope, basicService) {
    console.log('core basicController initialized');

    $scope.currentUser = {
        name: 'Anonymous',
        role: 'USER'
    };

    basicService.getAuth(function (data) {
        console.log('getAuth defined userName as: ' + data);
        $scope.currentUser.name = data[0];
        $scope.currentUser.role = data[1];
    });

    $scope.logout = function () {
        console.log('basicController works... -> logout');
        basicService.logout();
    };

});