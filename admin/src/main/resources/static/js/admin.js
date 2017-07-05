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

            // CLIENTS
            .when('/client/list', {
                templateUrl: 'client/list.html',
                controller: 'ClientListController'
            })
            .when('/client/add', {
                templateUrl: 'client/add.html',
                controller: 'ClientAddController'
            })
            .when('/client/edit/:id', {
                templateUrl: 'client/edit.html',
                controller: 'ClientEditController'
            })
            .when('/client/delete/:id', {
                templateUrl: 'client/delete.html',
                controller: 'ClientDeleteController'
            })

            // ROLES
            .when('/role/list', {
                templateUrl: 'role/list.html',
                controller: 'RoleListController'
            })
            .when('/role/add', {
                templateUrl: 'role/add.html',
                controller: 'RoleAddController'
            })
            .when('/role/edit/:id', {
                templateUrl: 'role/edit.html',
                controller: 'RoleEditController'
            })
            .when('/role/delete/:id', {
                templateUrl: 'role/delete.html',
                controller: 'RoleDeleteController'
            })

            .otherwise({ redirectTo: '/' });

        //$locationProvider.html5Mode(true);
    }

    config.$inject = ['$routeProvider', '$locationProvider'];


})();