import { Component, OnInit } from '@angular/core';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { AuthService } from '../../common/service/auth.service';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'index-logout-dialog',
    templateUrl: './logout.dialog.component.html'
})
export class LogoutDialogComponent implements OnInit {

    constructor(private authService: AuthService) { }

    ngOnInit(): void {
       
    }

    processAction() {
        this.authService.logout()
    }  
}
