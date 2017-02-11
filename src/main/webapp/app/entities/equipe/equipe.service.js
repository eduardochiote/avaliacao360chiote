(function() {
    'use strict';
    angular
        .module('avaliacao360ChioteApp')
        .factory('Equipe', Equipe);

    Equipe.$inject = ['$resource', 'DateUtils'];

    function Equipe ($resource, DateUtils) {
        var resourceUrl =  'api/equipes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dataCriacao = DateUtils.convertDateTimeFromServer(data.dataCriacao);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
