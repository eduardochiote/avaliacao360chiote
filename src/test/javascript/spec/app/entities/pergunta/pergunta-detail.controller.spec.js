'use strict';

describe('Controller Tests', function() {

    describe('Pergunta Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPergunta, MockAvaliacaoModelo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPergunta = jasmine.createSpy('MockPergunta');
            MockAvaliacaoModelo = jasmine.createSpy('MockAvaliacaoModelo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Pergunta': MockPergunta,
                'AvaliacaoModelo': MockAvaliacaoModelo
            };
            createController = function() {
                $injector.get('$controller')("PerguntaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'avaliacao360ChioteApp:perguntaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
