var iconiq = angular.module('iconiq', ['ngRoute','stocksModule']);

iconiq.config(['$routeProvider', function($routeProvider) {
    $routeProvider.
        when('/home', {
            templateUrl: 'templates/stock-table.html',
        }).
        when('/about', {
            templateUrl: 'templates/about.html'
        }).
        otherwise({
            redirectTo: '/home'
        });
}]);
