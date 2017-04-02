(function() {
    'use strict';

    angular
        .module('avaliacao360ChioteApp')
        .controller('AvaliacaoDetailController', AvaliacaoDetailController);

    AvaliacaoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Avaliacao', 'User', 'AvaliacaoModelo', 'Resposta'];

    function AvaliacaoDetailController($scope, $rootScope, $stateParams, previousState, entity, Avaliacao, User, AvaliacaoModelo, Resposta) {
        var vm = this;

        vm.avaliacao = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('avaliacao360ChioteApp:avaliacaoUpdate', function(event, result) {
            vm.avaliacao = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
