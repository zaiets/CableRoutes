app.service('adminService', function ($http) {

    console.log('core adminService initialized');


    this.addNewUser = function (newUser, result) {
        console.log('adminService works... -> addNewUser');
        $http.post('/admin/user', newUser)
            .then(function (response) {
                console.log(response.status);
                result(response.status);
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

    this.delete = function (loginToDelete) {
        console.log('adminService works... -> delete user: ' + loginToDelete);
        $http.delete('/admin/user/' + loginToDelete)
            .then(location.reload());
    };

});
