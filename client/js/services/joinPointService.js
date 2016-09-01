app.service('joinPointService', function ($http) {

    console.log('core app.service initialized');

    this.getJoinPoints = function (result) {
        $http.get('http://localhost:8080/joinPoint')
            .then(function (response) {
                console.log(response.data);
                result(response.data);
            });
    };

    this.getCurrentUser = function () {
        $http.get('http://localhost:8080/currentuser')
            .then(function (response) {
                console.log(response.data)
            });
        return 'Hello ' + (counting++);
    };


});
