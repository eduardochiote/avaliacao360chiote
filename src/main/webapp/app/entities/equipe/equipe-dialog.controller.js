(function() {
    'use strict';

    angular
        .module('avaliacao360ChioteApp')
        .controller('EquipeDialogController', EquipeDialogController);

    EquipeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Equipe', 'User'];

    function EquipeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Equipe, User) {
        var vm = this;

        vm.equipe = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.equipe.id !== null) {
                Equipe.update(vm.equipe, onSaveSuccess, onSaveError);
            } else {
                Equipe.save(vm.equipe, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('avaliacao360ChioteApp:equipeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dataCriacao = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
