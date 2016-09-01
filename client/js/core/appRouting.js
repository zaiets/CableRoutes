app.config(function ($routeProvider) {
    $routeProvider
        .when("/joinPointsPage", {
            templateUrl: 'views/joinPointActionsPage.html',
            controller: 'joinPointController'
        })
});
