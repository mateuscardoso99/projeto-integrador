import { Component, OnInit, inject } from '@angular/core';
import { Partida, PartidaService } from '../services/partida.service';
import { Categoria } from '../services/categoria.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

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
  router: Router = inject(Router);

  ngOnInit(): void {
    this.partidaService.getCategorias().then(resp => {
      this.categorias = resp;
    })
  }

  iniciarPartida(idCategoria: number){
    this.partidaService.iniciarPartida(idCategoria).then((resp: Partida) => {
      console.log(resp);
      
      if(resp.id){
        this.router.navigate([`/usuario/partida/${resp.id}`]);
      }
    }).catch(e => {
      Swal.fire({
        title: 'Erro',
        text: e.error.errors[0] || 'erro ao iniciar a partida',
        icon: 'error'
      })
    })
  }

}
