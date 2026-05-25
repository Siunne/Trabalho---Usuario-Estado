package br.com.aplcurso.dao;

import br.com.aplcurso.model.Usuario;
import br.com.aplcurso.utils.SingleConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements GenericDAO {

    private Connection conexao;

    public UsuarioDAO() throws Exception {
        conexao = SingleConnection.getConnection();
    }

    @Override
    public Boolean cadastrar(Object objeto) {
        Usuario oUsuario = (Usuario) objeto;

        if (oUsuario.getId() == 0) {
            return this.inserir(oUsuario);
        } else {
            return this.alterar(oUsuario);
        }
    }

    @Override
    public Boolean inserir(Object objeto) {
        Usuario oUsuario = (Usuario) objeto;

        String sql = "insert into usuario (nome, datanascimento, cpf, email, senha, salario) "
                + "values (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, oUsuario.getNome());
            stmt.setDate(2, new java.sql.Date(oUsuario.getDataNascimento().getTime()));
            stmt.setString(3, oUsuario.getCpf());
            stmt.setString(4, oUsuario.getEmail());
            stmt.setString(5, oUsuario.getSenha());
            stmt.setDouble(6, oUsuario.getSalario());

            int linhas = stmt.executeUpdate();
            conexao.commit();

            return linhas > 0;

        } catch (Exception ex) {
            try {
                System.out.println("Problemas ao cadastrar o Usuario: " + ex.getMessage());
                ex.printStackTrace();
                conexao.rollback();
            } catch (SQLException e) {
                System.out.println("Erro no rollback: " + e.getMessage());
                e.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public Boolean alterar(Object objeto) {
        Usuario oUsuario = (Usuario) objeto;

        String sql = "update usuario set nome = ?, datanascimento = ?, cpf = ?, email = ?, senha = ?, salario = ? "
                + "where id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, oUsuario.getNome());
            stmt.setDate(2, new java.sql.Date(oUsuario.getDataNascimento().getTime()));
            stmt.setString(3, oUsuario.getCpf());
            stmt.setString(4, oUsuario.getEmail());
            stmt.setString(5, oUsuario.getSenha());
            stmt.setDouble(6, oUsuario.getSalario());
            stmt.setInt(7, oUsuario.getId());

            int linhas = stmt.executeUpdate();
            conexao.commit();

            return linhas > 0;

        } catch (Exception ex) {
            try {
                System.out.println("Problemas ao alterar o Usuario: " + ex.getMessage());
                ex.printStackTrace();
                conexao.rollback();
            } catch (SQLException e) {
                System.out.println("Erro no rollback: " + e.getMessage());
                e.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public Boolean excluir(int numero) {
        String sql = "delete from usuario where id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, numero);

            int linhas = stmt.executeUpdate();
            conexao.commit();

            return linhas > 0;

        } catch (Exception ex) {
            try {
                System.out.println("Problemas ao excluir usuario: " + ex.getMessage());
                ex.printStackTrace();
                conexao.rollback();
            } catch (SQLException e) {
                System.out.println("Erro no rollback: " + e.getMessage());
                e.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public Object carregar(int numero) {
        String sql = "select * from usuario where id = ?";
        Usuario oUsuario = null;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, numero);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    oUsuario = new Usuario();
                    oUsuario.setId(rs.getInt("id"));
                    oUsuario.setNome(rs.getString("nome"));
                    oUsuario.setCpf(rs.getString("cpf"));
                    oUsuario.setEmail(rs.getString("email"));
                    oUsuario.setSenha(rs.getString("senha"));
                    oUsuario.setSalario(rs.getDouble("salario"));
                    oUsuario.setDataNascimento(rs.getDate("datanascimento"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao carregar usuario: " + ex.getMessage());
            ex.printStackTrace();
        }

        return oUsuario;
    }

    @Override
    public List<Object> listar() {
        List<Object> resultado = new ArrayList<>();
        String sql = "select * from usuario order by id";

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario oUsuario = new Usuario();
                oUsuario.setId(rs.getInt("id"));
                oUsuario.setNome(rs.getString("nome"));
                oUsuario.setCpf(rs.getString("cpf"));
                oUsuario.setEmail(rs.getString("email"));
                oUsuario.setSenha(rs.getString("senha"));
                oUsuario.setSalario(rs.getDouble("salario"));
                oUsuario.setDataNascimento(rs.getDate("datanascimento"));

                resultado.add(oUsuario);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar usuarios: " + ex.getMessage());
            ex.printStackTrace();
        }

        return resultado;
    }

    public boolean cpfExiste(String cpf) {
        String sql = "select count(*) as quantidade_cpf from usuario where cpf = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpf);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("quantidade_cpf") > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar CPF: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean cpfExiste(String cpf, int idIgnorar) {
        String sql = "select count(*) as quantidade_cpf from usuario where cpf = ? and id <> ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.setInt(2, idIgnorar);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("quantidade_cpf") > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar CPF: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}
