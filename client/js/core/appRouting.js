app.config(function ($routeProvider) {
    $routeProvider
        .when("/joinPointPage", {
            templateUrl: 'views/joinPointActionsPage.html',
            controller: 'joinPointController'
        })
});
