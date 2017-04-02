'use strict';

describe('Controller Tests', function() {

    describe('Avaliacao Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAvaliacao, MockUser, MockAvaliacaoModelo, MockResposta;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAvaliacao = jasmine.createSpy('MockAvaliacao');
            MockUser = jasmine.createSpy('MockUser');
            MockAvaliacaoModelo = jasmine.createSpy('MockAvaliacaoModelo');
            MockResposta = jasmine.createSpy('MockResposta');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Avaliacao': MockAvaliacao,
                'User': MockUser,
                'AvaliacaoModelo': MockAvaliacaoModelo,
                'Resposta': MockResposta
            };
            createController = function() {
                $injector.get('$controller')("AvaliacaoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'avaliacao360ChioteApp:avaliacaoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
