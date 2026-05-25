<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Estado</title>
</head>

<body>

    <h1>Cadastro de Estado</h1>
    <hr>

    <form action="${pageContext.request.contextPath}/EstadoCadastrar"
          method="post">

        <label>Nome:</label>
        <input type="text" name="nome">
        <br><br>

        <label>Sigla:</label>
        <input type="text" name="sigla" maxlength="2">
        <br><br>

        <button type="submit">Salvar</button>

    </form>

</body>
</html>