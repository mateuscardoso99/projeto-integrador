import { Component, OnInit } from '@angular/core';
import { CategoriaService } from '../../services/categoria.service';
import { QuestaoService } from '../../services/questao.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit{
  totalCategorias: number = 0;
  totalQuestoes: number = 0;
  
  constructor(private categoriaService: CategoriaService, private questaoService: QuestaoService){}

  ngOnInit(): void {
    Promise.all([this.categoriaService.count(), this.questaoService.count()]).then(resp => {
      this.totalCategorias = resp[0];
      this.totalQuestoes = resp[1];
    });
  }

}
