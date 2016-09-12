app.service('equipmentService', function ($http) {

    console.log('core EquipmentService initialized');

    this.createOrUpdate = function (uploadUrl, newEntities, result) {
        console.log('createOrUpdate works...');
        var rejected = [];
        for (var key in newEntities) {
            var current = newEntities[key];
            $http.put(uploadUrl, current)
                .then(function (response) {
                    console.log('On data pushed:' + current + ' server responded: ' + response.status);
                    if (response.status != 200) {
                        rejected.push(current);
                    }
                });
            result(rejected);
        }
    };

});
