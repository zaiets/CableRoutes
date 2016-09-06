app.service('basicService', function ($http) {

    console.log('core app.commonService initialized');

    this.getAuth = function (result) {
        console.log('commonService works... -> getAuth');
        $http.get('/currentuser')
            .then(function (response) {
                console.log(response.data);
                result(response.data);
            });
    };

    this.logout = function () {
        console.log('commonService works... -> logout');
        $http.get('/logout')
            .then(function (response) {
                console.log(response.data);
                location.assign(response.data);
            });
    }

});
