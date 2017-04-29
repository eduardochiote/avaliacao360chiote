(function() {
    'use strict';

    angular
        .module('avaliacao360ChioteApp')
        .controller('AvaliacaoControleDetailController', AvaliacaoControleDetailController);

    AvaliacaoControleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AvaliacaoControle', 'User'];

    function AvaliacaoControleDetailController($scope, $rootScope, $stateParams, previousState, entity, AvaliacaoControle, User) {
        var vm = this;

        vm.avaliacaoControle = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('avaliacao360ChioteApp:avaliacaoControleUpdate', function(event, result) {
            vm.avaliacaoControle = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
