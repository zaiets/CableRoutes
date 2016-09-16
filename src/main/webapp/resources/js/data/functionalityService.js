app.service('functionalityService', function ($http) {
    this.equipmentsWithNewPoints = function(result) {
        console.log('get works...');
        $http.put('/define/pointsbyequips')
            .then(function (response) {
                console.log('server responded status ' + response.status);
                if (response.status == 200) result(response.data);
            });
    }

});
