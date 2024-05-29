import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { CategoriaService, SaveCategoria } from '../../../services/categoria.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';
import ApiErrorMessages from '../../../utils/showErrorValidationApi';

@Component({
  selector: 'app-edit',
  standalone: true,
  imports: [RouterModule, FormsModule, CommonModule],
  templateUrl: './edit.component.html',
  styleUrl: './edit.component.scss'
})
export class EditComponent implements OnInit{
  saveCategoria: SaveCategoria = new SaveCategoria();
  idEditCategoria: number | null = null;
  errors: any = {
    codigo: null,
    nome: null
  }

  constructor(private categoriaService: CategoriaService, private route: ActivatedRoute){}

  ngOnInit(): void {
    this.route.queryParamMap.subscribe((p: any) => {
      if(p['params'].idCategoria){
        this.idEditCategoria = p['params'].idCategoria;

        this.categoriaService.findById(p['params'].idCategoria).then(response => {
          this.saveCategoria.codigo = response.codigo;
          this.saveCategoria.nome = response.nome;
        })
      }
    });
    
  }

  salvar(){
    if(this.idEditCategoria === null){
      this.categoriaService.save(this.saveCategoria).then(response => {
        if(response.id){
          Swal.fire({
            title: 'Sucesso',
            text: "Cadastrado com sucesso",
            icon: 'success'
          });
          return;
        }
        Swal.fire({
          title: 'Erro',
          text: "Ocorreu um erro ao realizar o cadastro",
          icon: 'error'
        });
      })
      .catch(error => {      
        ApiErrorMessages.buildErrorByInputs(this.errors, error)
         
        Swal.fire({
          title: 'Erro',
          text: error.error.errors ? error.error.errors[0] : "Ocorreu um erro ao realizar o cadastro",
          icon: 'error'
        });        
      });
    }
    else{
      this.categoriaService.update(this.saveCategoria, this.idEditCategoria).then(response => {
        if(response.id){
          Swal.fire({
            title: 'Sucesso',
            text: "Atualizado com sucesso",
            icon: 'success'
          });
          return;
        }
        Swal.fire({
          title: 'Erro',
          text: "Ocorreu um erro ao salvar a categoria",
          icon: 'error'
        });        
      })
      .catch(error => {
        ApiErrorMessages.buildErrorByInputs(this.errors, error);
        
        Swal.fire({
          title: 'Erro',
          text: error.error.errors[0] || "Ocorreu um erro ao salvar",
          icon: 'error'
        });
      });
    }
  }
}
