import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanTools } from 'src/app/modules/common/bean.tools';
import { CycleBean } from 'src/app/backed/bean.production.avicole';
import { EditMetadataListComponent } from './edit.metadata.list.component';
import { map } from 'rxjs';
import { Message } from 'src/app/backed/message';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { StockService } from 'src/app/modules/stock/stock.service';

@Component({
    standalone: true,
    imports: [
        EditMetadataListComponent,
        SharedModule
    ],
    selector: 'production-avicole-cycle-edit-page',
    templateUrl: './edit.page.component.html'
})
export class EditPageComponent implements OnInit {

    bean: CycleBean;

    pageTitle: string = "Edition";

    constructor(private router: Router,
        private route: ActivatedRoute,
        private http: HttpClient,
        public dialog: MatDialog,
        private ui: UIService, public stockService: StockService
    ) { }

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
                    .get(`production/avicole/cycle/create-form`, { params: queryParams })
                    .subscribe(data => { 
                        this.bean = <CycleBean>data;                        
                        this.initSelectMagasinOptions();
                    });

                this.pageTitle = "Création d'un Cycle"
            });
        } else {
            // On doit traiter les potentielles erreurs
            this.route.paramMap.subscribe(params => {
                const cycleCode = params.get('cycleCode');
                if (!!cycleCode) {
                    queryParams = queryParams.append("cycleCode", cycleCode);
                }
                this.http
                    .get<any>(`production/avicole/cycle/edit-form`, { params: queryParams })
                    .subscribe(data => { 
                        this.bean = data;                         
                        this.initSelectMagasinOptions();
                    });

                this.pageTitle = "Édition du Cycle " + cycleCode
            });
        }
    }

    addAction() {
        this.router.navigate(['edit'], { relativeTo: this.route })
    }

    copyAction() {
        this.router.navigate(['edit', this.bean.cycleCode.value], { relativeTo: this.route.parent })
    }

    saveAction() {
        this.http.post(`production/avicole/cycle`, BeanTools.reviewBeanAction(this.bean))
            .pipe(map((e: any) => <any>e))
            .subscribe(data => {
                this.redirectToEditPage(data.id)
                this.ui.displayFlashMessage(<Array<Message>>data.messages);
            });
    }

    private initSelectMagasinOptions() {
        this.stockService.getMagasinsAsValueOptions(this.http, {})
            .subscribe(e => this.bean.magasin.magasinCode.valueOptions = e)
    }

    private redirectToEditPage(id:string):void{
        this.router.navigate(
            [
                '/production/avicole/cycle',
                'edit', 
                id
            ]
        )
    }

}
