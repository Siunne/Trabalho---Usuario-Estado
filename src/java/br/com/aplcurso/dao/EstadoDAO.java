package br.com.aplcurso.dao;

import br.com.aplcurso.model.Estado;
import br.com.aplcurso.utils.SingleConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

public class EstadoDAO {

    private Connection conexao;

    public EstadoDAO() throws Exception {

        conexao = SingleConnection.getConnection();
    }

    public boolean cadastrar(Estado estado) {

        String sql =
                "insert into estado(nomeEstado, siglaEstado) values (?, ?)";

        try {

            PreparedStatement stmt =
                    conexao.prepareStatement(sql);

            stmt.setString(1, estado.getNomeEstado());

            stmt.setString(2, estado.getSiglaEstado());

            stmt.execute();

            conexao.commit();

            return true;

        } catch (Exception ex) {

            ex.printStackTrace();

            return false;
        }
    }

    public List<Estado> listar() {

        List<Estado> lista = new ArrayList<>();

        String sql = "select * from estado";

        try {

            PreparedStatement stmt =
                    conexao.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {

                Estado estado = new Estado();

                estado.setId(rs.getInt("id"));

                estado.setNomeEstado(
                        rs.getString("nomeEstado")
                );

                estado.setSiglaEstado(
                        rs.getString("siglaEstado")
                );

                lista.add(estado);
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return lista;
    }

    public boolean verificarSigla(String sigla) {

        String sql =
                "select * from estado where siglaEstado = ?";

        try {

            PreparedStatement stmt =
                    conexao.prepareStatement(sql);

            stmt.setString(1, sigla);

            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return false;
    }

    public boolean excluir(int id) {

        String sql = "delete from estado where id = ?";

        try {

            PreparedStatement stmt =
                    conexao.prepareStatement(sql);

            stmt.setInt(1, id);

            stmt.execute();

            conexao.commit();

            return true;

        } catch (Exception ex) {

            ex.printStackTrace();

            return false;
        }
    }

    public Estado carregar(int id) {

        Estado estado = new Estado();

        String sql =
                "select * from estado where id = ?";

        try {

            PreparedStatement stmt =
                    conexao.prepareStatement(sql);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {

                estado.setId(rs.getInt("id"));

                estado.setNomeEstado(
                        rs.getString("nomeEstado")
                );

                estado.setSiglaEstado(
                        rs.getString("siglaEstado")
                );
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return estado;
    }

    public boolean alterar(Estado estado) {

        String sql =
                "update estado set nomeEstado = ?, siglaEstado = ? where id = ?";

        try {

            PreparedStatement stmt =
                    conexao.prepareStatement(sql);

            stmt.setString(1, estado.getNomeEstado());

            stmt.setString(2, estado.getSiglaEstado());

            stmt.setInt(3, estado.getId());

            stmt.execute();

            conexao.commit();

            return true;

        } catch (Exception ex) {

            ex.printStackTrace();

            return false;
        }
    }
}