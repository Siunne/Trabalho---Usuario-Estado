package br.com.aplcurso.controller.estado;

import br.com.aplcurso.dao.EstadoDAO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EstadoListar")
public class EstadoListar extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            EstadoDAO dao = new EstadoDAO();

            request.setAttribute(
                    "listaEstados",
                    dao.listar()
            );

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        request.getRequestDispatcher(
                "/cadastros/estado/estado.jsp"
        ).forward(request, response);
    }
}