var stocksModule = angular.module('stocksModule', []);

stocksModule.controller('stocksController', ['$scope', '$http', '$location',
    function (scope, http, location) {

        scope.stocks = [];

        http.get('/api/stocks').success(function (data) {
            scope.stocks = data;
        }).error(function (data, status, headers, config) {
            location.path('/home');
        });
    }]);

