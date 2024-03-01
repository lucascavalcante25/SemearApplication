/*!
	* Start Bootstrap - SB Admin v7.0.7 (https://startbootstrap.com/template/sb-admin)
	* Copyright 2013-2023 Start Bootstrap
	* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-sb-admin/blob/master/LICENSE)
	*/
// 
// Scripts
// 

window.addEventListener('DOMContentLoaded', event => {

	// Toggle the side navigation
	const sidebarToggle = document.body.querySelector('#sidebarToggle');
	if (sidebarToggle) {
		// Uncomment Below to persist sidebar toggle between refreshes
		// if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
		//     document.body.classList.toggle('sb-sidenav-toggled');
		// }
		sidebarToggle.addEventListener('click', event => {
			event.preventDefault();
			document.body.classList.toggle('sb-sidenav-toggled');
			localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
		});
	}

});

$(document).ready(function() {
	$("#btnCadastrar").on("click", function() {
		$(this).hide();
		$("#formularioContainer").show();
		$("#btnSairCadastro").show();
	});

	$("#btnSairCadastro").on("click", function() {
		$(this).hide();
		$("#formularioContainer").hide();
		$("#btnCadastrar").show();
	});

	$("form").submit(function(event) {
		event.preventDefault(); // Evitar o comportamento padrão do formulário

		var formData = new FormData($(this)[0]); // Crie um objeto FormData com os dados do formulário

		$.ajax({
			type: "POST",
			url: $(this).attr("action"),
			data: formData, // Use o objeto FormData como dados
			contentType: false, // Não defina o tipo de conteúdo, deixe o jQuery decidir
			processData: false, // Não processe os dados, deixe o FormData lidar com isso
			success: function(response) {
				console.log(response);

				// Limpar o formulário
				$("form")[0].reset();

				// Ocultar o formulário
				$("#formularioContainer").hide();

				// Exibir o botão "Cadastrar Louvor" novamente
				$("#btnCadastrar").show();

				// Atualizar a tabela de louvores
				// Este código assume que a resposta contém a tabela HTML completa
				$("#tabelaLouvores").html(
					$(response).find("#tabelaLouvores").html());
			},
			error: function(error) {
				console.log(error);

				// Exibir mensagem de erro (opcional)
				alert("Erro ao cadastrar louvor");
			}
		});
	});
});


$(document).ready(function() {
	$("#myInput").on("keyup", function() {
		var value = $(this).val().toLowerCase();
		$("#myTable tr").filter(function() {
			$(this).toggle(
				$(this).text().toLowerCase().indexOf(value) > -1)
		});
	});
});

