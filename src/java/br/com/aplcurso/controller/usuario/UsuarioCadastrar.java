package br.com.aplcurso.controller.usuario;

import br.com.aplcurso.dao.UsuarioDAO;
import br.com.aplcurso.model.Usuario;
import br.com.aplcurso.utils.DocumentoValidador;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UsuarioCadastrar", urlPatterns = {"/UsuarioCadastrar"})
public class UsuarioCadastrar extends HttpServlet {

    protected void processRequest(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain;charset=UTF-8");

        try {

            UsuarioDAO dao = new UsuarioDAO();

            int id = 0;

            try {

                String idParam = request.getParameter("id");

                if (idParam != null
                        && !idParam.trim().equals("")) {

                    id = Integer.parseInt(idParam);
                }

            } catch (Exception e) {

                id = 0;
            }

            String nome =
                    valorOuPadrao(
                            request.getParameter("nome"),
                            ""
                    ).trim();

            String cpf =
                    valorOuPadrao(
                            request.getParameter("cpf"),
                            ""
                    ).replaceAll("[^\\d]", "");

            String email =
                    valorOuPadrao(
                            request.getParameter("email"),
                            ""
                    ).trim();

            String senha =
                    valorOuPadrao(
                            request.getParameter("senha"),
                            ""
                    ).trim();

            String dataNascimentoParam =
                    valorOuPadrao(
                            request.getParameter("datanascimento"),
                            ""
                    ).trim();

            String salarioStr =
                    valorOuPadrao(
                            request.getParameter("salario"),
                            "0"
                    );

         
            salarioStr = salarioStr
                    .replace("R$", "")
                    .replace(".", "")
                    .replace(",", ".")
                    .trim();

            double salario = Double.parseDouble(salarioStr);

        
            if (nome.isEmpty()
                    || cpf.isEmpty()
                    || dataNascimentoParam.isEmpty()
                    || email.isEmpty()
                    || senha.isEmpty()
                    || salario <= 0) {

                response.getWriter().write("5");
                return;
            }

            Date dataNascimento =
                    java.sql.Date.valueOf(dataNascimentoParam);

          
            if (!DocumentoValidador.isDocumentoValidador(cpf)) {

                response.getWriter().write("3");
                return;
            }

            
            if (id == 0 && dao.cpfExiste(cpf)) {

                response.getWriter().write("4");
                return;
            }

            if (id > 0 && dao.cpfExiste(cpf, id)) {

                response.getWriter().write("4");
                return;
            }

        
            Usuario oUsuario = new Usuario();

            oUsuario.setId(id);
            oUsuario.setNome(nome);
            oUsuario.setCpf(cpf);
            oUsuario.setDataNascimento(dataNascimento);
            oUsuario.setEmail(email);
            oUsuario.setSenha(senha);
            oUsuario.setSalario(salario);

            boolean resultado;

           
            if (id > 0) {

                resultado = dao.alterar(oUsuario);

            } else {

            
                resultado = dao.cadastrar(oUsuario);
            }

          
            if (resultado) {

                response.getWriter().write("1");

            } else {

                response.getWriter().write("0");
            }

        } catch (Exception ex) {

            System.out.println(
                    "Problemas no Servlet ao cadastrar Usuario! Erro: "
                    + ex.getMessage()
            );

            ex.printStackTrace();

            response.getWriter().write("0");
        }
    }

    private String valorOuPadrao(
            String valor,
            String padrao) {

        return valor == null ? padrao : valor;
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {

        return "Cadastro e alteracao de usuario";
    }
}