import { Component, OnInit, inject } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Categoria, CategoriaService } from '../../services/categoria.service';
import { ModalComponent } from '../../components/modal/modal.component';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-categoria',
  standalone: true,
  imports: [RouterModule, ModalComponent],
  templateUrl: './categoria.component.html',
  styleUrl: './categoria.component.scss'
})
export class CategoriaComponent implements OnInit{
  categorias: Categoria[] = [];
  categoriaService = inject(CategoriaService);
  modalOpen = false;
  categoriaToDelete: number | null = null;

  ngOnInit(): void {
    this.getCategorias();
  }

  getCategorias(){
    this.categoriaService.findAll().then(categorias => {
      this.categorias = categorias;
    });
  }

  closeModal(){
    this.modalOpen = false;
    this.categoriaToDelete = null;
  }

  openModalDelete(idCategoria: number){
    this.modalOpen = true;
    this.categoriaToDelete = idCategoria;
  }

  deleteCategoria(){
    if(this.categoriaToDelete !== null){
      this.categoriaService.delete(this.categoriaToDelete).then(resp => {
        this.closeModal();
        this.getCategorias();
      })
      .catch(err => {
        Swal.fire({
          title: 'Erro',
          text: err.error.errors[0] || "Ocorreu um erro ao salvar",
          icon: 'error'
        });
      })
    }
  }
}
