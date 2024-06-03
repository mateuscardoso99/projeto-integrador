import { Injectable } from "@angular/core";
import { Usuario } from "./auth.service";

@Injectable({
    providedIn:  'root'
})
export class StorageService{
    removeItem(key: string){
        localStorage.removeItem(key);
    }
    setItem(key: string, value: any){
        localStorage.setItem(key, value);
    }
    getItem(key: string){
        return localStorage.getItem(key);
    }
    hasItem(key: string){
        return !!this.getItem(key);
    }
    getUser(): Usuario | null {
        const user = this.getItem("user");
        if (user) {
          return JSON.parse(user);
        }
        return null;
    }
}