app.service('joinPointService', function ($http) {

    console.log('core app.joinPointService initialized');

    this.getJoinPoints = function (result) {
        console.log('joinPointService works... -> getJoinPoints');
        $http.get('/joinPoint')
            .then(function (response) {
                console.log(response.data);
                result(response.data);
            });
    };

    this.createOrUpdate = function (uploadUrl, newJoinPoints, result) {
        console.log('joinPointService works... -> createOrUpdate');
        for (var joinPoint in newJoinPoints) {
            ($http.put(uploadUrl)
                .then(function (response) {
                    console.log(response.data);
                    result(response.data);
                }));
        }
    };

});
