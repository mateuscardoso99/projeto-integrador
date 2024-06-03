import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { StorageService } from '../services/storage.service';
import { Router } from '@angular/router';

export const interceptor: HttpInterceptorFn = (req, next) => {
  const storageService = inject(StorageService);
  const router = inject(Router);
  //const user: UsuarioToken | null = storageService.getUser();
  
  /* 
  cookies são enviados automaticamente nas requisições pelo navegador, não precisa definir o header
  a api define o cookie e o cliente não precisa fazer nada, só definir withCredentials como true para o cookie ser enviado
  if(user != null){
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${user.token}`
      }
    });
  }*/
  
  req = req.clone({
      withCredentials: true
  });

  return next(req).pipe(
    catchError((err: any) => {
      console.log(err);
      
      if (err instanceof HttpErrorResponse) {
        // Handle HTTP errors
        if (err.status === 401 || err.status === 403) {
          // Specific handling for unauthorized errors         
          storageService.removeItem("user");
          router.navigate(['/login']);
          // You might trigger a re-authentication flow or redirect the user here
        } else {
          // Handle other HTTP error codes
          console.error('HTTP error:', err, err.status);
        }
      } else {
        // Handle non-HTTP errors
        console.error('An error occurred:', err);
      }

      // Re-throw the error to propagate it further
      return throwError(() => err); 
    })
  );;
};
