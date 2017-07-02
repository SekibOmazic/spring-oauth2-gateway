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
                enabled: user.enabled
            };

            return $http({
                method: 'POST',
                url: '/admin/user/add',
                data: userObj
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