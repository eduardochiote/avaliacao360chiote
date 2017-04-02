(function() {
    'use strict';
    angular
        .module('avaliacao360ChioteApp')
        .factory('AvaliacaoControle', AvaliacaoControle);

    AvaliacaoControle.$inject = ['$resource'];

    function AvaliacaoControle ($resource) {
        var resourceUrl =  'api/avaliacao-controles/:id';

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
