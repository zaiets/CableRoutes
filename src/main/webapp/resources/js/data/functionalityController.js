app.controller('functionalityController', function ($scope, entityService, cableService, equipmentService, functionalityService) {
    $scope.export = function (url) {
        console.log('export to url: ' + url);
        var form = document.createElement("form");
        form.setAttribute("action", url);
        form.setAttribute("method", "get");
        form.setAttribute("target", "_blank");
        var hiddenEle1 = document.createElement("input");
        hiddenEle1.setAttribute("type", "hidden");
        hiddenEle1.setAttribute("name", "some");
        hiddenEle1.setAttribute("value", value);
        form.append(hiddenEle1);
        form.submit();
    };

    $scope.defineEquipmentsWithNewPoints = function () {
        functionalityService.defineEquipmentsWithNewPoints(function (data) {
            console.log('defineEquipmentsWithNewPoints works...');
            $scope.equipmentsWithNewPoints = data;
        })
    };

    $scope.equipmentsWithNewPoints = [];

    $scope.putEquipmentsWithNewPointsToDB = function () {
        equipmentService.update('/equipment', function (data) {
            console.log('putEquipmentsWithNewPointsToDB works...');
            $scope.equipmentsWithNewPoints = data;
        })
    };

    $scope.allJournals = [];

    entityService.get('/journal', function (data) {
        console.log('get all items works...');
        if (data) allJournals.apply(data);
    });

    $scope.journalListToTrace = [];

    $scope.traceJournals = function () {
        console.log('traceJournals works...');
        functionalityService.traceJournals(journalListToTrace, function (data) {
            tracedCables.push(data);
        })
    };

    $scope.cableListToTrace = [];

    $scope.traceCables = function () {
        console.log('traceCables works...');
        functionalityService.traceCables(cableListToTrace, function (data) {
            tracedCables.push(data);
        })
    };

    $scope.tracedCables = [];
    $scope.putTracedCablesToDB = function () {
        cableService.update('/cable', function (data) {
            console.log('putTracedCablesToDB works...');
            $scope.tracedCables = data;
        })
    }


});


//todo this in cables/journal parsing part
<!--@RequestMapping(value = "/define/equipsinjournal", method = RequestMethod.PUT,-->
<!--consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)-->
<!--public ResponseEntity<List<EquipmentDto>> defineNewEquipmentsInJournals(@RequestBody @NotNull List<JournalDto> journalDtoList) {-->