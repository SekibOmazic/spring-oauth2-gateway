(function () {
    'use strict';

    angular
        .module('admin')
        .factory('ClientService', ClientService);

    ClientService.$inject = ['$http'];

    function ClientService($http) {
        var service = {};

        // get all clients from database
        service.getAll = function () {
            return $http({
                method: 'GET',
                url: '/admin/api/client/all'
            });
        };

        // get single record from database
        service.getClient = function (id) {
            return $http({
                method: 'GET',
                url: '/admin/api/client/'+ id
            });
        };

        // post the data to database
        service.addClient = function (client) {
            var userObj = {
                name: client.name,
                description: client.description,
                callback: client.callback
            };

            return $http({
                method: 'POST',
                url: '/admin/api/client/add',
                data: userObj
            });
        };

        // update client
        service.update = function (client) {
            return $http({
                method: 'PUT',
                url: '/admin/api/client/' + client.id,
                data: client
            });
        };

        // delete client from database
        service.remove = function (id) {
            return $http({
                method: 'DELETE',
                url: '/admin/api/client/' + id
            });
        };

        return service;
    }

})();