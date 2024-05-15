import { Component, OnInit } from '@angular/core';
import { BreadcrumbItem, UIService } from 'src/app/modules/common/service/ui.service';
import { FieldMetadata } from 'src/app/backed/metadata';
import { SharedModule } from '../../common/shared.module';
import { AuthService } from '../../common/service/auth.service';

@Component({
    standalone: true,
    imports: [
        SharedModule,     
    ],
    selector: 'home-index-login-page',
    templateUrl: './login.page.component.html'
})
export class LoginPageComponent implements OnInit {   
    
    breadcrumb:BreadcrumbItem;

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
        private ui: UIService,
        private authService: AuthService
    ){}

    ngOnInit(): void {        
        this.breadcrumb = new BreadcrumbItem("Login page");
    }

    ngAfterViewInit(): void {
        this.ui.setBreadcrumb(this.breadcrumb)
    }

    authenticate(){
        this.authService.login({username:this.username.value, password:this.password.value})
    }
}
