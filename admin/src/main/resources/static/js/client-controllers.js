(function () {
    'use strict';

    angular
        .module('admin')
        .controller('ClientListController', ClientListController)
        .controller('ClientAddController', ClientAddController)
        .controller('ClientEditController', ClientEditController)
        .controller('ClientDeleteController', ClientDeleteController);

    /* Client List Controller  */
    ClientListController.$inject = ['$scope', 'ClientService'];

    function ClientListController($scope, ClientService) {
        $scope.clients = [];

        ClientService.getAll().then(
            function (response) {
                $scope.clients = response.data;
                $scope.forbidden = false;
            }, function (error) {
                $scope.forbidden = true;
            });
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
    ClientEditController.$inject = ['$scope', '$routeParams', '$location', 'ClientService'];

    function ClientEditController($scope, $routeParams, $location, ClientService) {
        ClientService.getClient($routeParams.id).then(
            function (response) {
                $scope.client = response.data;
            },
            function (error) {
                _showValidationErrors($scope, error);
            }
        );

        $scope.edit = function () {
            ClientService.update($scope.client).then(
                function (response) {
                    $location.path('/client/list');
                }, function (error) {
                    _showValidationErrors($scope, error);
                }
            );
        };
    }

    /* Client Delete Controller  */
    ClientDeleteController.$inject = ['$scope', '$routeParams', '$location', 'ClientService'];

    function ClientDeleteController($scope, $routeParams, $location, ClientService) {
        ClientService.getClient($routeParams.id).then(
            function (response) {
                $scope.client = response.data;
            },
            function (error) {
                _showValidationErrors($scope, error);
            }
        );

        $scope.remove = function () {
            ClientService.remove($scope.client.id).then(function (response) {
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