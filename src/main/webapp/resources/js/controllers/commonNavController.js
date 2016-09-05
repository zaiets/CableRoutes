app.controller('commonNavController', function ($scope, commonService) {
    console.log('core app.commonNavController initialized');

    $scope.currentUser = {
        name: function () {
            console.log('this.authenticated called');
            commonService.getAuth(function (userName) {
                console.log('this.authenticated got ' + userName);
                if (userName != null) {
                    return userName;
                } else {
                    return 'Anonymous';
                }
            });
        },
        isAuthenticated: name !== 'Anonymous'
    };

    $scope.logout = function () {
        console.log('commonService works... -> logout');
        commonService.logout();
    }
});