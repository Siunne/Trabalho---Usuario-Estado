package br.com.aplcurso.controller.estado;

import br.com.aplcurso.dao.EstadoDAO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EstadoExcluir")
public class EstadoExcluir extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(
                request.getParameter("id")
        );

        try {

            EstadoDAO dao = new EstadoDAO();

            dao.excluir(id);

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        response.sendRedirect("EstadoListar");
    }
}