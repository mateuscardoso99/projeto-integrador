import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { StorageService } from '../services/storage.service';
import { Usuario } from '../services/auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  //debugger; inicializa o debugger no navegador
  const router = inject(Router); //daí não precisa injetar no construtor
  const storageService = inject(StorageService);
  const user: Usuario | null = storageService.getUser();

  if(user == null){
    if(state.url !== '/login' && state.url !== '/criar-conta'){
      router.navigate(['/login']);
      return false;
    }
    return true;
  }
  else{
    if(state.url === '/login' || state.url === '/criar-conta'){
      if(user.isAdmin){
        router.navigate(['/admin']);
      }
      else{
        router.navigate(['/usuario/escolher-categoria']);
      }
      return false;
    }
    else if(state.url.includes("/admin") && !user.isAdmin){
      router.navigate(['/usuario/escolher-categoria']);
      return false;
    }
    return true;
  }
};
