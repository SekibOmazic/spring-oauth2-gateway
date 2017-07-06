(function () {
    'use strict';

    angular
        .module('admin')
        .controller('UserListController', UserListController)
        .controller('UserAddController', UserAddController)
        .controller('UserEditController', UserEditController)
        .controller('UserDeleteController', UserDeleteController);

    /* User List Controller  */
    UserListController.$inject = ['$scope', 'users'];

    function UserListController($scope, users) {
        $scope.users = users.data;
    }


    /* User Add Controller */
    UserAddController.$inject = ['$scope', '$location', 'UserService', 'roles'];

    function UserAddController($scope, $location, UserService, roles) {
        $scope.user = { selectedRoles: {}, roles: [] };

        $scope.roles = roles.data;

        $scope.add = function () {

            for (var p in $scope.user.selectedRoles) {
                var r = $scope.roles.find(function(role) {
                    return role.id === p;
                });
                if (r) {
                    $scope.user.roles.push(r);
                }
            }

            UserService.addUser($scope.user).then(
                function () {
                    $location.path('/');
                }, function (error) {
                    _showValidationErrors($scope, error);
                });
        };
    }

    /* User Edit Controller */
    UserEditController.$inject = ['$scope', '$location', 'user', 'roles', 'UserService' ];

    function UserEditController($scope, $location, user, roles, UserService) {
        $scope.roles = roles.data; // all available roles
        $scope.user = user.data;

        //$scope.selection = {}; //chosen roles
        $scope.selection = $scope.roles.reduce(function (acc, ro) {
            var foundRole = $scope.user.roles.find(function(userRole) {
                return userRole.id === ro.id;
            });

            acc[ro.id] = !!foundRole;

            return acc;
        }, {});

        $scope.edit = function () {

            $scope.user.roles.length = 0;

            for (var p in $scope.selection) {
                var r = $scope.roles.find(function(role) {
                    return role.id === p && $scope.selection[p];
                });
                if (r) {
                    $scope.user.roles.push(r);
                }
            }

            UserService.update($scope.user).then(
                function () {
                    $location.path('/');
                }, function (error) {
                    _showValidationErrors($scope, error);
                }
            );
        };
    }

    /* User Delete Controller  */
    UserDeleteController.$inject = ['$scope', '$location', 'user', 'UserService'];

    function UserDeleteController($scope, $location, user, UserService) {
        $scope.user = user.data;

        $scope.remove = function () {
            UserService.remove($scope.user.id).then(function () {
                $location.path('/');
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
            $scope.validationErrors.push('Could not add user.');
        }
    }

})();