<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>

<jsp:include page="/header.jsp"/>
<jsp:include page="/menu.jsp"/>

<div class="container-fluid">

    <h1 class="h3 mb-2 text-gray-800">Estados</h1>
    <p class="mb-4">Cadastro de Estados</p>


    <a class="btn btn-success mb-4"
       href="${pageContext.request.contextPath}/cadastros/estado/estado.jsp">

        <i class="fas fa-sticky-note"></i>
        <strong>Novo</strong>

    </a>
    <div class="card shadow">

        <div class="card-body">

            <form action="${pageContext.request.contextPath}/EstadoCadastrar"
                  method="post">

                <input type="hidden"
                       name="id"
                       value="${estado.id}">

                <div class="row">

                    <div class="col-md-6">

                        <label>Nome Estado</label>

                        <input type="text"
                               name="nomeEstado"
                               class="form-control"
                               maxlength="100"
                               value="${estado.nomeEstado}"
                               required>

                    </div>

                    <div class="col-md-2">

                        <label>Sigla</label>

                        <input type="text"
                               name="siglaEstado"
                               class="form-control"
                               maxlength="2"
                               value="${estado.siglaEstado}"
                               required>

                    </div>

                </div>

                <br>

                <button type="submit"
                        class="btn btn-primary">

                    <c:choose>

                        <c:when test="${estado.id != null}">
                            Alterar
                        </c:when>

                        <c:otherwise>
                            Cadastrar
                        </c:otherwise>

                    </c:choose>

                </button>

            </form>

            <hr>

            <table id="datatable"
                   class="display">

                <thead>

                    <tr>

                        <th align="right">Id</th>
                        <th align="left">Nome Estado</th>
                        <th align="left">Sigla</th>
                        <th align="center">Excluir</th>
                        <th align="center">Alterar</th>

                    </tr>

                </thead>

                <tbody>

                    <c:forEach var="estado"
                               items="${listaEstados}">

                        <tr>

                            <td align="right">
                                ${estado.id}
                            </td>

                            <td align="left">
                                ${estado.nomeEstado}
                            </td>

                            <td align="left">
                                ${estado.siglaEstado}
                            </td>

                            <td align="center">

                                <a href="${pageContext.request.contextPath}/EstadoExcluir?id=${estado.id}">

                                    <button style="
                                            background-color: red;
                                            color: white;
                                            border: none;
                                            padding: 5px 10px;
                                            border-radius: 5px;">

                                        Excluir

                                    </button>

                                </a>

                            </td>

                            <td align="center">

                                <a href="${pageContext.request.contextPath}/EstadoCarregar?id=${estado.id}">

                                    <button style="
                                            background-color: green;
                                            color: white;
                                            border: none;
                                            padding: 5px 10px;
                                            border-radius: 5px;">

                                        Alterar

                                    </button>

                                </a>

                            </td>

                        </tr>

                    </c:forEach>

                </tbody>

            </table>

        </div>

    </div>

</div>

<script>

    $(document).ready(function () {

        $('#datatable').DataTable({

            "oLanguage": {

                "sProcessing": "Processando...",
                "sLengthMenu": "Mostrar _MENU_ registros",
                "sZeroRecords": "Nenhum registro encontrado.",
                "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
                "sInfoEmpty": "Mostrando de 0 até 0 de 0 registros",
                "sSearch": "Buscar:",

                "oPaginate": {

                    "sFirst": "Primeiro",
                    "sPrevious": "Anterior",
                    "sNext": "Seguinte",
                    "sLast": "Último"

                }

            }

        });

    });

</script>

<%@include file="/footer.jsp"%>