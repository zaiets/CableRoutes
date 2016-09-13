app.service('routeService', function ($http) {

    console.log('core RouteService initialized');

    this.createOrUpdate = function (uploadUrl, newEntities, result) {
        console.log('createOrUpdate works...');
        var rejected = [];
        for (var key in newEntities) {
            var current = newEntities[key];
            if (current.routeTypeMarker) {
                console.log('define route type');
                current.routeType = $http.get('/routeType/' + current.routeTypeMarker);
            }
            if (current.joinPointFirstKks) {
                console.log('define joinPoint #1');
                current.joinPointFirst = $http.get('/joinPoint/' + current.joinPointFirstKks);
            }
            if (current.joinPointSecondKks) {
                console.log('define joinPoint #2');
                current.joinPointSecond = $http.get('/joinPoint/' + current.joinPointSecondKks);
            }
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
