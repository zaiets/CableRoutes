app.service('entityService', function ($http) {

    console.log('core app.entityService initialized');

    this.get = function (targetUrl, result) {
        console.log('getEntities works...');
        $http.get(targetUrl)
            .then(function (response) {
                console.log(response);
                if (response.status == 200) {
                    result(response.data);
                }
            });
    };

    this.createOrUpdate = function (uploadUrl, newEntities) {
        console.log('createOrUpdate works...');
        for (var key in newEntities) {
            $http.put(uploadUrl, newEntities[key])
                .success(function (response) {
                    console.log('On data pushed:' + newEntities[key] + ' server responded: ' + response.status);
                })
        }
        alert('OK');
        location.reload();
    };


    this.delete = function (uploadUrl) {
        console.log('delete works...' + uploadUrl);
        $http.delete(uploadUrl)
            .success(function (response) {
                console.log('server responded: ' + response.status);
            });
        alert('OK');
        location.reload();
    };

})
;
