'use strict';

describe('Controller Tests', function() {

    describe('Resposta Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockResposta, MockPergunta, MockAvaliacao;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockResposta = jasmine.createSpy('MockResposta');
            MockPergunta = jasmine.createSpy('MockPergunta');
            MockAvaliacao = jasmine.createSpy('MockAvaliacao');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Resposta': MockResposta,
                'Pergunta': MockPergunta,
                'Avaliacao': MockAvaliacao
            };
            createController = function() {
                $injector.get('$controller')("RespostaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'avaliacao360ChioteApp:respostaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
