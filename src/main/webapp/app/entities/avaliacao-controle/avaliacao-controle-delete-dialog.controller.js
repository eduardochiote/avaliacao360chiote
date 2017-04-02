(function() {
    'use strict';

    angular
        .module('avaliacao360ChioteApp')
        .controller('AvaliacaoControleDeleteController',AvaliacaoControleDeleteController);

    AvaliacaoControleDeleteController.$inject = ['$uibModalInstance', 'entity', 'AvaliacaoControle'];

    function AvaliacaoControleDeleteController($uibModalInstance, entity, AvaliacaoControle) {
        var vm = this;

        vm.avaliacaoControle = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AvaliacaoControle.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
