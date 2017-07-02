(function () {
    'use strict';

    angular
        .module('admin')
        .controller('UserListController', UserListController)
        .controller('UserAddController', UserAddController)
        .controller('UserDeleteController', UserDeleteController);

    /* User List Controller  */
    UserListController.$inject = ['$scope', 'UserService'];

    function UserListController($scope, UserService) {
        $scope.users = [];

        UserService.getAll().then(
            function (response) {
                $scope.users = response.data;
                $scope.forbidden = false;
            }, function (error) {
                $scope.forbidden = true;
            });
    }


    /* User Add Controller */
    UserAddController.$inject = ['$scope', '$location', 'UserService'];

    function UserAddController($scope, $location, UserService) {
        $scope.user = {};
        $scope.add = function () {
            UserService.addUser($scope.user).then(
                function () {
                    $location.path('/');
                }, function (error) {
                    _showValidationErrors($scope, error);
                });
        };
    }

    /* User Delete Controller  */
    UserDeleteController.$inject = ['$scope', '$routeParams', '$location', 'UserService'];

    function UserDeleteController($scope, $routeParams, $location, UserService) {
        UserService.getUser($routeParams.id).then(
            function (response) {
                $scope.user = response.data;
            },
            function (error) {
                _showValidationErrors($scope, error);
            }
        );

        $scope.remove = function () {
            UserService.remove($scope.user.id).then(function (response) {
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