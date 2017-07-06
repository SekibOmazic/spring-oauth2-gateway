(function () {
    'use strict';

    angular.module('admin', [
        'ngRoute'
    ]).config(config);

    function config($routeProvider, $locationProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'list.html',
                controller: 'UserListController',
                resolve: {
                    users: ['UserService', function (UserService) {
                        return UserService.getAll();
                    }]
                }
            })
            .when('/add', {
                templateUrl: 'add.html',
                controller: 'UserAddController',
                resolve: {
                    roles: ['RoleService', function (RoleService) {
                        return RoleService.getAll();
                    }]
                }
            })
            .when('/edit/:id', {
                templateUrl: 'edit.html',
                controller: 'UserEditController',
                resolve: {
                    roles: ['RoleService', function (RoleService) {
                        return RoleService.getAll();
                    }],
                    user: ['UserService', '$route', function (UserService, $route) {
                        return UserService.getUser($route.current.params.id);
                    }]
                }
            })
            .when('/delete/:id', {
                templateUrl: 'delete.html',
                controller: 'UserDeleteController',
                resolve: {
                    user: ['UserService', '$route', function (UserService, $route) {
                        return UserService.getUser($route.current.params.id);
                    }]
                }
            })

            // CLIENTS
            .when('/client/list', {
                templateUrl: 'client/list.html',
                controller: 'ClientListController',
                resolve: {
                    clients: ['ClientService', function (ClientService) {
                        return ClientService.getAll();
                    }]
                }
            })
            .when('/client/add', {
                templateUrl: 'client/add.html',
                controller: 'ClientAddController'
            })
            .when('/client/edit/:id', {
                templateUrl: 'client/edit.html',
                controller: 'ClientEditController',
                resolve: {
                    client: ['ClientService', '$route', function (ClientService, $route) {
                        return ClientService.getClient($route.current.params.id);
                    }]
                }
            })
            .when('/client/delete/:id', {
                templateUrl: 'client/delete.html',
                controller: 'ClientDeleteController',
                resolve: {
                    client: ['ClientService', '$route', function (ClientService, $route) {
                        return ClientService.getClient($route.current.params.id);
                    }]
                }
            })

            // ROLES
            .when('/role/list', {
                templateUrl: 'role/list.html',
                controller: 'RoleListController',
                resolve: {
                    roles: ['RoleService', function (RoleService) {
                        return RoleService.getAll();
                    }]
                }
            })
            .when('/role/add', {
                templateUrl: 'role/add.html',
                controller: 'RoleAddController'
            })
            .when('/role/edit/:id', {
                templateUrl: 'role/edit.html',
                controller: 'RoleEditController',
                resolve: {
                    role: ['RoleService', '$route', function (RoleService, $route) {
                        return RoleService.getRole($route.current.params.id);
                    }]
                }
            })
            .when('/role/delete/:id', {
                templateUrl: 'role/delete.html',
                controller: 'RoleDeleteController',
                resolve: {
                    role: ['RoleService', '$route', function (RoleService, $route) {
                        return RoleService.getRole($route.current.params.id);
                    }]
                }
            })

            .otherwise({ redirectTo: '/' });

        //$locationProvider.html5Mode(true);
    }

    config.$inject = ['$routeProvider', '$locationProvider'];


})();