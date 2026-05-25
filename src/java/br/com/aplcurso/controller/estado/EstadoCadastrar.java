package br.com.aplcurso.controller.estado;

import br.com.aplcurso.dao.EstadoDAO;
import br.com.aplcurso.model.Estado;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EstadoCadastrar")
public class EstadoCadastrar extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        String nomeEstado =
                request.getParameter("nomeEstado");

        String siglaEstado =
                request.getParameter("siglaEstado");

        try {

            EstadoDAO dao = new EstadoDAO();

            Estado estado = new Estado();

            estado.setNomeEstado(nomeEstado);

            estado.setSiglaEstado(siglaEstado);
            int id = 0;

            try {

                if (idParam != null &&
                    !idParam.trim().equals("")) {

                    id = Integer.parseInt(idParam);
                }

            } catch (Exception e) {

                id = 0;
            }

            estado.setId(id);

            if (estado.getId() > 0) {

                dao.alterar(estado);

                request.setAttribute(
                        "mensagem",
                        "Estado alterado!"
                );

            } else {

                if (dao.verificarSigla(siglaEstado)) {

                    request.setAttribute(
                            "mensagem",
                            "Sigla já cadastrada!"
                    );

                } else {

                    dao.cadastrar(estado);

                    request.setAttribute(
                            "mensagem",
                            "Estado cadastrado!"
                    );
                }
            }

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