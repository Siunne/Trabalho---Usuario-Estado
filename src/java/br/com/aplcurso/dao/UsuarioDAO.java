package br.com.aplcurso.dao;

import br.com.aplcurso.model.Usuario;
import br.com.aplcurso.utils.SingleConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements GenericDAO {

    private Connection conexao;

    public UsuarioDAO() throws Exception {
        conexao = SingleConnection.getConnection();
    }

    @Override
    public Boolean cadastrar(Object objeto) {
        Usuario u = (Usuario) objeto;
        return inserir(u);
    }

    @Override
    public Boolean inserir(Object objeto) {

        Usuario u = (Usuario) objeto;

        String sql = "INSERT INTO usuario (nome, datanascimento, cpf, email, senha, salario) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, u.getNome());
            stmt.setDate(2, new java.sql.Date(u.getDataNascimento().getTime()));
            stmt.setString(3, u.getCpf());
            stmt.setString(4, u.getEmail());
            stmt.setString(5, u.getSenha());
            stmt.setDouble(6, u.getSalario());

            int linhas = stmt.executeUpdate();
            conexao.commit();

            return linhas > 0;

        } catch (Exception ex) {
            try {
                conexao.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean alterar(Object objeto) {

        Usuario u = (Usuario) objeto;

        String sql = "UPDATE usuario SET nome=?, datanascimento=?, cpf=?, email=?, senha=?, salario=? WHERE id=?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, u.getNome());

            if (u.getDataNascimento() != null) {
                stmt.setDate(2, new java.sql.Date(u.getDataNascimento().getTime()));
            } else {
                stmt.setNull(2, Types.DATE);
            }

            stmt.setString(3, u.getCpf());
            stmt.setString(4, u.getEmail());
            stmt.setString(5, u.getSenha());
            stmt.setDouble(6, u.getSalario());
            stmt.setInt(7, u.getId());

            int linhas = stmt.executeUpdate();
            conexao.commit();

            return linhas > 0;

        } catch (Exception ex) {
            try {
                conexao.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean excluir(int numero) {

        String sql = "DELETE FROM usuario WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, numero);

            int linhas = stmt.executeUpdate();
            conexao.commit();

            return linhas > 0;

        } catch (Exception ex) {
            try {
                conexao.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Object carregar(int numero) {

        String sql = "SELECT * FROM usuario WHERE id = ?";
        Usuario u = null;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, numero);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setNome(rs.getString("nome"));
                    u.setCpf(rs.getString("cpf"));
                    u.setEmail(rs.getString("email"));
                    u.setSenha(rs.getString("senha"));
                    u.setSalario(rs.getDouble("salario"));
                    u.setDataNascimento(rs.getDate("datanascimento"));
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return u;
    }

    @Override
    public List<Object> listar() {

        List<Object> lista = new ArrayList<>();

        String sql = "SELECT * FROM usuario ORDER BY id";

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setCpf(rs.getString("cpf"));
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));
                u.setSalario(rs.getDouble("salario"));
                u.setDataNascimento(rs.getDate("datanascimento"));

                lista.add(u);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    public boolean cpfExiste(String cpf) {

        String sql = "SELECT COUNT(*) FROM usuario WHERE cpf = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, cpf);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean cpfExiste(String cpf, int idIgnorar) {

        String sql = "SELECT COUNT(*) FROM usuario WHERE cpf = ? AND id <> ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            stmt.setInt(2, idIgnorar);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}