(function () {
    'use strict';

    angular.module('admin', [
        'ngRoute'
    ]).config(config);

    function config($routeProvider, $locationProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'list.html',
                controller: 'UserListController'
            })
            .when('/add', {
                templateUrl: 'add.html',
                controller: 'UserAddController'
            })
            .when('/delete/:id', {
                templateUrl: 'delete.html',
                controller: 'UserDeleteController'
            })
            .otherwise({ redirectTo: '/' });

        //$locationProvider.html5Mode(true);
    }

    config.$inject = ['$routeProvider', '$locationProvider'];


})();