import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { LoaderComponent } from '../../components/loader/loader.component';
import { Categoria, CategoriaService } from '../../services/categoria.service';
import { Questao, QuestaoService } from '../../services/questao.service';

@Component({
  selector: 'app-questao',
  standalone: true,
  imports: [RouterModule, LoaderComponent],
  templateUrl: './questao.component.html',
  styleUrl: './questao.component.scss'
})
export class QuestaoComponent implements OnInit{
  isLoading = false;
  categorias: Categoria[] = [];
  questoes: Questao[] = [];

  constructor(private categoriaService: CategoriaService, private questaoService: QuestaoService){}
  
  ngOnInit(): void {
    this.isLoading = true;
    this.categoriaService.findAll().then(response => {
      this.categorias = response;
    }).finally(() => this.isLoading = false)
  }

  getQuestoes(event: any){
    this.isLoading = true;
    this.questaoService.findByCategoria(event.target.value).then(response => {
      this.questoes = response;
    }).finally(() => this.isLoading = false);
    
  }
}
