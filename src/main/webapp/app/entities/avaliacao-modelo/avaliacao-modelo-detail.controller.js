(function() {
    'use strict';

    angular
        .module('avaliacao360ChioteApp')
        .controller('AvaliacaoModeloDetailController', AvaliacaoModeloDetailController);

    AvaliacaoModeloDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AvaliacaoModelo', 'Pergunta', 'Equipe'];

    function AvaliacaoModeloDetailController($scope, $rootScope, $stateParams, previousState, entity, AvaliacaoModelo, Pergunta, Equipe) {
        var vm = this;

        vm.avaliacaoModelo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('avaliacao360ChioteApp:avaliacaoModeloUpdate', function(event, result) {
            vm.avaliacaoModelo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
