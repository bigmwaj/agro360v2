import { Component, OnInit } from '@angular/core';
import { BreadcrumbItem, UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from '../../common/shared.module';
import { AuthService } from '../../common/service/auth.service';

@Component({
    standalone: true,
    imports: [
        SharedModule,     
    ],
    selector: 'home-index-profil-page',
    templateUrl: './profil.page.component.html'
})
export class ProfilPageComponent implements OnInit {   
    
    breadcrumb:BreadcrumbItem;

    constructor(
        private ui: UIService,
        private authService: AuthService
    ){
    }

    ngOnInit(): void {        
        this.breadcrumb = new BreadcrumbItem("Profil");
    }

    ngAfterViewInit(): void {
        this.ui.setBreadcrumb(this.breadcrumb)
    }

}
