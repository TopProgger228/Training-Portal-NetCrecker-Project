import { Component, OnInit, Input } from '@angular/core';

import { TokenStorageService } from '../../auth/token-storage.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
@Input()
role:string;

info: any;

constructor(private token: TokenStorageService,
  private router: Router) { }

ngOnInit() {
  this.info = {
    token: this.token.getToken(),
    username: this.token.getUsername(),
    authorities: this.token.getAuthorities()
  };
}
  logout() {
    this.token.signOut();
    window.location.reload();
    this.router.navigate(['']);
  }

}
