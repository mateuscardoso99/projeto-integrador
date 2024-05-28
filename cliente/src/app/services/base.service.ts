import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { StorageService } from "./storage.service";
@Injectable({
    providedIn:  'root'
})
export class BaseService {
    constructor(protected http: HttpClient, protected storage: StorageService) { }
}