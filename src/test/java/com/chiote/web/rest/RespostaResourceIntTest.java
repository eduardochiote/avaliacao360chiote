package com.chiote.web.rest;

import com.chiote.Avaliacao360ChioteApp;

import com.chiote.domain.Resposta;
import com.chiote.domain.Pergunta;
import com.chiote.domain.Avaliacao;
import com.chiote.repository.RespostaRepository;
import com.chiote.service.RespostaService;

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
 * Test class for the RespostaResource REST controller.
 *
 * @see RespostaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Avaliacao360ChioteApp.class)
public class RespostaResourceIntTest {

    private static final Integer DEFAULT_NOTA = 1;
    private static final Integer UPDATED_NOTA = 2;

    @Inject
    private RespostaRepository respostaRepository;

    @Inject
    private RespostaService respostaService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restRespostaMockMvc;

    private Resposta resposta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RespostaResource respostaResource = new RespostaResource();
        ReflectionTestUtils.setField(respostaResource, "respostaService", respostaService);
        this.restRespostaMockMvc = MockMvcBuilders.standaloneSetup(respostaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resposta createEntity(EntityManager em) {
        Resposta resposta = new Resposta()
                .nota(DEFAULT_NOTA);
        // Add required entity
        Pergunta pergunta = PerguntaResourceIntTest.createEntity(em);
        em.persist(pergunta);
        em.flush();
        resposta.setPergunta(pergunta);
        // Add required entity
        Avaliacao avaliacao = AvaliacaoResourceIntTest.createEntity(em);
        em.persist(avaliacao);
        em.flush();
        resposta.setAvaliacao(avaliacao);
        return resposta;
    }

    @Before
    public void initTest() {
        resposta = createEntity(em);
    }

    @Test
    @Transactional
    public void createResposta() throws Exception {
        int databaseSizeBeforeCreate = respostaRepository.findAll().size();

        // Create the Resposta

        restRespostaMockMvc.perform(post("/api/respostas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resposta)))
            .andExpect(status().isCreated());

        // Validate the Resposta in the database
        List<Resposta> respostaList = respostaRepository.findAll();
        assertThat(respostaList).hasSize(databaseSizeBeforeCreate + 1);
        Resposta testResposta = respostaList.get(respostaList.size() - 1);
        assertThat(testResposta.getNota()).isEqualTo(DEFAULT_NOTA);
    }

    @Test
    @Transactional
    public void createRespostaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = respostaRepository.findAll().size();

        // Create the Resposta with an existing ID
        Resposta existingResposta = new Resposta();
        existingResposta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRespostaMockMvc.perform(post("/api/respostas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingResposta)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Resposta> respostaList = respostaRepository.findAll();
        assertThat(respostaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNotaIsRequired() throws Exception {
        int databaseSizeBeforeTest = respostaRepository.findAll().size();
        // set the field null
        resposta.setNota(null);

        // Create the Resposta, which fails.

        restRespostaMockMvc.perform(post("/api/respostas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resposta)))
            .andExpect(status().isBadRequest());

        List<Resposta> respostaList = respostaRepository.findAll();
        assertThat(respostaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRespostas() throws Exception {
        // Initialize the database
        respostaRepository.saveAndFlush(resposta);

        // Get all the respostaList
        restRespostaMockMvc.perform(get("/api/respostas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resposta.getId().intValue())))
            .andExpect(jsonPath("$.[*].nota").value(hasItem(DEFAULT_NOTA)));
    }

    @Test
    @Transactional
    public void getResposta() throws Exception {
        // Initialize the database
        respostaRepository.saveAndFlush(resposta);

        // Get the resposta
        restRespostaMockMvc.perform(get("/api/respostas/{id}", resposta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resposta.getId().intValue()))
            .andExpect(jsonPath("$.nota").value(DEFAULT_NOTA));
    }

    @Test
    @Transactional
    public void getNonExistingResposta() throws Exception {
        // Get the resposta
        restRespostaMockMvc.perform(get("/api/respostas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResposta() throws Exception {
        // Initialize the database
        respostaService.save(resposta);

        int databaseSizeBeforeUpdate = respostaRepository.findAll().size();

        // Update the resposta
        Resposta updatedResposta = respostaRepository.findOne(resposta.getId());
        updatedResposta
                .nota(UPDATED_NOTA);

        restRespostaMockMvc.perform(put("/api/respostas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResposta)))
            .andExpect(status().isOk());

        // Validate the Resposta in the database
        List<Resposta> respostaList = respostaRepository.findAll();
        assertThat(respostaList).hasSize(databaseSizeBeforeUpdate);
        Resposta testResposta = respostaList.get(respostaList.size() - 1);
        assertThat(testResposta.getNota()).isEqualTo(UPDATED_NOTA);
    }

    @Test
    @Transactional
    public void updateNonExistingResposta() throws Exception {
        int databaseSizeBeforeUpdate = respostaRepository.findAll().size();

        // Create the Resposta

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRespostaMockMvc.perform(put("/api/respostas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resposta)))
            .andExpect(status().isCreated());

        // Validate the Resposta in the database
        List<Resposta> respostaList = respostaRepository.findAll();
        assertThat(respostaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteResposta() throws Exception {
        // Initialize the database
        respostaService.save(resposta);

        int databaseSizeBeforeDelete = respostaRepository.findAll().size();

        // Get the resposta
        restRespostaMockMvc.perform(delete("/api/respostas/{id}", resposta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Resposta> respostaList = respostaRepository.findAll();
        assertThat(respostaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
