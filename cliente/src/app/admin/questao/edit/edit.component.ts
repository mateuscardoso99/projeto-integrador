import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { QuestaoService, SaveQuestao, SaveResposta } from '../../../services/questao.service';
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
  questao: SaveQuestao = new SaveQuestao();
  idCategoria: number | null = null;
  idQuestao: number | null = null;

  errors: any = {
    idCategoria: null,
    descricao: null,
    respostas: null
  }

  constructor(private questaoService: QuestaoService, private route: ActivatedRoute){}

  ngOnInit(): void {
    this.route.queryParamMap.subscribe((p: any) => {
      if(p['params'].idCategoria){
        this.idCategoria = p['params'].idCategoria;

        this.questao.idCategoria = this.idCategoria
      }

      if(p['params'].idQuestao){
        this.idQuestao = p['params'].idQuestao;

        if(this.idQuestao){
          this.questaoService.findById(this.idQuestao).then(questao => {
            this.questao.ativo = questao.ativo
            this.questao.idCategoria = questao.categoria.id
            this.questao.descricao = questao.descricao

            this.questao.respostas = [];

            questao.respostas.forEach(resposta => {

              const saveResposta: SaveResposta = new SaveResposta();
              saveResposta.certa = resposta.certa
              saveResposta.descricao = resposta.descricao
              saveResposta.idQuestao = questao.id
              saveResposta.id = resposta.id

              this.questao.respostas.push(saveResposta)
            })
          })
        }
      }
    });    
  }

  salvar(){
    console.log(this.questao);
    //this.questao.respostas[3].id = 200
    //editar
    if(this.idQuestao){
      this.questaoService.update(this.questao, this.idQuestao).then(response => {
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
          text: "Ocorreu um erro ao realizar o cadastro",
          icon: 'error'
        });
      }).catch(error => {
        Swal.fire({
          title: 'Erro',
          text: error.error.errors ? error.error.errors[0] : "Ocorreu um erro ao realizar a atualização",
          icon: 'error'
        });
      })
    }
    //cadastrar
    else{
      this.questaoService.save(this.questao).then(response => {
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
      }).catch(error => {  
        ApiErrorMessages.buildErrorByInputs(this.errors, error)

        //mensagens de erro da api do array de respostas
        const errosEnvioRespostas = Object.keys(error.error).filter(e => e.startsWith("respostas["))

        errosEnvioRespostas.forEach(e => {
          this.errors[e] = error.error[e];
        });

        console.log(this.errors);
        
        
        Swal.fire({
          title: 'Erro',
          text: error.error.errors ? error.error.errors[0] : "Ocorreu um erro ao realizar o cadastro",
          icon: 'error'
        });
      })
      
    }
  }
}
