app.controller('functionalityController', function ($scope, entityService, functionalityService) {
    $scope.export = function(url) {
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

    };

    $scope.equipmentsWithNewPoints = [];

    $scope.putEquipmentsWithNewPointsToDB = function () {

    };

    $scope.getAllJournals = function(){

    };

    $scope.journalListToTrace = [];
    $scope.traceJournals = function(){

    };
    $scope.cableListToTrace = [];
    $scope.traceCables = function(){

    };
    $scope.traceAll = function(){

    };



    $scope.tracedCables = [];
    $scope.putTracedCablesToDB = function() {

    }


});





<!--@RequestMapping(value = "/trace/cable", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)-->
<!--public ResponseEntity<List<CableDto>> traceCablesAndDefineLengths(@RequestBody @NotNull List<String> cablesKksList) {-->

<!--@RequestMapping(value = "/trace/journal", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)-->
<!--public ResponseEntity<List<CableDto>> traceJournalsAndDefineLengths(@RequestBody @NotNull List<String> journalsKksList) {-->


<!--@RequestMapping(value = "/define/pointsbyequips", method = RequestMethod.PUT,-->
<!--consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)-->
<!--public ResponseEntity<List<EquipmentDto>> defineEquipmentsClosestPoints(@RequestBody @NotNull List<EquipmentDto> equipments) {-->

<!--@RequestMapping(value = "/define/equipsinjournal", method = RequestMethod.PUT,-->
<!--consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)-->
<!--public ResponseEntity<List<EquipmentDto>> defineNewEquipmentsInJournals(@RequestBody @NotNull List<JournalDto> journalDtoList) {-->

<!--@RequestMapping(value = "/generate/traced", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)-->
<!--public ResponseEntity<List<File>> generateJournalInExcelFormatTraced(@RequestBody @NotNull List<String> journalNames) {-->

<!--@RequestMapping(value = "/generate/calculated", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)-->
<!--public ResponseEntity<List<File>> generateJournalInExcelFormatCalculated(@RequestBody @NotNull List<String> journalNames) {-->
