(function () {
    'use strict';

    angular
        .module('admin')
        .factory('UserService', UserService);

    UserService.$inject = ['$http'];

    function UserService($http) {
        var service = {};

        // get all users from database
        service.getAll = function () {
            return $http({
                method: 'GET',
                url: '/admin/user/all'
            });
        };

        // get single record from database
        service.getUser = function (id) {
            return $http({
                method: 'GET',
                url: '/admin/user/'+ id
            });
        };

        // post the data to database
        service.addUser = function (user) {
            var userObj = {
                username: user.username,
                password: user.password,
                enabled: user.enabled,
                roles: user.roles
            };

            return $http({
                method: 'POST',
                url: '/admin/user/add',
                data: userObj
            });
        };

        // update user
        service.update = function (user) {
            return $http({
                method: 'PUT',
                url: '/admin/user/' + user.id,
                data: user
            });
        };

        // delete user from database
        service.remove = function (id) {
            return $http({
                method: 'DELETE',
                url: '/admin/user/' + id
            });
        };

        return service;
    }


})();