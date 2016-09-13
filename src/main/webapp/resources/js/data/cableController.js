app.controller('cableController', function ($scope, entityService, multipartFormService, cableService) {
    //journals
    $scope.uploadedFiles = [];

    $scope.submit = function () {
        console.log('submit file for parsing works...');
        multipartFormService.post('/functionality/parse/journals', $scope.uploadedFiles, function (result) {
            $scope.tempJournals = result;
        })
    };

    $scope.journals = [];
    $scope.tempJournals = [];
    $scope.journal = {
        kksName: undefined,
        cables: undefined,
        file: undefined
    };

    entityService.get('/journal', function (data) {
        console.log('get all journals works...');
        $scope.journals = data;
    });

    $scope.sendNewJournalsToDatabase = function () {
        console.log('sendNewEntriesToDatabase works...');
        var uploadUrl = '/cable';
        entityService.createOrUpdate(uploadUrl, $scope.tempJournals);
    };

    $scope.deleteJournalFromDB = function (kks) {
        console.log('delete Journal from DB  works...' + kks);
        entityService.delete('/journal/' + kks)
    };

    //cables
    $scope.tempCables = [];
    $scope.newCable = {
        kksName: undefined,
        journal: undefined,
        numberInJournal: undefined,
        cableType: undefined,
        cableDimensions: undefined,
        startName: this.start? this.start.fullName : undefined,
        start: undefined,
        endName: this.end? this.end.fullName : undefined,
        end: undefined,
        reserving: undefined,
        length: undefined
    };

    $scope.getAndShow = function () {
        console.log('getAndShow works...' + $scope.newCable.kksName);
        entityService.get('/cable/' + $scope.newCable.kksName, function (data) {
            console.log(data);
            if (data) {
                $scope.newCable = data;
            }
        })
    };

    $scope.addNewToTemp = function () {
        console.log('addNewItem to temp collection works...');
        var entity = angular.copy($scope.newCable);
        $scope.tempCables.push(entity);
        console.log(entity);
    };

    $scope.sendNewEntriesToDatabase = function () {
        console.log('sendNewEntriesToDatabase works...');
        var uploadUrl = '/cable';
        cableService.createOrUpdate(uploadUrl, $scope.tempCables, function (rejected) {
            console.log('DB rejected: ' + rejected);
            $scope.tempCables = rejected;
            //reload table of cables (sorted by journal)
            entityService.get('/journal', function (data) {
                console.log('getAll works...');
                $scope.journals = data;
            });
        });
    };

    $scope.deleteCableFromDB = function (kks) {
        console.log('delete Cable from DB  works...' + kks);
        entityService.delete('/cable/' + kks)
    };

});