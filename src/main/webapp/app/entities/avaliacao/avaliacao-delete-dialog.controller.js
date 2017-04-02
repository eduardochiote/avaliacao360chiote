(function() {
    'use strict';

    angular
        .module('avaliacao360ChioteApp')
        .controller('AvaliacaoDeleteController',AvaliacaoDeleteController);

    AvaliacaoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Avaliacao'];

    function AvaliacaoDeleteController($uibModalInstance, entity, Avaliacao) {
        var vm = this;

        vm.avaliacao = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Avaliacao.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
