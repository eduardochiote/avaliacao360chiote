(function() {
    'use strict';
    angular
        .module('avaliacao360ChioteApp')
        .factory('AvaliacaoModelo', AvaliacaoModelo);

    AvaliacaoModelo.$inject = ['$resource'];

    function AvaliacaoModelo ($resource) {
        var resourceUrl =  'api/avaliacao-modelos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
