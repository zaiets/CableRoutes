app.service('cableService', function ($http) {

    console.log('core CableService initialized');

    this.createOrUpdate = function (uploadUrl, newEntities, result) {
        console.log('createOrUpdate works...');
        var rejected = [];
        for (var key in newEntities) {
            var current = newEntities[key];
            if (current.startName) {
                console.log('define start');
                current.start = $http.get('/equipment/' + current.startName);
            }
            if (current.endName) {
                console.log('define end');
                current.end = $http.get('/equipment/' + current.endName);
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
