(function() {
    'use strict';

    angular
        .module('avaliacao360ChioteApp')
        .controller('RespostaDeleteController',RespostaDeleteController);

    RespostaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Resposta'];

    function RespostaDeleteController($uibModalInstance, entity, Resposta) {
        var vm = this;

        vm.resposta = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Resposta.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
