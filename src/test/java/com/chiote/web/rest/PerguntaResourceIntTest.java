package com.chiote.web.rest;

import com.chiote.Avaliacao360ChioteApp;

import com.chiote.domain.Pergunta;
import com.chiote.repository.PerguntaRepository;
import com.chiote.service.PerguntaService;

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
 * Test class for the PerguntaResource REST controller.
 *
 * @see PerguntaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Avaliacao360ChioteApp.class)
public class PerguntaResourceIntTest {

    private static final String DEFAULT_ASSUNTO = "AAAAAAAAAA";
    private static final String UPDATED_ASSUNTO = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO = "BBBBBBBBBB";

    @Inject
    private PerguntaRepository perguntaRepository;

    @Inject
    private PerguntaService perguntaService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPerguntaMockMvc;

    private Pergunta pergunta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PerguntaResource perguntaResource = new PerguntaResource();
        ReflectionTestUtils.setField(perguntaResource, "perguntaService", perguntaService);
        this.restPerguntaMockMvc = MockMvcBuilders.standaloneSetup(perguntaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pergunta createEntity(EntityManager em) {
        Pergunta pergunta = new Pergunta()
                .assunto(DEFAULT_ASSUNTO)
                .texto(DEFAULT_TEXTO);
        return pergunta;
    }

    @Before
    public void initTest() {
        pergunta = createEntity(em);
    }

    @Test
    @Transactional
    public void createPergunta() throws Exception {
        int databaseSizeBeforeCreate = perguntaRepository.findAll().size();

        // Create the Pergunta

        restPerguntaMockMvc.perform(post("/api/perguntas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pergunta)))
            .andExpect(status().isCreated());

        // Validate the Pergunta in the database
        List<Pergunta> perguntaList = perguntaRepository.findAll();
        assertThat(perguntaList).hasSize(databaseSizeBeforeCreate + 1);
        Pergunta testPergunta = perguntaList.get(perguntaList.size() - 1);
        assertThat(testPergunta.getAssunto()).isEqualTo(DEFAULT_ASSUNTO);
        assertThat(testPergunta.getTexto()).isEqualTo(DEFAULT_TEXTO);
    }

    @Test
    @Transactional
    public void createPerguntaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = perguntaRepository.findAll().size();

        // Create the Pergunta with an existing ID
        Pergunta existingPergunta = new Pergunta();
        existingPergunta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerguntaMockMvc.perform(post("/api/perguntas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPergunta)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Pergunta> perguntaList = perguntaRepository.findAll();
        assertThat(perguntaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAssuntoIsRequired() throws Exception {
        int databaseSizeBeforeTest = perguntaRepository.findAll().size();
        // set the field null
        pergunta.setAssunto(null);

        // Create the Pergunta, which fails.

        restPerguntaMockMvc.perform(post("/api/perguntas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pergunta)))
            .andExpect(status().isBadRequest());

        List<Pergunta> perguntaList = perguntaRepository.findAll();
        assertThat(perguntaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTextoIsRequired() throws Exception {
        int databaseSizeBeforeTest = perguntaRepository.findAll().size();
        // set the field null
        pergunta.setTexto(null);

        // Create the Pergunta, which fails.

        restPerguntaMockMvc.perform(post("/api/perguntas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pergunta)))
            .andExpect(status().isBadRequest());

        List<Pergunta> perguntaList = perguntaRepository.findAll();
        assertThat(perguntaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPerguntas() throws Exception {
        // Initialize the database
        perguntaRepository.saveAndFlush(pergunta);

        // Get all the perguntaList
        restPerguntaMockMvc.perform(get("/api/perguntas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pergunta.getId().intValue())))
            .andExpect(jsonPath("$.[*].assunto").value(hasItem(DEFAULT_ASSUNTO.toString())))
            .andExpect(jsonPath("$.[*].texto").value(hasItem(DEFAULT_TEXTO.toString())));
    }

    @Test
    @Transactional
    public void getPergunta() throws Exception {
        // Initialize the database
        perguntaRepository.saveAndFlush(pergunta);

        // Get the pergunta
        restPerguntaMockMvc.perform(get("/api/perguntas/{id}", pergunta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pergunta.getId().intValue()))
            .andExpect(jsonPath("$.assunto").value(DEFAULT_ASSUNTO.toString()))
            .andExpect(jsonPath("$.texto").value(DEFAULT_TEXTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPergunta() throws Exception {
        // Get the pergunta
        restPerguntaMockMvc.perform(get("/api/perguntas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePergunta() throws Exception {
        // Initialize the database
        perguntaService.save(pergunta);

        int databaseSizeBeforeUpdate = perguntaRepository.findAll().size();

        // Update the pergunta
        Pergunta updatedPergunta = perguntaRepository.findOne(pergunta.getId());
        updatedPergunta
                .assunto(UPDATED_ASSUNTO)
                .texto(UPDATED_TEXTO);

        restPerguntaMockMvc.perform(put("/api/perguntas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPergunta)))
            .andExpect(status().isOk());

        // Validate the Pergunta in the database
        List<Pergunta> perguntaList = perguntaRepository.findAll();
        assertThat(perguntaList).hasSize(databaseSizeBeforeUpdate);
        Pergunta testPergunta = perguntaList.get(perguntaList.size() - 1);
        assertThat(testPergunta.getAssunto()).isEqualTo(UPDATED_ASSUNTO);
        assertThat(testPergunta.getTexto()).isEqualTo(UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void updateNonExistingPergunta() throws Exception {
        int databaseSizeBeforeUpdate = perguntaRepository.findAll().size();

        // Create the Pergunta

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPerguntaMockMvc.perform(put("/api/perguntas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pergunta)))
            .andExpect(status().isCreated());

        // Validate the Pergunta in the database
        List<Pergunta> perguntaList = perguntaRepository.findAll();
        assertThat(perguntaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePergunta() throws Exception {
        // Initialize the database
        perguntaService.save(pergunta);

        int databaseSizeBeforeDelete = perguntaRepository.findAll().size();

        // Get the pergunta
        restPerguntaMockMvc.perform(delete("/api/perguntas/{id}", pergunta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pergunta> perguntaList = perguntaRepository.findAll();
        assertThat(perguntaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
