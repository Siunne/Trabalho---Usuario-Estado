<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<jsp:include page="/header.jsp"/>
<jsp:include page="/menu.jsp"/>

<fmt:formatDate value="${usuario.dataNascimento}" pattern="yyyy-MM-dd" var="dataNascimentoFormatada"/>

<div class="container-fluid">
    <h1 class="h3 mb-2 text-gray-800">Usuarios</h1>
    <p class="mb-4">Formulario de Cadastro</p>

    <a class="btn btn-secondary mb-4" href="${pageContext.request.contextPath}/UsuarioListar">
        <strong>Voltar</strong>
    </a>

    <div class="row">
        <div class="col-lg-9">
            <div class="card shadow mb-4">
                <div class="card-body">
                    <div class="form-group">
                        <label>Id</label>
                        <input class="form-control" type="text" name="id" id="id"
                               value="${usuario.id}" readonly="readonly"/>
                    </div>

                    <div class="form-group">
                        <label>Nome</label>
                        <input class="form-control" type="text" name="nome" id="nome"
                               value="${usuario.nome}" size="100" maxlength="100"/>
                    </div>

                    <div class="form-group">
                        <label>CPF</label>
                        <input class="form-control" type="text" name="cpf" id="cpf"
                               value="${usuario.cpf}" size="20" maxlength="20"/>
                    </div>

                    <div class="form-group">
                        <div class="form-line row">
                            <div class="col-sm">
                                <label>Data de Nascimento</label>
                                <input class="form-control" type="date" name="datanascimento" id="datanascimento"
                                       value="${dataNascimentoFormatada}"/>
                            </div>

                            <div class="col-sm">
                                <label>Valor do Salario</label>
                                <input class="form-control" type="text" style="text-align:right;"
                                       name="salario" id="salario"
                                       value="<fmt:formatNumber value='${usuario.salario}' type='currency'/>"/>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label>Email</label>
                        <input class="form-control" type="email" name="email" id="email"
                               value="${usuario.email}" size="100" maxlength="100"/>
                    </div>

                    <div class="form-group">
                        <label>Senha</label>
                        <input class="form-control" type="password" name="senha" id="senha"
                               value="${usuario.senha}" size="100" maxlength="100"/>
                    </div>

                    <div class="form-group">
                        <button class="btn btn-success" type="button" id="submit" onclick="return validarCampos();">
                            Salvar Documento
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
$(document).ready(function () {
    console.log("entrei na ready do documento");

    $("#salario").maskMoney({
        prefix: 'R$',
        suffix: '',
        allowZero: false,
        allowNegative: false,
        allowEmpty: false,
        doubleClickSelection: true,
        selectAllOnFocus: true,
        thousands: '.',
        decimal: ',',
        precision: 2,
        affixesStay: true,
        bringCareAtEndOnFocus: true
    });

    $('#cpf').focus(function () {
        trocaMascara();
        this.select();
    });

    $('#cpf').blur(function () {
        var cpfLimpo = $('#cpf').unmask().val();

        if (cpfLimpo === '') {
            return;
        }

        if (!validarCpfCnpj(cpfLimpo)) {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: 'Verifique o CPF/CNPJ!',
                showConfirmButton: true,
                timer: 10000
            });
            return;
        }

        trocaMascara($('#cpf').val());

        $.ajax({
            type: 'get',
            url: '${pageContext.request.contextPath}/UsuarioVerificarCPF',
            data: {
                cpf: cpfLimpo,
                id: $('#id').val()
            },
            success: function (response) {
                if (response == '1') {
                    Swal.fire({
                        position: 'center',
                        icon: 'warning',
                        title: 'CPF ja cadastrado!',
                        text: 'Por favor, verifique o CPF informado.',
                        showConfirmButton: true,
                        timer: 4000
                    }).then(function () {
                        $('#cpf').focus();
                    });
                }
            },
            error: function () {
                console.log("Erro ao verificar CPF no servidor.");
            }
        });
    });

    $('#nome').focus();
});

function trocaMascara(cpfCnpj) {
    if (typeof cpfCnpj === 'undefined') {
        cpfCnpj = "";
    }

    cpfCnpj = cpfCnpj.trim();

    if (cpfCnpj !== "") {
        var masks = ['999.999.999-99', '99.999.999/9999-99'];
        var cpfcnpj = cpfCnpj.replace(/\D/g, '');
        var mask = (cpfcnpj.length > 11) ? masks[1] : masks[0];

        $('#cpf').unmask().mask(mask);
    } else {
        $('#cpf').unmask();
    }
}

function validarCampos() {
    console.log("entrei na validacao de campos");

    if ($('#nome').val().trim() === '') {
        Swal.fire({position: 'center', icon: 'error', title: 'Verifique o nome do usuario!', showConfirmButton: false, timer: 1000});
        $('#nome').focus();
        return false;
    }

    if ($('#cpf').val().trim() === '') {
        Swal.fire({position: 'center', icon: 'error', title: 'Verifique o CPF!', showConfirmButton: false, timer: 1000});
        $('#cpf').focus();
        return false;
    }

    if ($('#datanascimento').val().trim() === '') {
        Swal.fire({position: 'center', icon: 'error', title: 'Verifique a data de nascimento!', showConfirmButton: false, timer: 1000});
        $('#datanascimento').focus();
        return false;
    }

    if ($('#salario').val().trim() === '') {
        Swal.fire({position: 'center', icon: 'error', title: 'Verifique o valor do salario!', showConfirmButton: false, timer: 1000});
        $('#salario').focus();
        return false;
    }

    if ($('#email').val().trim() === '') {
        Swal.fire({position: 'center', icon: 'error', title: 'Verifique o email!', showConfirmButton: false, timer: 1000});
        $('#email').focus();
        return false;
    }

    if ($('#senha').val().trim() === '') {
        Swal.fire({position: 'center', icon: 'error', title: 'Verifique a senha!', showConfirmButton: false, timer: 1000});
        $('#senha').focus();
        return false;
    }

    gravarDados();
    return false;
}

function gravarDados() {
    console.log("Gravando dados ....");

    $.ajax({
        type: 'post',
        url: '${pageContext.request.contextPath}/UsuarioCadastrar',
        data: {
            id: $('#id').val(),
            nome: $('#nome').val().toUpperCase(),
            cpf: $('#cpf').unmask().val(),
            datanascimento: $('#datanascimento').val(),
            salario: $('#salario').val(),
            email: $('#email').val(),
            senha: $('#senha').val()
        },
        success: function (data) {
            console.log("resposta servlet->");
            console.log(data);

            if (data == 1) {
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'Sucesso',
                    text: 'Usuario gravado com sucesso!',
                    showConfirmButton: true,
                    timer: 3000
                }).then(function () {
                    window.location.href = '${pageContext.request.contextPath}/UsuarioListar';
                });
            } else if (data == 3) {
                Swal.fire({position: 'center', icon: 'error', title: 'CPF invalido!', showConfirmButton: true, timer: 5000}).then(function () {
                    $('#cpf').focus();
                });
            } else if (data == 4) {
                Swal.fire({position: 'center', icon: 'error', title: 'CPF ja cadastrado!', showConfirmButton: true, timer: 5000}).then(function () {
                    $('#cpf').focus();
                });
            } else if (data == 5) {
                Swal.fire({position: 'center', icon: 'error', title: 'Dados em branco ou nao informados, verifique!', showConfirmButton: true, timer: 5000}).then(function () {
                    $('#nome').focus();
                });
            } else {
                Swal.fire({position: 'center', icon: 'error', title: 'Nao foi possivel gravar o usuario!', showConfirmButton: true, timer: 5000}).then(function () {
                    $('#nome').focus();
                });
            }
        },
        error: function () {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: 'Erro de comunicacao',
                text: 'Falha na comunicacao com o servidor.',
                showConfirmButton: true,
                timer: 5000
            }).then(function () {
                $('#nome').focus();
            });
        }
    });
}
</script>
<jsp:include page="/footer.jsp"/>
