import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { LoaderComponent } from '../../components/loader/loader.component';
import { Categoria, CategoriaService } from '../../services/categoria.service';
import { Questao, QuestaoService } from '../../services/questao.service';
import Swal from 'sweetalert2';
import { ModalComponent } from '../../components/modal/modal.component';

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
  categoriaSelecionada: number | null = null;

  constructor(private categoriaService: CategoriaService, private questaoService: QuestaoService){}
  
  ngOnInit(): void {
    this.isLoading = true;
    this.categoriaService.findAll().then(response => {
      this.categorias = response;
    }).finally(() => this.isLoading = false)
  }

  changeCategoria(event: any){
    this.categoriaSelecionada = event.target.value;
    this.getQuestoes();
  }

  getQuestoes(){
    if(this.categoriaSelecionada){
      this.isLoading = true;
      this.questaoService.findByCategoria(this.categoriaSelecionada).then(response => {
        this.questoes = response;
      }).finally(() => this.isLoading = false);
    }    
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
