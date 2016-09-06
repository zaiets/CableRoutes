app.controller('basicController', function ($scope, commonService) {
    console.log('core app.commonNavController initialized');

    $scope.currentUser = {
        name: function () {
            console.log('this.authenticated called');
            commonService.getAuth(function (userName) {
                console.log('this.authenticated got ' + userName);
                return userName;
            });
        }
    };

    $scope.logout = function () {
        console.log('commonService works... -> logout');
        commonService.logout();
    }
});