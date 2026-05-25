package br.com.aplcurso.controller.usuario;

import br.com.aplcurso.dao.UsuarioDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UsuarioVerificarCPF", urlPatterns = {"/UsuarioVerificarCPF", "/UsuarioVerificarCpf"})
public class UsuarioVerificarCPF extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain;charset=UTF-8");

        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            String cpf = request.getParameter("cpf");
            String idParam = request.getParameter("id");
            int id = 0;

            if (idParam != null && !idParam.trim().isEmpty()) {
                id = Integer.parseInt(idParam);
            }

            boolean existe;

            if (id > 0) {
                existe = usuarioDAO.cpfExiste(cpf, id);
            } else {
                existe = usuarioDAO.cpfExiste(cpf);
            }

            response.getWriter().write(existe ? "1" : "0");

        } catch (Exception ex) {
            System.out.println("Problemas no Servlet ao validar CPF de usuario! Erro: " + ex.getMessage());
            ex.printStackTrace();
            response.getWriter().write("0");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Verifica CPF de usuario";
    }
}
