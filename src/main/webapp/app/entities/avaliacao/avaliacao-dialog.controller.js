(function() {
    'use strict';

    angular
        .module('avaliacao360ChioteApp')
        .controller('AvaliacaoDialogController', AvaliacaoDialogController);

    AvaliacaoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Avaliacao', 'User', 'AvaliacaoModelo', 'Resposta'];

    function AvaliacaoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Avaliacao, User, AvaliacaoModelo, Resposta) {
        var vm = this;

        vm.avaliacao = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();
        vm.avaliacaomodelos = AvaliacaoModelo.query();
        vm.respostas = Resposta.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.avaliacao.id !== null) {
                Avaliacao.update(vm.avaliacao, onSaveSuccess, onSaveError);
            } else {
                Avaliacao.save(vm.avaliacao, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('avaliacao360ChioteApp:avaliacaoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dataCricao = false;
        vm.datePickerOpenStatus.dataAtualizacao = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
