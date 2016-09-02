app.service('multipartForm', ['$http', function($http){
    this.post = function(uploadUrl, data, result){
        var fd = new FormData();
        fd.append('uploadedFile', data);
        $http.post(uploadUrl, fd, {
            transformRequest: angular.indentity,
            headers: { 'Content-Type': undefined }
        }).then(function (response) {
            console.log(response.data);
            result(response.data);
        });
    }
}]);