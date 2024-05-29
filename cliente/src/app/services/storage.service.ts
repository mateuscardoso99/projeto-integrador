import { Injectable } from "@angular/core";
import { UsuarioToken } from "./auth.service";
@Injectable({
    providedIn:  'root'
})
export class StorageService{
    removeItem(key: string){
        sessionStorage.removeItem(key);
    }
    setItem(key: string, value: any){
        sessionStorage.setItem(key, value);
    }
    getItem(key: string){
        return sessionStorage.getItem(key);
    }
    hasItem(key: string){
        return !!this.getItem(key);
    }
    getUser(): UsuarioToken | null {
        const user = this.getItem("user");
        if (user) {
          return JSON.parse(user);
        }
        return null;
    }
}