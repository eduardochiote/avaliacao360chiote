(function() {
    'use strict';
    angular
        .module('avaliacao360ChioteApp')
        .factory('Avaliacao', Avaliacao);

    Avaliacao.$inject = ['$resource', 'DateUtils'];

    function Avaliacao ($resource, DateUtils) {
        var resourceUrl =  'api/avaliacaos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dataCricao = DateUtils.convertDateTimeFromServer(data.dataCricao);
                        data.dataAtualizacao = DateUtils.convertDateTimeFromServer(data.dataAtualizacao);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
