import { Component, OnInit } from '@angular/core';
import { BreadcrumbItem, UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from '../../common/shared.module';
import { UserAccountBean } from 'src/app/backed/bean.core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs';

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

    bean: UserAccountBean;

    constructor(
        private ui: UIService,
        private http: HttpClient,
    ){
    }

    ngOnInit(): void {        
        this.breadcrumb = new BreadcrumbItem("Profil");

        this.http
            .get(`get-profil`)
            .pipe(map((e: any) => <any>e))
            .subscribe(data => {
                this.bean = data.record;
            });
    }

    ngAfterViewInit(): void {
        this.ui.setBreadcrumb(this.breadcrumb)
    }

}
