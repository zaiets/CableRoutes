app.controller('basicController', function ($scope, basicService) {
    console.log('core basicController initialized');

    $scope.currentUser = {
        name: 'Anonymous',
        isAuth: name !== 'Anonymous'
    };

    basicService.getAuth(function (userName) {
        console.log('getAuth defined userName as: ' + userName);
        $scope.currentUser.name = userName;
    });

    $scope.logout = function () {
        console.log('basicController works... -> logout');
        basicService.logout();
    };

});