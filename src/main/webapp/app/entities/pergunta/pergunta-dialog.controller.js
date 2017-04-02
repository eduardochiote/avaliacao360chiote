(function() {
    'use strict';

    angular
        .module('avaliacao360ChioteApp')
        .controller('PerguntaDialogController', PerguntaDialogController);

    PerguntaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Pergunta', 'AvaliacaoModelo'];

    function PerguntaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Pergunta, AvaliacaoModelo) {
        var vm = this;

        vm.pergunta = entity;
        vm.clear = clear;
        vm.save = save;
        vm.avaliacaomodelos = AvaliacaoModelo.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pergunta.id !== null) {
                Pergunta.update(vm.pergunta, onSaveSuccess, onSaveError);
            } else {
                Pergunta.save(vm.pergunta, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('avaliacao360ChioteApp:perguntaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
