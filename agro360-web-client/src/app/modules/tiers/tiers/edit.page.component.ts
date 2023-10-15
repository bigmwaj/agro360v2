import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { TiersBean } from 'src/app/backed/bean.tiers';
import { IndexModalComponent as CategoryIndexModalComponent } from '../category/index.modal.component';
import { CategoryBlockComponent } from './category.block.component';
import { SharedModule } from 'src/app/common/shared.module';
import { BeanTools } from 'src/app/common/bean.tools';
import { TiersService } from '../tiers.service';
import { map } from 'rxjs';
import { Message } from 'src/app/backed/message';
import { UIService } from 'src/app/common/service/ui.service';

@Component({
    standalone: true,
    imports:[
        CategoryBlockComponent,    
        SharedModule
    ],
    selector: 'tiers-tiers-edit-page',
    templateUrl: './edit.page.component.html'
})
export class EditPageComponent implements OnInit {

    bean: TiersBean;

    pageTitle: string = "Edition";

    constructor(private router: Router,
        private route: ActivatedRoute,
        private http: HttpClient,
        public dialog: MatDialog,
        private service:TiersService,
        private ui: UIService) { }

    isCreation(): boolean {
        let path = this.route.routeConfig?.path;
        return !!path && path.endsWith("create");
    }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        if (this.isCreation()) {
            this.route.queryParamMap.subscribe(params => {
                const copyFrom = params.get('copyFrom');
                if (copyFrom) {
                    queryParams = queryParams.append("copyFrom", copyFrom);
                }
                this.http
                    .get(`tiers/tiers/create-form`, { params: queryParams })
                    .subscribe(data => this.bean = <TiersBean>data);
                
                    this.ui.setTitle("Création d'un Tiers")
            });
        } else {
            // On doit traiter les potentielles erreurs
            this.route.paramMap.subscribe(params => {
                const tiersCode = params.get('tiersCode');
                if( !! tiersCode ){
                    queryParams = queryParams.append("tiersCode", tiersCode);
                }
                this.http
                    .get<any>(`tiers/tiers/edit-form`, {params: queryParams})
                    .subscribe(data => this.bean = data);
                
                this.ui.setTitle("Édition du Tiers " + tiersCode)
            });
        }
    }

    addAction() {
        this.router.navigate(['edit'], { relativeTo: this.route })
    }

    copyAction() {
        this.router.navigate(['edit', this.bean.tiersCode.value], { relativeTo: this.route.parent })
    }

    saveAction() {
        this.http.post(`tiers/tiers`, BeanTools.reviewBeanAction(this.bean))            
            .pipe(map((e: any) => <any>e))
            .subscribe(data => {
                this.redirectToEditPage(data.id)
                this.ui.displayFlashMessage(<Array<Message>>data.messages);
            });
    }

    categoryAction() {
        this.dialog.open(CategoryIndexModalComponent);
    }
    
    private redirectToEditPage(id:string):void{
        this.router.navigate(
            [
                '/tiers/tiers',
                'edit', 
                id
            ]
        )
    }
}
