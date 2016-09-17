app.service('functionalityService', function ($http) {
    this.defineEquipmentsWithNewPoints = function(result) {
        console.log('defineEquipmentsWithNewPoints works...');
        $http.put('/functionality/define/pointsbyequips')
            .then(function (response) {
                console.log('server responded status ' + response.status);
                if (response.status == 200) result(response.data);
            });
    }

});
