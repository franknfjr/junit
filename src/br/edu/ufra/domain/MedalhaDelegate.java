/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufra.domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Frank
 */
public class MedalhaDelegate {
    private MedalhaDAO db = new MedalhaDAO();

	// Lista todos as medalhas do banco de dados
	public List<Medalha> getMedalhas() {
		try {
			List<Medalha> medalhas = db.getMedalhas();
			return medalhas;
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<Medalha>();
		}
	}

	// Busca uma medalha pelo id
	public Medalha getMedalha(Long id) {
		try {
			return db.getMedalhaById(id);
		} catch (SQLException e) {
			return null;
		}
	}

	// Deleta a medalhas pelo id
	public boolean delete(Long id) {
		try {
			return db.delete(id);
		} catch (SQLException e) {
			return false;
		}
	}

	// Salva ou atualiza as medalhas
	public boolean save(Medalha medalha) {
		try {
			db.save(medalha);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	public List<Medalha> findByTipo(String tipo) {
		try {
			return db.findByTipo(tipo);
		} catch (SQLException e) {
			return null;
		}
	}
}

