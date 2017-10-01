/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import br.edu.ufra.domain.BaseDAO;
import br.edu.ufra.domain.Medalha;
import br.edu.ufra.domain.MedalhaDAO;
import br.edu.ufra.domain.MedalhaDelegate;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Frank
 */
public class MedalhaTest extends TestCase {

    private MedalhaDelegate medalhasDelegate = new MedalhaDelegate();

    @Test
    public void testeListaMedalhas() {

        List<Medalha> medalhas = medalhasDelegate.getMedalhas();
        assertNotNull(medalhas);
        //valida se encontrou algo
        assertTrue(medalhas.size() > 0);
        //valida se encontrou uma medalha de Ouro
        Medalha ouro = medalhasDelegate.findByTipo("ouro").get(0);
        assertEquals("ouro", ouro.getTipo());
        //valida se encontrou uma medalha de Prata
        Medalha prata = medalhasDelegate.findByTipo("prata").get(0);
        assertEquals("prata", prata.getTipo());
        //valida se encontrou uma medalha de Bronze
        Medalha bronze = medalhasDelegate.findByTipo("bronze").get(0);
        assertEquals("bronze", bronze.getTipo());
    }

    @Test
    public void testInsert() {
        MedalhaDelegate md = mock(MedalhaDelegate.class);

        Medalha m1 = new Medalha();
        m1.setTipo("Lata123");
        m1.setDesc("Lata desc123");
        m1.setPeso("2000123");
        m1.setFabricacao("2017123");
        m1.setSede("Paraguai123");
        m1.setUrlFoto("www.google.com123");
        medalhasDelegate.save(m1);
        Medalha m2 = new Medalha();
        m2.setTipo("Lata123123");
        m2.setDesc("Lata desc123123");
        m2.setPeso("2000123123");
        m2.setFabricacao("2017123123");
        m2.setSede("Paraguai123123");
        m2.setUrlFoto("www.google.com123123");
        medalhasDelegate.save(m2);

        List<Medalha> medalhas = Arrays.asList(m1, m2);
        when(md.getMedalhas()).thenReturn(medalhas);

    }

    @Test
    public void testSalvarDeletarMedalhaDeLata() {
        Medalha m = new Medalha();
        m.setTipo("Lata");
        m.setDesc("Lata desc");
        m.setPeso("2000");
        m.setFabricacao("2017");
        m.setSede("Paraguai");
        m.setUrlFoto("www.google.com");
        medalhasDelegate.save(m);
        // id da medalha salvo
        Long id = m.getId();
        assertNotNull(id);
        // Busca no banco de dados para confirmar se a medalha foi salva
        m = medalhasDelegate.getMedalha(id);
        assertEquals("Lata", m.getTipo());
        assertEquals("Lata desc", m.getDesc());
        assertEquals("2000", m.getPeso());
        assertEquals("2017", m.getFabricacao());
        assertEquals("Paraguai", m.getSede());
        assertEquals("www.google.com", m.getUrlFoto());
        // Atualiza a medalha
        m.setTipo("Teste UPDATE");
        medalhasDelegate.save(m);
        // Busca a medalha novamente (vai estar atualizado)
        m = medalhasDelegate.getMedalha(id);
        assertEquals("Teste UPDATE", m.getTipo());
        // Deleta a medalha
        medalhasDelegate.delete(id);
        // Busca a medalha novamente
        m = medalhasDelegate.getMedalha(id);
        // Desta vez a medalha não existe mais.
        assertNull(m);
    }

}
