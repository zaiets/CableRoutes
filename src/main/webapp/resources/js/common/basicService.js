app.service('basicService', function ($http) {

    console.log('core basicService initialized');

    this.getAuth = function (result) {
        console.log('basicService works... -> getAuth');
        $http.get('/current')
            .then(function (response) {
                console.log(response.data);
                result(response.data);
            });
    };

    this.logout = function () {
        console.log('basicService works... -> logout');
        $http.get('/logout')
            .then(function (response) {
                console.log(response.data);
                location.reload();
            });
    };


});
