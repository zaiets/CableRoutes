app.service('joinPointService', function ($http) {

    console.log('core app.joinPointService initialized');

    this.getJoinPoints = function (result) {
        console.log('joinPointService works... -> getJoinPoints');
        $http.get('http://localhost:8080/joinPoint')
            .then(function (response) {
                console.log(response.data);
                result(response.data);
            });
    };

    this.postNewEntriesToDatabase = function (entries) {
        console.log('joinPointService works... -> postNewEntriesToDatabase');
        entries.forEach($http.put('http://localhost:8080/joinPoint')
            .then(function (response) {
                console.log(response.data)
            }));
        return alert("Create or update done!");
    };


});
