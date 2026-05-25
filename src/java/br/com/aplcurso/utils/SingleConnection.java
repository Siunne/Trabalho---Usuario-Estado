package br.com.aplcurso.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection {

    private static Connection conexao = null;

    private static String servidor =
            "jdbc:postgresql://localhost:5432/bdaplcurso";

    private static String usuario = "postgres";
    private static String senha = "123456";

    static {

        try {

            System.out.println("Tentando conectar...");

            Class.forName("org.postgresql.Driver");

            conexao = DriverManager.getConnection(
                    servidor,
                    usuario,
                    senha);

            conexao.setAutoCommit(false);

            System.out.println("CONECTOU!");

        } catch (Exception ex) {

            System.out.println("ERRO REAL DA CONEXÃO:");

            ex.printStackTrace();
        }
    }

    public static Connection getConnection() {

        System.out.println("Conexao: " + conexao);

        return conexao;
    }
}