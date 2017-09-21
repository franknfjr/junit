/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufra.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Frank
 */
public class MedalhaDAO extends BaseDAO {
	public Medalha getMedalhaById(Long id) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("select * from medalha where id=?");
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Medalha m = createMedalha(rs);
				rs.close();
				return m;
			}
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}


	public List<Medalha> findByTipo(String tipo) throws SQLException {
		List<Medalha> medalhas = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("select * from medalha where lower(tipo) like ?");
			stmt.setString(1, "%" + tipo.toLowerCase() +"%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Medalha m = createMedalha(rs);
				medalhas.add(m);
			}
			rs.close();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return medalhas;
	}

	public List<Medalha> getMedalhas() throws SQLException {
		List<Medalha> medalhas = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("select * from medalha");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Medalha m = createMedalha(rs);
				medalhas.add(m);
			}
			rs.close();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return medalhas;
	}

	public Medalha createMedalha(ResultSet rs) throws SQLException {
		Medalha m = new Medalha();
		m.setId(rs.getLong("id"));
		m.setTipo(rs.getString("tipo"));
		m.setDesc(rs.getString("descricao"));
		m.setPeso(rs.getString("peso"));
		m.setFabricacao(rs.getString("fabricacao"));
		m.setSede(rs.getString("sede"));
		m.setUrlFoto(rs.getString("url_foto"));
		return m;
	}

	public void save(Medalha m) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			if (m.getId() == null) {
				stmt = conn
						.prepareStatement(
								"insert into medalha (tipo, descricao, peso, fabricacao, sede, url_foto) VALUES(?,?,?,?,?,?)",
								Statement.RETURN_GENERATED_KEYS);
			} else {
				stmt = conn
						.prepareStatement("update medalha set tipo=?,descricao=?,peso=?,fabricacao=?,sede=?,url_foto=? where id=?");
			}
			stmt.setString(1, m.getTipo());
			stmt.setString(2, m.getDesc());
			stmt.setString(3, m.getPeso());
			stmt.setString(4, m.getFabricacao());
			stmt.setString(5, m.getSede());
			stmt.setString(6, m.getUrlFoto());
			if (m.getId() != null) {
				// Update
				stmt.setLong(7, m.getId());
			}
			int count = stmt.executeUpdate();
			if (count == 0) {
				throw new SQLException("Erro ao inserir medalha");
			}
			// Se inseriu, ler o id auto incremento
			if (m.getId() == null) {
				Long id = getGeneratedId(stmt);
				m.setId(id);
			}
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	// id gerado com o campo auto incremento
	public static Long getGeneratedId(Statement stmt) throws SQLException {
		ResultSet rs = stmt.getGeneratedKeys();
		if (rs.next()) {
			Long id = rs.getLong(1);
			return id;
		}
		return 0L;
	}

	public boolean delete(Long id) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("delete from medalha where id=?");
			stmt.setLong(1, id);
			int count = stmt.executeUpdate();
			boolean ok = count > 0;
			return ok;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
}