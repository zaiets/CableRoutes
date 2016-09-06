app.service('adminService', function ($http) {

    console.log('core adminService initialized');


    this.addNewUser = function (result) {
        console.log('adminService works... -> addNewUser');
        $http.post('/admin/user')
            .then(function (response) {
                console.log(response.data);
                result(response.data);
            });
    };

    this.getUsers = function (result) {
        console.log('adminService works... -> getUsers');
        $http.get('/admin/user')
            .then(function (response) {
                console.log(response.data);
                result(response.data);
            });
    };

});
