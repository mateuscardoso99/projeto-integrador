import { Component, OnInit } from '@angular/core';
import { Categoria } from '../services/categoria.service';
import { PartidaService, Ranking } from '../services/partida.service';
import { LoaderComponent } from '../components/loader/loader.component';
import { CommonModule } from '@angular/common';
import { Usuario } from '../services/auth.service';
import { StorageService } from '../services/storage.service';

@Component({
  selector: 'app-ranking',
  standalone: true,
  imports: [LoaderComponent, CommonModule],
  templateUrl: './ranking.component.html',
  styleUrl: './ranking.component.scss'
})
export class RankingComponent implements OnInit{
  categorias: Categoria[] = [];
  ranking: Ranking[] = [];
  isLoading: boolean = true;
  usuario: Usuario | null;

  constructor(private partidaService: PartidaService, private storageService: StorageService){
    this.usuario = storageService.getUser();
  }

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
