import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { LoaderComponent } from '../../components/loader/loader.component';
import { Categoria, CategoriaService } from '../../services/categoria.service';
import { Questao, QuestaoService } from '../../services/questao.service';
import Swal from 'sweetalert2';
import { ModalComponent } from '../../components/modal/modal.component';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-questao',
  standalone: true,
  imports: [RouterModule, LoaderComponent, ModalComponent],
  templateUrl: './questao.component.html',
  styleUrl: './questao.component.scss'
})
export class QuestaoComponent implements OnInit{
  isLoading = false;
  categorias: Categoria[] = [];
  questoes: Questao[] = [];
  modalOpen = false;
  questaoToDelete: number | null = null;
  categoriaSelecionada: Categoria | null = null;

  categoriaSubscribe: Subscription | undefined = undefined;

  paginaAtual = 0;
  itemsPorPagina = 5;
  totalDePaginas = 0;

  constructor(private categoriaService: CategoriaService, private questaoService: QuestaoService){}

  /**
   * behavior subject serve como uma fonte central de dados onde todos os componentes podem acessar e obter o valor atualizado para usar
   * através do método subject(), pra atualizar um valor usa-se next(), pra obter o valor usa-se value()
   */
  
  ngOnInit(): void {
    this.isLoading = true;
    this.categoriaService.findAll().then(response => {
      this.categorias = response;
    }).finally(() => this.isLoading = false)

    this.categoriaSelecionada = this.categoriaService.getCategoriaSelecionada().value;
    this.getQuestoes();

    this.categoriaSubscribe = this.categoriaService.getCategoriaSelecionada().subscribe(cat => {     
      this.categoriaSelecionada = cat;
    })
  }

  ngOnDestroy() {
    if(this.categoriaSubscribe){
      this.categoriaSubscribe.unsubscribe();
    }
  }

  changeCategoria(event: any){
    const categoria = this.categorias.filter(c => c.id == event.target.value)[0];
    this.categoriaService.alterarCategoriaSelecionada(categoria);
    this.getQuestoes();
  }

  getQuestoes(){   
    if(this.categoriaSelecionada){
      this.isLoading = true;
      this.questaoService.findByCategoria(this.categoriaSelecionada.id, this.paginaAtual, this.itemsPorPagina).then(response => {
        this.totalDePaginas = response.totalPages;

        const novasQuestoes: Questao[] = [];

        response.questoes.forEach((q: any) => {
          novasQuestoes.push(new Questao(q));
        })

        this.questoes = [...this.questoes, ...novasQuestoes];
        
      }).finally(() => this.isLoading = false);
    }    
  }

  carregarMais(){
    if(this.paginaAtual == this.totalDePaginas - 1) return;
    this.paginaAtual++;
    this.getQuestoes();
  }

  closeModal(){
    this.modalOpen = false;
    this.questaoToDelete = null;
  }

  openModalDelete(idQuestao: number){
    this.modalOpen = true;
    this.questaoToDelete = idQuestao;
  }

  deleteQuestao(){
    if(this.questaoToDelete !== null){
      this.questaoService.delete(this.questaoToDelete).then(() => {
        this.closeModal();
        this.getQuestoes();
        Swal.fire({
          title: 'Excluído',
          text: "Questão apagada com sucesso",
          icon: 'success'
        });
      })
      .catch(err => {
        Swal.fire({
          title: 'Erro',
          text: err.error.errors[0] || "Ocorreu um erro ao excluir",
          icon: 'error'
        });
      })
    }
  }
}
