(function() {
    'use strict';

    angular
        .module('avaliacao360ChioteApp')
        .controller('AvaliacaoModeloDeleteController',AvaliacaoModeloDeleteController);

    AvaliacaoModeloDeleteController.$inject = ['$uibModalInstance', 'entity', 'AvaliacaoModelo'];

    function AvaliacaoModeloDeleteController($uibModalInstance, entity, AvaliacaoModelo) {
        var vm = this;

        vm.avaliacaoModelo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AvaliacaoModelo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
