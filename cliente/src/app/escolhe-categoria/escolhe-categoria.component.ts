import { Component, OnInit, inject } from '@angular/core';
import { PartidaService } from '../services/partida.service';
import { Categoria } from '../services/categoria.service';

@Component({
  selector: 'app-escolhe-categoria',
  standalone: true,
  imports: [],
  templateUrl: './escolhe-categoria.component.html',
  styleUrl: './escolhe-categoria.component.scss'
})
export class EscolheCategoriaComponent implements OnInit{
  categorias: Categoria[] = [];
  partidaService: PartidaService = inject(PartidaService);

  ngOnInit(): void {
    this.partidaService.getCategorias().then(resp => {
      this.categorias = resp;
    })
  }

}
