app.service('routeService', function ($http) {

    console.log('core RouteService initialized');

    this.createOrUpdate = function (uploadUrl, newEntities, result) {
        console.log('createOrUpdate works...');
        var rejected = [];
        for (var key in newEntities) {
            var current = newEntities[key];
            $http.put(uploadUrl, current)
                .success(function () {
                    console.log('On data pushed:' + current + ' success');
                })
                .error(function () {
                    console.log('On data pushed:' + current + ' rejected');
                    rejected.push(current);
                });
        }
        result(rejected);
    };

});
