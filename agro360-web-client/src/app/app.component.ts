import { Component, ViewChild, ElementRef } from '@angular/core';
import { UIService } from './modules/common/service/ui.service';
import { AuthService } from './modules/common/service/auth.service';
import { MatDialog } from '@angular/material/dialog';
import { LogoutDialogComponent } from './modules/home/index/logout.dialog.component';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html'
})
export class AppComponent  {
    constructor(
        private ui: UIService, 
        private authService: AuthService,    
        private dialog: MatDialog) { }

    @ViewChild("pageTitle")
    pageTitle:ElementRef;

    ngAfterViewInit(): void {
        this.ui.setPageTitle(this.pageTitle)
    }

    isAuthenticated():boolean{
        return this.authService.isLogged();
    }

    displayMenu():boolean{
        return this.authService.isLogged();
    }

    logoutAction() {
        this.dialog.open(LogoutDialogComponent);
    }

}
