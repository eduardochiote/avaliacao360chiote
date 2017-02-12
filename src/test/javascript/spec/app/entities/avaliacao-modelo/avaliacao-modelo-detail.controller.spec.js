'use strict';

describe('Controller Tests', function() {

    describe('AvaliacaoModelo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAvaliacaoModelo, MockPergunta, MockEquipe;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAvaliacaoModelo = jasmine.createSpy('MockAvaliacaoModelo');
            MockPergunta = jasmine.createSpy('MockPergunta');
            MockEquipe = jasmine.createSpy('MockEquipe');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'AvaliacaoModelo': MockAvaliacaoModelo,
                'Pergunta': MockPergunta,
                'Equipe': MockEquipe
            };
            createController = function() {
                $injector.get('$controller')("AvaliacaoModeloDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'avaliacao360ChioteApp:avaliacaoModeloUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
