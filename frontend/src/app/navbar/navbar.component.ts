import {Component, HostListener} from '@angular/core';
import {RegisteredUserService} from "../services/registered-user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  menuOpen = false; // Variable de control para el estado del menú desplegable

  constructor(public registeredUserService: RegisteredUserService, private router: Router) {
  }

  closeMenu(): void {
    this.menuOpen = false;
  }

  @HostListener('document:click')
  onDocumentClick(): void {
    if (this.menuOpen) {
      this.closeMenu();
    }
  }

  logOut() {
    this.registeredUserService.logOut();
    this.router.navigate([''])
  }

}
