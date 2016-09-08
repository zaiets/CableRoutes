app.service('joinPointService', function ($http) {

    console.log('core app.joinPointService initialized');

    this.getJoinPoints = function (result) {
        console.log('joinPointService works... -> getJoinPoints');
        $http.get('/joinPoint')
            .then(function (response) {
                console.log(response);
                if (response.status == 200) {
                    result(response.data);
                }
            });
    };

    this.createOrUpdate = function (uploadUrl, newEntities) {
        console.log('joinPointService works... -> createOrUpdate');
        for (var key in newEntities) {
            ($http.put(uploadUrl, newEntities[key])
                .then(function (response) {
                    console.log('On data pushed:' + newEntities[key] + ' server responded: ' + response.status);
                }));
        }
        alert('All done!');
        location.reload();
    };

});
