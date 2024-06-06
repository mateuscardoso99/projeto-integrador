import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { QuestaoService, SaveQuestao, SaveResposta } from '../../../services/questao.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

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

  constructor(private questaoService: QuestaoService, private route: ActivatedRoute){}

  ngOnInit(): void {
    this.route.queryParamMap.subscribe((p: any) => {
      if(p['params'].idCategoria){
        this.idCategoria = p['params'].idCategoria;

        this.questao.categoria = this.idCategoria
      }

      if(p['params'].idQuestao){
        this.idQuestao = p['params'].idQuestao;

        if(this.idQuestao){
          this.questaoService.findById(this.idQuestao).then(questao => {
            this.questao.ativo = questao.ativo
            this.questao.categoria = questao.categoria.id
            this.questao.descricao = questao.descricao

            this.questao.respostas = [];

            questao.respostas.forEach(resposta => {

              const saveResposta: SaveResposta = new SaveResposta();
              saveResposta.certa = resposta.certa
              saveResposta.descricao = resposta.descricao
              saveResposta.questao = questao.id

              this.questao.respostas.push(saveResposta)
            })
          })
        }
      }
    });    
  }

  salvar(){
    console.log(this.questao);
    
    //editar
    if(this.idQuestao){
      this.questaoService.update(this.questao, this.idQuestao).then(resp => {
        console.log(resp);
      })
    }
    //cadastrar
    else{
      this.questaoService.save(this.questao).then(resp => {
        console.log(resp);
      })
      
    }
  }
}
