import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { StorageService } from '../services/storage.service';
import { UsuarioToken } from '../services/auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  //debugger; inicializa o debugger no navegador
  const router = inject(Router); //daí não precisa injetar no construtor
  const storageService = inject(StorageService);
  const user: UsuarioToken | null = storageService.getUser();

  if(user == null){
    if(state.url !== '/login' && state.url !== '/criar-conta'){
      router.navigate(['/login']);
      return false;
    }
    return true;
  }
  else{    
    if(user.usuario.isAdmin){
      if(!state.url.includes("/admin")){
        router.navigate(['/admin']);
        return false;
      }
      return true;
    }
    else{
      if(state.url === '/login' || state.url === '/criar-conta'){
        router.navigate(['/escolher-categoria']);
        return false;
      }
      return true;
    }
  }
};
