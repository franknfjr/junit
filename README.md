# JUnit
Testes unitários com o framework JUnit - Java

## Dominio do problema
A aplicação consiste em um cadastro de medalhas olimpicas para aplicar os conceitos
ministrados na disciplina de Eng. de Software, onde usaremos algumas ferramentas, tais
como o MySQL Workbench 6.3 CE e NetBeans IDE 8.2.

* Para iniciar vamos aos scripts sql de criaçao do banco e tabela:
```sql
create database olimpiada;

use olimpiada;

create table medalha (id bigint not null auto_increment,
tipo varchar(255),
descricao varchar(255),
peso varchar(255),
fabricacao varchar(255),
sede varchar(255),
url_foto varchar(255), primary key (id));
```

* Alguns inserts....
```sql
insert into medalha (tipo, descricao, peso, fabricacao, sede, url_foto) values('ouro','desc medalha de ouro','500','2016','Rio','www.google.com/medalhadeOuro');
insert into medalha (tipo, descricao, peso, fabricacao, sede, url_foto) values('prata','desc medalha de prata','500','2016','Rio','www.google.com/medalhadePrata');
insert into medalha (tipo, descricao, peso, fabricacao, sede, url_foto) values('bronze','desc medalha de bronze','600','2016','Rio','www.google.com/medalhadeBronze');
```

A figura a seguir mostra a estrutura de diretórios e arquivos que precisaremos *criar.*
![Image Teste 01](https://github.com/franknfjr/junit/blob/master/src/img/01.PNG)

* Nessa parte iremos tentar fazer a conexão com o banco, pra isso abra o arquivo `BaseDAO` e digite:
```java
public class BaseDAO {
    public BaseDAO(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    protected Connection getConnection() throws SQLException{
        String url = "jdbc:mysql://localhost/olimpiada";

        Connection conn = DriverManager.getConnection(url,"root","");

        return conn;
    }

    public static void main(String[] args) throws SQLException {
        BaseDAO db = new BaseDAO();
        Connection conn = db.getConnection();
        System.out.println(conn);
    }
}
```
Quando executarmos o codigo anterior a janela saida do NetBeans informará um erro, pois o
drive JDBC do MySQL ainda não foi adicionado ao `classpath` do *projeto.*

![Image Teste 02](https://github.com/franknfjr/junit/blob/master/src/img/02.PNG)

Para isso iremos na pasta de `Bibliotecas`>`Adicionar JAR/Pastas...` e adicionar o mysql-connector-java.jar
que pode ser facilmente encontrado no [Google!](http://google.com)

Com o driver JDBC do MySQL corretamente configurado execute o arquivo `BaseDAO` novamente e a saída retornará
*com sucesso.*

![Image Teste 03](https://github.com/franknfjr/junit/blob/master/src/img/03.PNG)

## Persistindo a classe Dominio do problema: Medalha

* Nessa parte vamos criar a classe `Medalha` que será persistida na tabela `medalha` abra a classe em questao e digite:
```java
public class Medalha implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String tipo;
    private String desc;
    private String peso;
    private String fabricacao;
    private String sede;
    private String urlFoto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getFabricacao() {
        return fabricacao;
    }

    public void setFabricacao(String fabricacao) {
        this.fabricacao = fabricacao;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    @Override
    public String toString() {
        return "Medalha{" + "id=" + id + ", tipo=" + tipo + ", desc=" + desc + ", peso=" + peso + ", fabricacao=" + fabricacao + ", sede=" + sede + ", urlFoto=" + urlFoto + '}';
    }
}       
```

A Serialização resume-se em salvar, gravar, capturar o estado de um objeto. Ou seja, tenho um objeto de uma classe e quero salvar seu estado.

Pra persistir na classe `Medalha`, iremos criar a classe `MedalhaDAO`:
```java
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
```
Essa classe é filha de `BaseDAO` afim de herdar o método `getConnection()` para obter conexão com o banco.

Seguindo as boas práticas de programação em Java, nao iremos acessar diretamente a camada base de dados, ou seja,
chamar a classe DAO diretamente. Por fim iremos escrever na classe `MedalhaDelegate`:
```java
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
```

Agora que a classe de persistencia esta funcionando, iremos chegar ao apse do projeto e criar um teste
unitário com o JUnit afim de verificar se realmente esta tudo ok.

Iremos novamente acessar o diretorio Bibliotecas no NetBeans, então vá em: `Bibliotecas`>`Adicionar Bibliotecas...` e procure pelo pacote JUnit.

Feito isso, clique no diretorio domain com o lado direito do mouse, `Novo`>`Outros`>`Testes de Unidades`>`Teste JUnit` e depois em próximo, e altere os fields igual os da imagem *a seguir:*

![Image Teste 04](https://github.com/franknfjr/junit/blob/master/src/img/04.PNG)

Apague todos os códigos automatico caso gere algum, e no primeiro teste, iremos listar todas as medalhas e comparar se existe no banco. Modifique o arquivo `MedalhaTest` no pacote `test`.
```java
public class MedalhaTest extends TestCase {

    private MedalhaDelegate medalhasDelegate = new MedalhaDelegate();

    public void testeListaMedalhas(){
        List<Medalha> medalhas = medalhasDelegate.getMedalhas();
        assertNotNull(medalhas);
        //valida se encontrou algo
        assertTrue(medalhas.size()>0);
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

}
```
Ao executar a classe `MedalhaTest` teremos o *seguinte resultado:*

![Image Teste 05](https://github.com/franknfjr/junit/blob/master/src/img/05.PNG)

Como dito no inicio, iremos fazer mais um teste, que irá testar a inserção, consulta, atualização e exclusão de uma medalha, essa ação é conhecida como CRUD(Create, Read, Update e Delete). Vamos adicinar o método `testSalvarDeletarMedalhaDeLata` na classe `MedalhaTest`.
```java
public class MedalhaTest extends TestCase {

    private MedalhaDelegate medalhasDelegate = new MedalhaDelegate();

    public void testeListaMedalhas(){
        List<Medalha> medalhas = medalhasDelegate.getMedalhas();
        ...
    }

    public void testSalvarDeletarMedalhaDeLata(){
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
```
Execute o teste mais uma vez *e...*

![Image Teste 06](https://github.com/franknfjr/junit/blob/master/src/img/06.PNG)
