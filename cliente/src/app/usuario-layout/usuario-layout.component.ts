import { Component, OnInit } from '@angular/core';
import { Router, RouterModule, RouterOutlet } from '@angular/router';
import { StorageService } from '../services/storage.service';
import { AuthService, Usuario } from '../services/auth.service';

@Component({
  selector: 'app-usuario-layout',
  standalone: true,
  imports: [RouterOutlet, RouterModule],
  templateUrl: './usuario-layout.component.html',
  styleUrl: './usuario-layout.component.scss'
})
export class UsuarioLayoutComponent implements OnInit{
  usuarioLogado: Usuario | null = null;

  constructor(private router: Router, private storageService: StorageService, protected authService: AuthService){}
  
  ngOnInit(): void {
    this.usuarioLogado = this.storageService.getUser();
  }
}
