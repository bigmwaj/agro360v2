import { Component } from '@angular/core';
import { FieldMetadata } from 'src/app/backed/metadata';
import { SharedModule } from '../../common/shared.module';
import { AuthService } from '../../common/service/auth.service';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
    standalone: true,
    imports: [
        SharedModule,     
    ],
    selector: 'home-index-login-dialog',
    templateUrl: './login.dialog.component.html'
})
export class LoginDialogComponent{   
    
    username:FieldMetadata<string> = {
        label:"Nom d'utilisateur",
        editable:true,
        required:true,
        maxLength: 32,
    } as FieldMetadata<string>;

    password:FieldMetadata<string> = {
        label:"Mot de passe",
        editable:true,
        required:true,
        maxLength: 32,
    } as FieldMetadata<string>;

    constructor(
        private authService: AuthService,
        public dialogRef: MatDialogRef<LoginDialogComponent>
    ){}

    authenticate(){
        this.authService.login({username:this.username.value, password:this.password.value}, () => this.dialogRef.close(true));
    }
    
    cancel(){
        this.dialogRef.close(false)
        this.authService.redirectToLoginPage();
    }
}
