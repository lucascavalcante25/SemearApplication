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

function mostrarFormCadastro() {
	document.getElementById('cadastroAvisoForm').style.display = 'block';
	document.getElementById('botoesCadastro').style.display = 'none';
}

function ocultarFormCadastro() {
	document.getElementById('cadastroAvisoForm').style.display = 'none';
	document.getElementById('botoesCadastro').style.display = 'block';
}

$("#campoData").mask("99/99/9999");


$(document).ready(function() {
	$("#myInput").on("keyup", function() {
		var value = $(this).val().toLowerCase();
		$("#myTableAviso tr").filter(function() {
			$(this).toggle(
				$(this).text().toLowerCase().indexOf(value) > -1)
		});
	});
});

document.addEventListener('DOMContentLoaded', function() {
    // Seleciona o formulário
    const form = document.getElementById('editarAvisoForm');

    // Adiciona um ouvinte para o evento de submissão do formulário
    form.addEventListener('submit', function(event) {
        // Não precisamos mais prevenir o comportamento padrão de envio do formulário
        // event.preventDefault();

        // Agora, o formulário será submetido normalmente, enviando os dados para o servidor
        // e aguardando uma resposta do servidor

        // Você pode adicionar aqui alguma lógica adicional, se necessário

        // Se o processamento do formulário no servidor for bem-sucedido, o navegador será redirecionado automaticamente
        // para /lista-membros, de acordo com a configuração do atributo action do formulário
    });
});

// Função para redirecionar para a página /lista-membros
function voltarParaListaAviso() {
	window.location.href = '/lista-aviso';
}

// Adiciona um ouvinte para o evento de clique do botão "Voltar"
document.getElementById('btnVoltar').addEventListener('click', voltarParaListaAviso);



