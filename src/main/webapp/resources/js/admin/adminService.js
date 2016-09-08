app.service('adminService', function ($http) {

    console.log('core adminService initialized');


    this.addNewUser = function (newUser) {
        console.log('adminService works... -> addNewUser');
        $http.post('/admin/user', newUser)
            .then(function (response) {
                console.log(response.status);
                location.reload();
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

    this.getUser = function (userForUpdate, result) {
        console.log('adminService works... -> getUser');
        $http.get('/admin/user/' + userForUpdate)
            .then(function (response) {
                console.log(response.data);
                result(response.data);
            });
    };

    this.updateUser = function (updatedUser) {
        console.log('adminService works... -> updateUser');
        $http.put('/admin/user/' + updatedUser.login, updatedUser)
            .then(function (response) {
                console.log(response.status);
                location.reload();
            });
    };

    this.delete = function (loginToDelete) {
        console.log('adminService works... -> delete user: ' + loginToDelete);
        $http.delete('/admin/user/' + loginToDelete)
            .then(location.reload());
    };

});
