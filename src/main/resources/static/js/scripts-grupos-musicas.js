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
	// Adiciona um ouvinte de evento de clique a todos os botões de expansão
	$('.btn-outline-primary').on('click', function() {
		// Encontre o bloco de detalhes associado usando o atributo data-target
		var target = $($(this).data('target'));

		// Inverte a visibilidade do bloco de detalhes usando o Bootstrap collapse
		target.collapse('toggle');
	});

	// Inicializar Sortable para cada grupo
	for (let i = 1; i <= 8; i++) {
		let groupID = "grupo" + i;
		Sortable.create(document.getElementById(groupID), {
			group: 'louvor-groups',
			draggable: '.list-group-item',
			handle: '.list-group-item',
			sort: true,
			chosenClass: 'active',
			onAdd: function(evt) {
				// Lógica a ser executada quando um item é adicionado ao grupo
				console.log("Item adicionado ao grupo " + i
					+ ": ", evt.item.textContent);

				// Adiciona o ID do louvor diretamente ao elemento da lista
				var louvorId = evt.item
					.querySelector('.louvor-id').value;
				var nomeDoGrupo = "Grupo " + i; // Altere conforme necessário
				console.log(louvorId);

				// Chame a função para salvar o louvor no grupo
				salvarLouvorNoGrupo(louvorId, i,
					nomeDoGrupo);
			}
		});
	}

	// Inicializar Sortable para cada lista de louvores
	for (let i = 1; i <= 3; i++) { // assumindo 3 tipos de louvor (JUBILO, ADORACAO, CEIA)
		let louvorListID = "lista-louvores-" + i;
		Sortable.create(document.getElementById(louvorListID), {
			group: 'louvor-groups',
			draggable: '.list-group-item',
			handle: '.list-group-item',
			sort: true,
			chosenClass: 'active',
			onStart: function(evt) {
				// Lógica a ser executada quando um item é arrastado da lista de louvores
				console.log("Item iniciou o arraste: ",
					evt.item.textContent);
			}
		});
	}
});

$(document).ready(function() {
	$('.btn-voltar').on('click', function() {
		var louvorId = $(this).data('louvor-id');
		var tipoLouvor = $(this).data('louvor-origem');
		var listItemToRemove = $(this).closest('.list-group-item');
		var listItemData = listItemToRemove.html();

		$.ajax({
			type: 'POST',
			url: '/mover-para-lista-de-origem',
			data: {
				louvorId: louvorId,
				tipoLouvor: tipoLouvor
			},
			success: function(
				response) {
				alert(response);
				listItemToRemove.remove();

				// Adicionar o louvor de volta à lista correspondente
				var listaCorrespondenteId = '#lista-louvores-' + obterIndiceLista(tipoLouvor);
				if (listaCorrespondenteId !== '#lista-louvores-1') { // Verificar se não é a lista principal
					$(listaCorrespondenteId).append('<li class="list-group-item">' + listItemData + '</li>');
				}
			},
			error: function(error) {
				console.error('Erro ao mover o louvor de volta:', error);
			}
		});
	});

	function obterIndiceLista(tipoLouvor) {
		// Mapear tipoLouvorEnum para o índice da lista
		if (tipoLouvor === 'JUBILO') {
			return 1;
		} else if (tipoLouvor === 'ADORACAO') {
			return 2;
		} else if (tipoLouvor === 'CEIA') {
			return 3;
		}
		// Adicione mais mapeamentos conforme necessário

		// Caso padrão (retorna 1 por padrão)
		return 1;
	}
});

function salvarLouvorNoGrupo(louvorId, grupoId, nomeDoGrupo) {
	console.log("Tentando salvar louvor no grupo. Louvor ID: " + louvorId
		+ ", Grupo ID: " + grupoId);
	// Verifica se o grupoId está definido para determinar se é um novo salvamento ou uma mudança de grupo
	var isMudancaDeGrupo = grupoId !== undefined;

	$.ajax({
		type: 'POST',
		url: isMudancaDeGrupo ? '/salvar-louvor-ao-grupo'
			: '/mudar-louvor-de-grupo',
		data: {
			louvorId: louvorId,
			grupoId: grupoId,
			nomeDoGrupo: nomeDoGrupo
		},
		success: function(response) {
			console.log("Resposta do servidor:", response); // Log da resposta do servidor (pode ser removido)
			// Lógica adicional se necessário
		},
		error: function(error) {
			console.error("Erro ao salvar louvor no grupo:", error);
			// Lógica adicional de tratamento de erro, se necessário
		}
	});
}

 $(document).ready(function(){
        $('[data-toggle="tooltip"]').tooltip();
    });




