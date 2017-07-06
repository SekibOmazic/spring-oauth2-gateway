(function () {
    'use strict';

    angular
        .module('admin')
        .controller('ClientListController', ClientListController)
        .controller('ClientAddController', ClientAddController)
        .controller('ClientEditController', ClientEditController)
        .controller('ClientDeleteController', ClientDeleteController);

    /* Client List Controller  */
    ClientListController.$inject = ['$scope', 'clients'];

    function ClientListController($scope, clients) {
        $scope.clients = clients.data;
    }


    /* Client Add Controller */
    ClientAddController.$inject = ['$scope', '$location', 'ClientService'];

    function ClientAddController($scope, $location, ClientService) {
        $scope.client = {};
        $scope.add = function () {
            ClientService.addClient($scope.client).then(
                function () {
                    $location.path('/client/list');
                }, function (error) {
                    _showValidationErrors($scope, error);
                });
        };
    }

    /* Client Edit Controller */
    ClientEditController.$inject = ['$scope', '$location', 'client', 'ClientService'];

    function ClientEditController($scope, $location, client, ClientService) {
        $scope.client = client.data;

        $scope.edit = function () {
            ClientService.update($scope.client).then(
                function () {
                    $location.path('/client/list');
                }, function (error) {
                    _showValidationErrors($scope, error);
                }
            );
        };
    }

    /* Client Delete Controller  */
    ClientDeleteController.$inject = ['$scope', '$location', 'client', 'ClientService'];

    function ClientDeleteController($scope, $location, client, ClientService) {
        $scope.client = client.data;

        $scope.remove = function () {
            ClientService.remove($scope.client.id).then(function () {
                $location.path('/client/list');
            });
        };
    }


    /* Utility Functions */

    function _showValidationErrors($scope, error) {
        $scope.validationErrors = [];
        if (error.data && angular.isObject(error.data)) {
            for (var key in error.data) {
                $scope.validationErrors.push(error.data[key][0]);
            }
        } else {
            $scope.validationErrors.push('Could not add client.');
        }
    }

})();