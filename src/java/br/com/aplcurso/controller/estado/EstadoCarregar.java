package br.com.aplcurso.controller.estado;

import br.com.aplcurso.dao.EstadoDAO;
import br.com.aplcurso.model.Estado;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EstadoCarregar")
public class EstadoCarregar extends HttpServlet {

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

            Estado estado = dao.carregar(id);

            request.setAttribute(
                    "estado",
                    estado
            );

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