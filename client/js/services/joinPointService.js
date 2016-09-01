app.service('joinPointService', function ($http) {

    console.log('core app.joinPointService initialized');

    this.getJoinPoints = function (result) {
        $http.get('http://localhost:8080/joinPoint/')
            .then(function (response) {
                console.log(response.data);
                result(response.data);
            });
    };

    this.postNewEntriesToDatabase = function (entries) {
        entries.foreach($http.put('http://localhost:8080/joinPoint/')
            .then(function (response) {
                console.log(response.data)
            }));
        return alert("Create or update done!");
    };


});
