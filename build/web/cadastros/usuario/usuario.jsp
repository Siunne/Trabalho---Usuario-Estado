<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<jsp:include page="/header.jsp"/>
<jsp:include page="/menu.jsp"/>

<div class="container-fluid">
<h1 class="h3 mb-2 text-gray-800">Usuários</h1>
<p class="mb-4">Cadastro de Usuários</p>

<a class="btn btn-success mb-4" href="${pageContext.request.contextPath}/UsuarioNovo">
<i class="fas fa-sticky-note"></i> <strong>Novo</strong>
</a>

<div class="card shadow">
<div class="card-body">

<table id="datatable" class="display">
<thead>
<tr>
<th>Id</th>
<th>Nome</th>
<th>CPF</th>
<th>Email</th>
<th>Nascimento</th>
<th>Salário</th>
<th>Excluir</th>
<th>Alterar</th>
</tr>
</thead>

<tbody>
<c:forEach var="usuario" items="${usuarios}">
<tr>
<td>${usuario.id}</td>
<td>${usuario.nome}</td>
<td>${usuario.cpf}</td>
<td>${usuario.email}</td>
<td><fmt:formatDate pattern="dd/MM/yyyy" value="${usuario.dataNascimento}"/></td>
<td><fmt:formatNumber value="${usuario.salario}" type="currency"/></td>

<td>
<button class="btn btn-danger btn-sm" onclick="deletar(${usuario.id})">Excluir</button>
</td>

<td>
<a href="${pageContext.request.contextPath}/UsuarioCarregar?id=${usuario.id}">
<button class="btn btn-success btn-sm">Alterar</button>
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
$(document).ready(function(){
$('#datatable').DataTable({
"oLanguage":{
"sProcessing":"Processando...",
"sLengthMenu":"Mostrar _MENU_ registros",
"sZeroRecords":"Nenhum registro encontrado.",
"sInfo":"Mostrando _START_ até _END_ de _TOTAL_",
"sSearch":"Buscar:",
"oPaginate":{
"sFirst":"Primeiro",
"sPrevious":"Anterior",
"sNext":"Próximo",
"sLast":"Último"
}
}
});
});

function deletar(id){
Swal.fire({
title:'Confirmar?',
text:'Não poderá reverter!',
icon:'warning',
showCancelButton:true,
confirmButtonColor:'#d33',
cancelButtonColor:'#3085d6',
confirmButtonText:'Sim',
cancelButtonText:'Cancelar'
}).then((result)=>{
if(result.isConfirmed){
$.ajax({
type:'post',
url:'${pageContext.request.contextPath}/UsuarioExcluir',
data:{id:id},
success:function(data){
if(data==1){
Swal.fire('OK','Excluído!','success');
}else{
Swal.fire('Erro','Falha ao excluir','error');
}
window.location.href='${pageContext.request.contextPath}/UsuarioListar';
}
});
}
});
}
</script>

<jsp:include page="/footer.jsp"/>