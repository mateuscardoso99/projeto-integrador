import { Component, OnInit } from '@angular/core';
import { CategoriaService } from '../../services/categoria.service';
import { QuestaoService } from '../../services/questao.service';
import { LoaderComponent } from '../../components/loader/loader.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [LoaderComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit{
  totalCategorias: number = 0;
  totalQuestoes: number = 0;
  isLoading = false;
  
  constructor(private categoriaService: CategoriaService, private questaoService: QuestaoService){}

  ngOnInit(): void {
    this.isLoading = true;
    Promise.all([this.categoriaService.count(), this.questaoService.count()]).then(resp => {
      this.totalCategorias = resp[0];
      this.totalQuestoes = resp[1];
    }).finally(() => this.isLoading = false);
  }

}
