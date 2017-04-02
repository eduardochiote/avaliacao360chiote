(function() {
    'use strict';

    angular
        .module('avaliacao360ChioteApp')
        .controller('PerguntaDeleteController',PerguntaDeleteController);

    PerguntaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Pergunta'];

    function PerguntaDeleteController($uibModalInstance, entity, Pergunta) {
        var vm = this;

        vm.pergunta = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Pergunta.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
