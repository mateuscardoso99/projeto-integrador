import { Component, OnInit } from '@angular/core';
import { Categoria } from '../services/categoria.service';
import { PartidaService, Ranking } from '../services/partida.service';
import { LoaderComponent } from '../components/loader/loader.component';

@Component({
  selector: 'app-ranking',
  standalone: true,
  imports: [LoaderComponent],
  templateUrl: './ranking.component.html',
  styleUrl: './ranking.component.scss'
})
export class RankingComponent implements OnInit{
  categorias: Categoria[] = [];
  ranking: Ranking[] = [];
  isLoading: boolean = true;

  constructor(private partidaService: PartidaService){}

  ngOnInit(): void {
    this.partidaService.getCategorias().then((resp: Categoria[]) => {
      this.categorias = resp;
    }).finally(() => this.isLoading = false);
  }

  changeCategoria(event: any){
    this.isLoading = true;
    this.partidaService.getRanking(event.target.value).then((resp: Ranking[]) => {
      this.ranking = resp;
    }).finally(() => this.isLoading = false);
  }

}
