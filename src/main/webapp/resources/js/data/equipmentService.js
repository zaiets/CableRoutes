app.service('equipmentService', function ($http) {

    console.log('core EquipmentService initialized');

    this.getEquipments = function (result) {
        console.log('EquipmentService works... -> getEquipments');
        $http.get('/equipment')
            .then(function (response) {
                console.log(response);
                if (response.status == 200) {
                    result(response.data);
                }
            });
    };

    this.createOrUpdate = function (uploadUrl, newEntities, result) {
        console.log('EquipmentService works... -> createOrUpdate');
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
