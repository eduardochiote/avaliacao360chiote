package com.chiote.web.rest;

import com.chiote.Avaliacao360ChioteApp;

import com.chiote.domain.AvaliacaoModelo;
import com.chiote.domain.Equipe;
import com.chiote.repository.AvaliacaoModeloRepository;
import com.chiote.service.AvaliacaoModeloService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AvaliacaoModeloResource REST controller.
 *
 * @see AvaliacaoModeloResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Avaliacao360ChioteApp.class)
public class AvaliacaoModeloResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Inject
    private AvaliacaoModeloRepository avaliacaoModeloRepository;

    @Inject
    private AvaliacaoModeloService avaliacaoModeloService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAvaliacaoModeloMockMvc;

    private AvaliacaoModelo avaliacaoModelo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AvaliacaoModeloResource avaliacaoModeloResource = new AvaliacaoModeloResource();
        ReflectionTestUtils.setField(avaliacaoModeloResource, "avaliacaoModeloService", avaliacaoModeloService);
        this.restAvaliacaoModeloMockMvc = MockMvcBuilders.standaloneSetup(avaliacaoModeloResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvaliacaoModelo createEntity(EntityManager em) {
        AvaliacaoModelo avaliacaoModelo = new AvaliacaoModelo()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO);
        // Add required entity
        Equipe equipe = EquipeResourceIntTest.createEntity(em);
        em.persist(equipe);
        em.flush();
        avaliacaoModelo.setEquipe(equipe);
        return avaliacaoModelo;
    }

    @Before
    public void initTest() {
        avaliacaoModelo = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvaliacaoModelo() throws Exception {
        int databaseSizeBeforeCreate = avaliacaoModeloRepository.findAll().size();

        // Create the AvaliacaoModelo

        restAvaliacaoModeloMockMvc.perform(post("/api/avaliacao-modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avaliacaoModelo)))
            .andExpect(status().isCreated());

        // Validate the AvaliacaoModelo in the database
        List<AvaliacaoModelo> avaliacaoModeloList = avaliacaoModeloRepository.findAll();
        assertThat(avaliacaoModeloList).hasSize(databaseSizeBeforeCreate + 1);
        AvaliacaoModelo testAvaliacaoModelo = avaliacaoModeloList.get(avaliacaoModeloList.size() - 1);
        assertThat(testAvaliacaoModelo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAvaliacaoModelo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createAvaliacaoModeloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avaliacaoModeloRepository.findAll().size();

        // Create the AvaliacaoModelo with an existing ID
        AvaliacaoModelo existingAvaliacaoModelo = new AvaliacaoModelo();
        existingAvaliacaoModelo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvaliacaoModeloMockMvc.perform(post("/api/avaliacao-modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingAvaliacaoModelo)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AvaliacaoModelo> avaliacaoModeloList = avaliacaoModeloRepository.findAll();
        assertThat(avaliacaoModeloList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoModeloRepository.findAll().size();
        // set the field null
        avaliacaoModelo.setNome(null);

        // Create the AvaliacaoModelo, which fails.

        restAvaliacaoModeloMockMvc.perform(post("/api/avaliacao-modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avaliacaoModelo)))
            .andExpect(status().isBadRequest());

        List<AvaliacaoModelo> avaliacaoModeloList = avaliacaoModeloRepository.findAll();
        assertThat(avaliacaoModeloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoModeloRepository.findAll().size();
        // set the field null
        avaliacaoModelo.setDescricao(null);

        // Create the AvaliacaoModelo, which fails.

        restAvaliacaoModeloMockMvc.perform(post("/api/avaliacao-modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avaliacaoModelo)))
            .andExpect(status().isBadRequest());

        List<AvaliacaoModelo> avaliacaoModeloList = avaliacaoModeloRepository.findAll();
        assertThat(avaliacaoModeloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAvaliacaoModelos() throws Exception {
        // Initialize the database
        avaliacaoModeloRepository.saveAndFlush(avaliacaoModelo);

        // Get all the avaliacaoModeloList
        restAvaliacaoModeloMockMvc.perform(get("/api/avaliacao-modelos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avaliacaoModelo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    public void getAvaliacaoModelo() throws Exception {
        // Initialize the database
        avaliacaoModeloRepository.saveAndFlush(avaliacaoModelo);

        // Get the avaliacaoModelo
        restAvaliacaoModeloMockMvc.perform(get("/api/avaliacao-modelos/{id}", avaliacaoModelo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(avaliacaoModelo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAvaliacaoModelo() throws Exception {
        // Get the avaliacaoModelo
        restAvaliacaoModeloMockMvc.perform(get("/api/avaliacao-modelos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvaliacaoModelo() throws Exception {
        // Initialize the database
        avaliacaoModeloService.save(avaliacaoModelo);

        int databaseSizeBeforeUpdate = avaliacaoModeloRepository.findAll().size();

        // Update the avaliacaoModelo
        AvaliacaoModelo updatedAvaliacaoModelo = avaliacaoModeloRepository.findOne(avaliacaoModelo.getId());
        updatedAvaliacaoModelo
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO);

        restAvaliacaoModeloMockMvc.perform(put("/api/avaliacao-modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAvaliacaoModelo)))
            .andExpect(status().isOk());

        // Validate the AvaliacaoModelo in the database
        List<AvaliacaoModelo> avaliacaoModeloList = avaliacaoModeloRepository.findAll();
        assertThat(avaliacaoModeloList).hasSize(databaseSizeBeforeUpdate);
        AvaliacaoModelo testAvaliacaoModelo = avaliacaoModeloList.get(avaliacaoModeloList.size() - 1);
        assertThat(testAvaliacaoModelo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAvaliacaoModelo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingAvaliacaoModelo() throws Exception {
        int databaseSizeBeforeUpdate = avaliacaoModeloRepository.findAll().size();

        // Create the AvaliacaoModelo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAvaliacaoModeloMockMvc.perform(put("/api/avaliacao-modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avaliacaoModelo)))
            .andExpect(status().isCreated());

        // Validate the AvaliacaoModelo in the database
        List<AvaliacaoModelo> avaliacaoModeloList = avaliacaoModeloRepository.findAll();
        assertThat(avaliacaoModeloList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAvaliacaoModelo() throws Exception {
        // Initialize the database
        avaliacaoModeloService.save(avaliacaoModelo);

        int databaseSizeBeforeDelete = avaliacaoModeloRepository.findAll().size();

        // Get the avaliacaoModelo
        restAvaliacaoModeloMockMvc.perform(delete("/api/avaliacao-modelos/{id}", avaliacaoModelo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AvaliacaoModelo> avaliacaoModeloList = avaliacaoModeloRepository.findAll();
        assertThat(avaliacaoModeloList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
