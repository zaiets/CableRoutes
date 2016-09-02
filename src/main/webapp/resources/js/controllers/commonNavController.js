app.controller('commonNavController', function ($scope, commonService) {
    console.log('core app.commonNavController initialized');

    $scope.currentUserName = 'Anonymous';

    this.authenticated = function() {
        commonService.getAuth(function(userName) {
            if (userName !== null) {
                $scope.currentUserName = userName;
                return true;
            }
            return false;
        });
    };

    this.logout = function() {
        console.log('commonService works... -> logout');
        $scope.currentUserName = 'Anonymous';
        commonService.logout();
    }
});