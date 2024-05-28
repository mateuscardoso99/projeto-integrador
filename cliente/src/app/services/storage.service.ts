import { Injectable } from "@angular/core";
@Injectable({
    providedIn:  'root'
})
export class StorageService{
    setItem(key: string, value: any){
        sessionStorage.setItem(key, value);
    }
    getItem(key: string){
        return sessionStorage.getItem(key);
    }
    hasItem(key: string){
        return !!this.getItem(key);
    }
}