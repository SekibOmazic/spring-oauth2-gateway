(function () {
    'use strict';

    angular
        .module('admin')
        .controller('RoleListController', RoleListController)
        .controller('RoleAddController', RoleAddController)
        .controller('RoleEditController', RoleEditController)
        .controller('RoleDeleteController', RoleDeleteController);

    /* Role List Controller  */
    RoleListController.$inject = ['$scope', 'RoleService'];

    function RoleListController($scope, RoleService) {
        $scope.roles = [];

        RoleService.getAll().then(
            function (response) {
                $scope.roles = response.data;
                $scope.forbidden = false;
            }, function (error) {
                $scope.forbidden = true;
            });
    }


    /* Role Add Controller */
    RoleAddController.$inject = ['$scope', '$location', 'RoleService'];

    function RoleAddController($scope, $location, RoleService) {
        $scope.role = {};
        $scope.add = function () {
            RoleService.addRole($scope.role).then(
                function () {
                    $location.path('/role/list');
                }, function (error) {
                    _showValidationErrors($scope, error);
                });
        };
    }

    /* Role Edit Controller */
    RoleEditController.$inject = ['$scope', '$routeParams', '$location', 'RoleService'];

    function RoleEditController($scope, $routeParams, $location, RoleService) {
        RoleService.getRole($routeParams.id).then(
            function (response) {
                $scope.role = response.data;
            },
            function (error) {
                _showValidationErrors($scope, error);
            }
        );

        $scope.edit = function () {
            RoleService.update($scope.role).then(
                function (response) {
                    $location.path('/role/list');
                }, function (error) {
                    _showValidationErrors($scope, error);
                }
            );
        };
    }

    /* Role Delete Controller  */
    RoleDeleteController.$inject = ['$scope', '$routeParams', '$location', 'RoleService'];

    function RoleDeleteController($scope, $routeParams, $location, RoleService) {
        RoleService.getRole($routeParams.id).then(
            function (response) {
                $scope.role = response.data;
            },
            function (error) {
                _showValidationErrors($scope, error);
            }
        );

        $scope.remove = function () {
            RoleService.remove($scope.role.id).then(function (response) {
                $location.path('/role/list');
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