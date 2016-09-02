app.config(function ($routeProvider) {
    $routeProvider
        .when("/joinPointPage", {
            templateUrl: '/resources/views/tabs/joinPointActionsPage.html',
            controller: 'joinPointController'
        })
        .when("/equipments", {
            templateUrl: '/resources/views/tabs/equipmentActionsPage.html',
            controller: 'equipmentController'
        })
});
