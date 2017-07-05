(function () {
    'use strict';

    angular
        .module('admin')
        .factory('RoleService', RoleService);

    RoleService.$inject = ['$http'];

    function RoleService($http) {
        var service = {};

        // get all roles from database
        service.getAll = function () {
            return $http({
                method: 'GET',
                url: '/admin/api/role/all'
            });
        };

        // get single record from database
        service.getRole = function (id) {
            return $http({
                method: 'GET',
                url: '/admin/api/role/'+ id
            });
        };

        // post the data to database
        service.addRole = function (role) {
            var roleObj = {
                id: role.id,
                displayName: role.displayName,
                application: role.application
            };

            return $http({
                method: 'POST',
                url: '/admin/api/role/add',
                data: roleObj
            });
        };

        // update role
        service.update = function (role) {
            return $http({
                method: 'PUT',
                url: '/admin/api/role/' + role.id,
                data: role
            });
        };

        // delete role from database
        service.remove = function (id) {
            return $http({
                method: 'DELETE',
                url: '/admin/api/role/' + id
            });
        };

        return service;
    }

})();