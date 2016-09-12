app.service('equipmentService', function ($http) {

    console.log('core EquipmentService initialized');

    this.createOrUpdate = function (uploadUrl, newEntities, result) {
        console.log('createOrUpdate works...');
        var rejected = [];
        for (var key in newEntities) {
            var current = newEntities[key];
            $http.put(uploadUrl, current)
                .success(function (response) {
                    console.log('On data pushed:' + current + ' server responded: ' + response.status);
                })
                .error(function (response) {
                    console.log('On data pushed:' + current + ' server responded: ' + response.status);
                    rejected.push(current);
                });
        }
        result(rejected);
    };

});
