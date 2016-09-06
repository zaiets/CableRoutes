app.config(function ($routeProvider) {
    $routeProvider
        .when("/admins", {
            templateUrl: '/resources/views/admin/admins.html',
            controller: 'adminController'
        })
        .when("/joinPointPage", {
            templateUrl: '/resources/views/tabs/joinPointActionsPage.html',
            controller: 'joinPointController'
        })
        .when("/equipments", {
            templateUrl: '/resources/views/tabs/equipmentActionsPage.html',
            controller: 'equipmentController'
        })
        .otherwise({
            redirectTo: '/'
        });
});
