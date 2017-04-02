(function() {
    'use strict';

    angular
        .module('avaliacao360ChioteApp')
        .controller('RespostaDetailController', RespostaDetailController);

    RespostaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Resposta', 'Pergunta', 'Avaliacao'];

    function RespostaDetailController($scope, $rootScope, $stateParams, previousState, entity, Resposta, Pergunta, Avaliacao) {
        var vm = this;

        vm.resposta = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('avaliacao360ChioteApp:respostaUpdate', function(event, result) {
            vm.resposta = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
