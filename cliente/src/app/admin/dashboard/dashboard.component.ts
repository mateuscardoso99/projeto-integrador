import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router, RouterModule, RouterOutlet, UrlSegment } from '@angular/router';
import { Observable, filter, map } from 'rxjs';
import { StorageService } from '../../services/storage.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [RouterOutlet, RouterModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit{
  path: string = '';

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private storageService: StorageService){}
  ngOnInit(): void {
    this.path = this.activatedRoute.snapshot.url[0].path;
    
    this.router.events.subscribe(e => {      
      if(e instanceof NavigationEnd){
        this.path = e.url        
      }
    });
  }

  logout(){
    this.storageService.removeItem("user");
    this.router.navigate(['/login']);
  }
}
