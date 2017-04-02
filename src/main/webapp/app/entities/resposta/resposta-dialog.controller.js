(function() {
    'use strict';

    angular
        .module('avaliacao360ChioteApp')
        .controller('RespostaDialogController', RespostaDialogController);

    RespostaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Resposta', 'Pergunta', 'Avaliacao'];

    function RespostaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Resposta, Pergunta, Avaliacao) {
        var vm = this;

        vm.resposta = entity;
        vm.clear = clear;
        vm.save = save;
        vm.perguntas = Pergunta.query();
        vm.avaliacaos = Avaliacao.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.resposta.id !== null) {
                Resposta.update(vm.resposta, onSaveSuccess, onSaveError);
            } else {
                Resposta.save(vm.resposta, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('avaliacao360ChioteApp:respostaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
