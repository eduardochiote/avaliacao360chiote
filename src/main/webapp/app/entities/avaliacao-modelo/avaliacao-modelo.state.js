(function() {
    'use strict';

    angular
        .module('avaliacao360ChioteApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('avaliacao-modelo', {
            parent: 'entity',
            url: '/avaliacao-modelo?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'avaliacao360ChioteApp.avaliacaoModelo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/avaliacao-modelo/avaliacao-modelos.html',
                    controller: 'AvaliacaoModeloController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('avaliacaoModelo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('avaliacao-modelo-detail', {
            parent: 'entity',
            url: '/avaliacao-modelo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'avaliacao360ChioteApp.avaliacaoModelo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/avaliacao-modelo/avaliacao-modelo-detail.html',
                    controller: 'AvaliacaoModeloDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('avaliacaoModelo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'AvaliacaoModelo', function($stateParams, AvaliacaoModelo) {
                    return AvaliacaoModelo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'avaliacao-modelo',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('avaliacao-modelo-detail.edit', {
            parent: 'avaliacao-modelo-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/avaliacao-modelo/avaliacao-modelo-dialog.html',
                    controller: 'AvaliacaoModeloDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AvaliacaoModelo', function(AvaliacaoModelo) {
                            return AvaliacaoModelo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('avaliacao-modelo.new', {
            parent: 'avaliacao-modelo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/avaliacao-modelo/avaliacao-modelo-dialog.html',
                    controller: 'AvaliacaoModeloDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                descricao: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('avaliacao-modelo', null, { reload: 'avaliacao-modelo' });
                }, function() {
                    $state.go('avaliacao-modelo');
                });
            }]
        })
        .state('avaliacao-modelo.edit', {
            parent: 'avaliacao-modelo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/avaliacao-modelo/avaliacao-modelo-dialog.html',
                    controller: 'AvaliacaoModeloDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AvaliacaoModelo', function(AvaliacaoModelo) {
                            return AvaliacaoModelo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('avaliacao-modelo', null, { reload: 'avaliacao-modelo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('avaliacao-modelo.delete', {
            parent: 'avaliacao-modelo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/avaliacao-modelo/avaliacao-modelo-delete-dialog.html',
                    controller: 'AvaliacaoModeloDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AvaliacaoModelo', function(AvaliacaoModelo) {
                            return AvaliacaoModelo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('avaliacao-modelo', null, { reload: 'avaliacao-modelo' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
