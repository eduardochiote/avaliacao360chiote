(function() {
    'use strict';

    angular
        .module('avaliacao360ChioteApp')
        .controller('AvaliacaoControleDialogController', AvaliacaoControleDialogController);

    AvaliacaoControleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AvaliacaoControle', 'User', 'Avaliado', 'AvaliacaoModelo'];

    function AvaliacaoControleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AvaliacaoControle, User, Avaliado, AvaliacaoModelo) {
        var vm = this;

        vm.avaliacaoControle = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.avaliados = Avaliado.query();
        vm.avaliacaomodelos = AvaliacaoModelo.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.avaliacaoControle.id !== null) {
                AvaliacaoControle.update(vm.avaliacaoControle, onSaveSuccess, onSaveError);
            } else {
                AvaliacaoControle.save(vm.avaliacaoControle, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('avaliacao360ChioteApp:avaliacaoControleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
