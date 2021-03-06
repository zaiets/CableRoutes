app.config(function ($routeProvider) {
    $routeProvider
        .when("/admins", {
            templateUrl: '/resources/views/admin/admins.html',
            controller: 'adminController'
        })
        .when("/joinPointPage", {
            templateUrl: '/resources/views/tabs/joinPoints.html',
            controller: 'joinPointController'
        })
        .when("/equipments", {
            templateUrl: '/resources/views/tabs/equipments.html',
            controller: 'equipmentController'
        })
        .when("/routes", {
            templateUrl: '/resources/views/tabs/routes.html',
            controller: 'routeController'
        })
        .when("/routeTypes", {
            templateUrl: '/resources/views/tabs/routeTypes.html',
            controller: 'routeTypeController'
        })
        .when("/cables", {
            templateUrl: '/resources/views/tabs/cables.html',
            controller: 'cableController'
        })
        .when("/functionality", {
            templateUrl: '/resources/views/tabs/functionality.html',
            controller: 'functionalityController'
        })
        .otherwise({
            redirectTo: '/'
        });
});
