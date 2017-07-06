(function () {
    'use strict';

    angular
        .module('admin')
        .controller('RoleListController', RoleListController)
        .controller('RoleAddController', RoleAddController)
        .controller('RoleEditController', RoleEditController)
        .controller('RoleDeleteController', RoleDeleteController);


    /* Role List Controller  */
    RoleListController.$inject = ['$scope', 'roles'];

    function RoleListController($scope, roles) {
        $scope.roles = roles.data;
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
    RoleEditController.$inject = ['$scope', '$location', 'role', 'RoleService'];

    function RoleEditController($scope, $location, role, RoleService) {
        $scope.role = role.data;

        $scope.edit = function () {
            RoleService.update($scope.role).then(
                function () {
                    $location.path('/role/list');
                }, function (error) {
                    _showValidationErrors($scope, error);
                }
            );
        };
    }

    /* Role Delete Controller  */
    RoleDeleteController.$inject = ['$scope', '$location', 'role', 'RoleService'];

    function RoleDeleteController($scope, $location, role, RoleService) {
        $scope.role = role.data;

        $scope.remove = function () {
            RoleService.remove($scope.role.id).then(function () {
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