(function () {
    'use strict';

    angular
        .module('avaliacao360ChioteApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
